# Nuevas pestañas en Playwright Java con POM, Cucumber y TestNG paralelo

## Resumen ejecutivo

La captura de una pestaña debe ser **atómica con la acción que la crea**. En Playwright Java la
forma estable es `Page.waitForPopup(() -> locator.click())`; si el popup puede originarse fuera de
una `Page` concreta, el equivalente es `BrowserContext.waitForPage(...)`. No se debe hacer clic y
después inspeccionar `context.pages()`: entre ambas operaciones existe una carrera.

Para este framework, la opción preferida es que el Page Object de origen devuelva el Page Object de
destino, apoyado por un helper pequeño de `BasePage`. El objeto destino recibe la `Page` capturada,
espera una condición observable propia de la pantalla y ofrece cierre explícito. La `Page` original
no necesita "recuperar foco": cada Page Object conserva su propia referencia `Page`.

Referencias oficiales: [Pages y popups](https://playwright.dev/java/docs/pages),
[aislamiento con BrowserContext](https://playwright.dev/java/docs/browser-contexts),
[auto-waiting](https://playwright.dev/java/docs/actionability) y
[modelo de hilos de Playwright Java](https://playwright.dev/java/docs/multithreading).

## Premisas aplicadas al repositorio

- `DriverFactory` crea `Playwright`, `Browser`, `BrowserContext` y la página inicial dentro del
  lifecycle existente; ningún enfoque propuesto crea esos objetos de infraestructura.
- `DriverManager` confina esos objetos con `ThreadLocal`, pero sólo registra la página inicial. Un
  popup debe permanecer como referencia local o scenario-scoped, no reemplazar globalmente esa
  referencia.
- el hook crea y destruye el driver por escenario; cerrar el `BrowserContext` al final termina
  cerrando cualquier popup olvidado, aunque el cierre temprano sigue siendo recomendable;
- PicoContainer crea los objetos de glue por escenario. `ScenarioContext` puede transportar datos
  de negocio, pero no debería convertirse en un registro global mutable de pestañas;
- el `DataProvider` de TestNG es paralelo. La seguridad depende de que todas las operaciones sobre
  una instancia Playwright ocurran en el hilo del escenario: Playwright Java no es thread-safe.

## Reglas invariantes

1. Registrar la espera **antes** de disparar el popup mediante el callback de `waitForPopup` o
   `waitForPage`.
2. Esperar una señal específica del destino (`URL`, heading, dato clave), no un tiempo fijo y no
   `NETWORKIDLE` como contrato genérico.
3. Mantener cada `Page` ligada al objeto que la usa; no implementar un `currentPage` estático.
4. No acceder desde otro hilo a `Page`, `BrowserContext` ni objetos derivados.
5. Cerrar el popup cuando deja de ser útil y tolerar que la aplicación ya lo haya cerrado.
6. Mantener assertions de negocio en el Page Object (o capa de assertions), no dentro del helper de
   tabs ni de los Steps.

## Estrategias viables

### 1. Captura local con `Page.waitForPopup`

La operación completa vive en el Page Object que contiene el enlace. Es la unidad mínima correcta.

```java
public final class ResultsPage extends BasePage {
  private final Locator verMas;

  public ResultsPage(Page page) {
    super(page);
    verMas = page.getByRole(AriaRole.LINK,
        new Page.GetByRoleOptions().setName("Ver más"));
  }

  public Page abrirDetalleRaw() {
    Page popup = page.waitForPopup(verMas::click);
    popup.waitForURL("**/detalle/**");
    return popup;
  }
}
```

**Ventajas:** máxima proximidad entre evento y espera, código explícito, riesgo bajo de carrera,
ningún estado compartido y comportamiento naturalmente aislado por escenario/hilo.

**Desventajas:** devuelve una abstracción Playwright a quien llama; repetición si hay muchos
enlaces; puede empujar navegación y assertions a los Steps. Mantenibilidad media y acoplamiento
Step/API de Playwright medio si se usa como solución final.

**Cuándo usarla:** prueba exploratoria, transición única o como primera refactorización segura.

### 2. Helper de popup en `BasePage`

Centraliza únicamente la mecánica, no selectores, destinos ni reglas de negocio.

```java
protected final Page clickAndWaitForPopup(Locator trigger) {
  Page popup = page.waitForPopup(trigger::click);
  popup.waitForLoadState(LoadState.DOMCONTENTLOADED);
  return popup;
}
```

```java
public Page abrirDetalleRaw() {
  Page popup = clickAndWaitForPopup(verMas);
  popup.waitForURL("**/detalle/**");
  return popup;
}
```

**Ventajas:** elimina duplicación, normaliza la captura atómica y conserva Steps delgados. Es
fácil añadir logging técnico sin duplicarlo.

**Desventajas:** un `BasePage` grande se convierte fácilmente en *god object*; una espera global a
`DOMCONTENTLOADED` puede ser insuficiente o innecesaria para algunos popups; el helper no conoce la
condición funcional de cada destino. La estabilidad es alta sólo si el destino aún valida su propia
señal.

**Cuándo usarla:** hay varias transiciones popup con una mecánica verdaderamente común.

### 3. Page Object de origen retorna el Page Object de detalle

Modela la transición en el API del dominio y oculta por completo la `Page` al Step.

```java
public final class ResultsPage extends BasePage {
  private final Locator verMas;

  public ResultsPage(Page page) {
    super(page);
    verMas = page.getByRole(AriaRole.LINK,
        new Page.GetByRoleOptions().setName("Ver más"));
  }

  public DetailPage abrirDetalle() {
    Page popup = page.waitForPopup(verMas::click);
    DetailPage detail = new DetailPage(popup);
    detail.esperarLista();
    return detail;
  }
}

public final class DetailPage extends BasePage implements AutoCloseable {
  private final Locator title;

  public DetailPage(Page page) {
    super(page);
    title = page.getByRole(AriaRole.HEADING,
        new Page.GetByRoleOptions().setName("Detalle"));
  }

  void esperarLista() {
    page.waitForURL("**/detalle/**");
    title.waitFor();
  }

  public String titulo() { return title.textContent(); }

  @Override public void close() {
    if (!page.isClosed()) page.close();
  }
}
```

```java
@Cuando("consulta el detalle")
public void consultaElDetalle() {
  detailPage = resultsPage.abrirDetalle();
}

@Entonces("ve el detalle esperado")
public void veElDetalleEsperado() {
  Assert.assertEquals(detailPage.titulo(), "Detalle");
}
```

**Ventajas:** API legible, encapsulación fuerte, máxima mantenibilidad y mínimo acoplamiento de
Steps. Cada objeto tiene una referencia inmutable a su tab; no existe cambio de foco implícito.

**Desventajas:** crea una dependencia intencional del origen al tipo destino; requiere decidir quién
cierra el destino; no conviene si el enlace abre destinos polimórficos o externos sin contrato.

**Cuándo usarla:** flujo de negocio conocido y estable. Es el enfoque principal recomendado.

### 4. Componente que posee la transición

Si "Ver más" pertenece a una tarjeta o tabla reutilizable, el componente —no `BasePage`— conoce el
selector y devuelve el destino.

```java
public final class ResultCard {
  private final Page page;
  private final Locator root;

  public ResultCard(Page page, Locator root) {
    this.page = page;
    this.root = root;
  }

  public DetailPage abrirDetalle() {
    Locator link = root.getByRole(AriaRole.LINK,
        new Locator.GetByRoleOptions().setName("Ver más"));
    Page popup = page.waitForPopup(link::click);
    DetailPage detail = new DetailPage(popup);
    detail.esperarLista();
    return detail;
  }
}
```

**Ventajas:** cohesión máxima para UI reutilizable, selectores fuera de Steps y `BasePage`, buen
aislamiento y poco riesgo de flakiness.

**Desventajas:** más clases y composición; puede sobrediseñar una pantalla simple; hay que evitar
que componentes almacenen estado static o sean singleton entre escenarios.

**Cuándo usarla:** el disparador se repite en filas/tarjetas o aparece en varias páginas.

### 5. Coordinador de tabs con alcance de escenario

Un objeto inyectado por PicoContainer puede administrar varias pestañas de un flujo no lineal. Debe
ser por escenario y usar el contexto ya creado por `DriverFactory`, nunca crear lifecycle propio.

```java
public final class ScenarioTabs implements AutoCloseable {
  private final Map<String, Page> owned = new LinkedHashMap<>();

  public Page open(String name, Page source, Runnable action) {
    if (source.context() != DriverManager.context()) {
      throw new IllegalArgumentException("Contexto de otro escenario");
    }
    Page popup = source.waitForPopup(action);
    owned.put(name, popup);
    return popup;
  }

  @Override public void close() {
    for (Page popup : List.copyOf(owned.values())) {
      if (!popup.isClosed()) popup.close();
    }
    owned.clear();
  }
}
```

**Ventajas:** ownership y cierre centralizados, útil para múltiples popups o popups iniciados por
otra página, auditable para detectar fugas.

**Desventajas:** estado mutable adicional, mayor complejidad y acoplamiento a DI; si se vuelve static,
singleton o `ThreadLocal` adicional causa contaminación. `BrowserContext.waitForPage` es menos
específico que `page.waitForPopup`, por lo que puede capturar una página no deseada si dos aperturas
ocurren concurrentemente en el mismo contexto.

**Cuándo usarla:** flujos realmente multitab, no como abstracción por defecto.

### 6. Cierre controlado y retorno explícito al original

No existe `switchTo` en Playwright. El retorno consiste en volver a usar el Page Object original;
`bringToFront()` sólo es necesario si el comportamiento visual/foco del sitio lo exige.

```java
public void validarDetalleYCerrar() {
  try (DetailPage detail = resultsPage.abrirDetalle()) {
    detail.validarNumeroDePoliza(numeroEsperado);
  }

  // resultsPage todavía contiene la Page original.
  resultsPage.esperarResultadosVisibles();
  // Usar sólo si la aplicación depende del foco real:
  // resultsPage.traerAlFrente();
}
```

```java
public void traerAlFrente() {
  page.bringToFront();
}
```

**Ventajas:** ownership visible, fuga improbable, retorno determinista y compatible con paralelo.

**Desventajas:** `try-with-resources` no atraviesa cómodamente múltiples Steps; almacenar el detalle
en una fixture scenario-scoped exige cerrarlo en `@After`; abusar de `bringToFront()` oculta un mal
diseño basado en "página actual".

**Cuándo usarla:** validación completa dentro de una operación o cierre scenario-scoped en el hook.

## Riesgos técnicos y mitigación

| Riesgo | Causa típica | Mitigación verificable |
|---|---|---|
| Race condition | `click(); context.pages()` o registrar listener después del clic | `waitForPopup(action)`; una sola apertura dentro del callback |
| Capturar el tab equivocado | `waitForPage` genérico con varias aperturas | preferir `sourcePage.waitForPopup`; verificar URL y elemento distintivo |
| "Cambio de foco" | variable global `currentPage` o asumir el último tab | PO por `Page`; volver a usar el PO original; `bringToFront` sólo por necesidad funcional |
| Estado mutable compartido | `static Page`, singleton de tabs, ScenarioContext global | alcance PicoContainer por escenario y campos de instancia; nada Playwright cruza hilos |
| Fuga de páginas | no cerrar popups o fallar antes del cierre | `AutoCloseable`, cleanup scenario-scoped y cierre final del contexto |
| Espera incorrecta | `Thread.sleep`, `waitForTimeout`, `NETWORKIDLE` universal | auto-wait de locators + URL/heading/dato de negocio; timeout acotado por framework |
| Popup temprano cerrado | llamada a `close()` sobre Page ya cerrada | comprobar `page.isClosed()` en cleanup idempotente |
| Operación desde otro hilo | futures/executors o compartir driver entre escenarios | todas las llamadas Playwright en el hilo del escenario; un contexto por escenario |
| Assertion prematura | validar sólo `DOMCONTENTLOADED` | esperar el locator/dato que representa pantalla lista antes de afirmar |

`DOMCONTENTLOADED` indica estado del documento, no que una SPA haya terminado de renderizar. Los
locators ya realizan checks de actionability; añadir esperas arbitrarias puede alargar la suite sin
reducir flakiness.

## Recomendación priorizada

### Top 1: transición tipada `OrigenPage -> DetailPage`

Usar la estrategia 3, con captura local atómica. Añadir el helper de la estrategia 2 sólo cuando
existan al menos dos o tres usos idénticos. Es la mejor decisión cuando el destino pertenece al
dominio, los Steps posteriores interactúan con él y se quiere proteger el diseño POM.

**Criterios:** mayor cohesión, Steps sin `Page`, condición de readiness específica, referencias por
instancia y comportamiento estable en paralelo.

### Top 2: destino `AutoCloseable` + ownership scenario-scoped

Combinar las estrategias 3 y 6. Usar `try-with-resources` si abrir-validar-cerrar cabe en una llamada
de negocio; si cruza Steps, inyectar una fixture de escenario que almacene el `DetailPage` y cerrarla
en `@After`. Adoptar el coordinador 5 únicamente si hay varias pestañas simultáneas o aperturas no
atribuibles a una página.

**Criterios:** ciclo de vida inequívoco, ausencia de fugas, cleanup idempotente y evidencia sencilla
de aislamiento.

## Antipatrones a evitar

- `Thread.sleep`, `page.waitForTimeout` o polling manual de `context.pages()`.
- `click()` seguido de `waitForPopup`; la suscripción llega tarde.
- seleccionar `context.pages().get(context.pages().size() - 1)` y llamarlo "tab actual".
- `static Page`, `static List<Page>`, singleton mutable o compartir un `BrowserContext` entre
  escenarios paralelos.
- reemplazar `DriverManager.TL_PAGE` con cada popup: rompe objetos ya construidos y screenshots del
  lifecycle.
- crear `Playwright`, `Browser`, `BrowserContext` o una página inicial desde un Step/Page Object.
- pasar `Page` por `ScenarioContext` sin ownership y cleanup claros.
- listeners permanentes `context.onPage` para una única transición; acumulan handlers y correlacionan
  mal eventos.
- usar `bringToFront()` como sustituto de conservar referencias.
- capturar excepciones de timeout y continuar como si el popup fuera opcional.
- assertions de negocio dentro de `BasePage`, un coordinador de tabs o Steps cargados de locators.
- ejecutar llamadas a Playwright con `CompletableFuture`, pools o callbacks en otro hilo.

## Integración con Cucumber, PicoContainer y TestNG

1. Mantener `DriverFactory.init()` y `DriverManager.cleanup()` exclusivamente en hooks.
2. Construir Page Objects desde `PageProvider.get()` en objetos de glue scenario-scoped; no cachearlos
   estáticamente.
3. Guardar el `DetailPage` en una fixture tipada inyectada cuando deba sobrevivir entre Steps:

   ```java
   public final class DetailFixture implements AutoCloseable {
     private DetailPage detail;
     public void set(DetailPage detail) { this.detail = detail; }
     public DetailPage get() {
       if (detail == null) throw new IllegalStateException("Detalle no abierto");
       return detail;
     }
     @Override public void close() {
       if (detail != null) detail.close();
       detail = null;
     }
   }
   ```

4. Inyectar la misma fixture en Steps y Hooks por constructor. En un `@After(order = 100)` cerrar la
   fixture **antes** de `DriverManager.cleanup()`; el cleanup del contexto queda como última red.
5. No confiar en que `ThreadLocal` convierte Playwright en thread-safe: sólo confina la referencia.
   Cada escenario debe iniciar, usar y cerrar su instancia en el mismo worker de TestNG.
6. Mantener `@DataProvider(parallel = true)` y variar `-Ddp.threads=N`; no añadir paralelismo interno
   a un escenario.
7. Los Steps expresan negocio (`fixture.get().validarPoliza(...)`), mientras la localización, espera,
   apertura y cierre viven en objetos UI/fixture.
8. Adaptar screenshots para elegir explícitamente la página relevante si la evidencia debe mostrar
   el popup. El `DriverManager.page()` actual representa la página inicial, no automáticamente el
   detalle.

## Checklist previo a implementar

### Arquitectura

- [ ] ¿El destino tiene comportamiento suficiente para ser un Page Object?
- [ ] ¿El enlace pertenece a una Page o a un componente reutilizable?
- [ ] ¿El método de origen retorna un tipo de dominio y no expone `Page` al Step?
- [ ] ¿Está definido quién es owner del popup y quién lo cierra ante éxito y fallo?
- [ ] ¿No se crea infraestructura fuera de hooks/factory?
- [ ] ¿La evidencia debe capturar original, popup o ambos?

### Sincronización

- [ ] ¿`waitForPopup`/`waitForPage` envuelve exactamente la acción disparadora?
- [ ] ¿Se valida URL y al menos una señal específica de readiness?
- [ ] ¿Se usan locators y auto-wait en vez de sleeps?
- [ ] ¿Los timeouts provienen de configuración y generan diagnóstico útil?
- [ ] ¿El flujo contempla popup bloqueado, navegación fallida o cierre temprano?

### Paralelismo

- [ ] ¿Contexto, pages, Page Objects y fixtures son por escenario?
- [ ] ¿No existen campos static mutables con objetos Playwright?
- [ ] ¿No se cambia de hilo dentro de un escenario?
- [ ] ¿El cleanup es idempotente y corre incluso tras una assertion fallida?
- [ ] ¿Cada artifact incluye scenario id, thread id y URLs para detectar cruces?

### Assertions de negocio

- [ ] ¿Se valida un dato que vincula popup y escenario (póliza/cliente/id), no sólo el título?
- [ ] ¿La assertion ocurre después de la condición observable correcta?
- [ ] ¿Los mensajes de fallo incluyen esperado, actual y URL del popup?
- [ ] ¿Tras cerrar se comprueba que el original conserva sesión y estado esperado?

## Plan de pruebas técnicas

### 1. Contrato unitario/integración de apertura

- Servir una página determinista que abra `/detalle/{scenarioId}` con `target=_blank`.
- Afirmar que el método retorna `DetailPage`, que su URL contiene el id y que la original sigue
  abierta.
- Repetir con popup de carga rápida para demostrar que no existe carrera.
- Provocar destino inválido y comprobar que falla por la señal específica, no por `NullPointerException`.

### 2. Cierre y fallos

- Abrir, validar y cerrar; afirmar `popup.isClosed()` y `context.pages().size() == 1`.
- Forzar una assertion fallida después de abrir; el `@After` debe dejar cero pages antes de cerrar el
  contexto o, como mínimo, confirmar que el contexto las cierra.
- Ejecutar doble cleanup y popup autocerrado para verificar idempotencia.

### 3. Paralelo

- Crear al menos 20 escenarios/datasets, cada uno con un UUID distinto visible en original y popup.
- Ejecutar primero con `-Ddp.threads=1`, luego `2`, `4` y el máximo usado en CI; repetir varias veces.
- En cada escenario afirmar que URL, heading y dato de negocio contienen **su** UUID.
- Registrar `scenarioId`, `Thread.currentThread().getId()`, identidad del contexto, URL original y
  URL popup en apertura/cierre.

### 4. Evidencias de no contaminación

Para cada ejecución conservar:

- tabla JSON/CSV por escenario con thread id, context id, page id lógico, URL y timestamps;
- screenshot del popup nombrado con scenario id y screenshot del original tras cerrar;
- trace/video por contexto cuando la política CI lo permita;
- conteo `context.pages()` antes de abrir, después de abrir y después de cerrar (esperado `1/2/1`);
- assertion de unicidad: ningún scenario id aparece en artifacts o URLs de otro escenario;
- reporte de cleanup sin tabs pendientes y sin errores tipo "Target/Page/Context has been closed".

La prueba técnica incluida reutiliza `testng.xml`, `RunnerCucumberTest`, los hooks y el lifecycle
normal del proyecto. Seleccionarla por tag evita introducir una segunda suite TestNG:

```bash
mvn test -Ddp.threads=4 -Dcucumber.filter.tags="@popup"
```

La prueba sólo es concluyente si solapa escenarios de forma real y repite lo suficiente para hacer
visibles carreras; un único pase verde con un hilo no valida aislamiento.

## Tabla de decisión ponderada

Escala: 0 (muy deficiente) a 5 (excelente). En **acoplamiento**, 5 significa Steps muy desacoplados
de Playwright y detalles UI. Pesos: mantenibilidad 25 %, legibilidad 20 %, estabilidad/anti-flakiness
25 %, paralelo/aislamiento 20 %, bajo acoplamiento 10 %.

| Estrategia | Mant. 25 % | Legib. 20 % | Estab. 25 % | Paralelo 20 % | Bajo acopl. 10 % | Total / 5 |
|---|---:|---:|---:|---:|---:|---:|
| 1. Captura local, retorna `Page` | 3 | 4 | 5 | 5 | 3 | **4.10** |
| 2. Helper técnico en `BasePage` | 4 | 4 | 5 | 5 | 4 | **4.45** |
| 3. Retorno de `DetailPage` | 5 | 5 | 5 | 5 | 5 | **5.00** |
| 4. Componente retorna destino | 4 | 5 | 5 | 5 | 5 | **4.75** |
| 5. Coordinador scenario-scoped | 3 | 3 | 4 | 4 | 4 | **3.75** |
| 6. Destino `AutoCloseable` y retorno | 4 | 4 | 5 | 5 | 4 | **4.45** |

Los puntajes comparan el uso idiomático de cada estrategia. No significa que deban excluirse entre
sí: la solución recomendada compone **3 + 6**, y agrega **2** o **4** sólo donde la reutilización lo
justifique. La estrategia 5 gana valor cuando el flujo exige varias tabs vivas, pero no debe ser la
abstracción inicial para un único "Ver más".
