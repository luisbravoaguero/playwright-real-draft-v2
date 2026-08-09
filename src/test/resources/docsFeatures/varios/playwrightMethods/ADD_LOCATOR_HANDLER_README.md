# AddLocatorHandler Documentation

## Flujo de Ejecución en el Método `fillName()` de BondaracademyPage

### Cómo funciona el `page.addLocatorHandler()` en Playwright

---

### Paso 1: clickAndSync(modalAndOverloadLink)
```
↓
No handler exists yet
```
Se ejecuta normalmente sin ningún handler registrado.

---

### Paso 2: clickAndSync(dialogLink)
```
↓
No handler exists yet
```
Se ejecuta normalmente sin ningún handler registrado.

---

### Paso 3: clickAndSync(enterNameButton)
```
↓
No handler exists yet
```
Se ejecuta normalmente sin ningún handler registrado.

---

### Paso 4: page.addLocatorHandler(friendlyReminderLabel, handler)
```
↓
Handler is REGISTERED
↓
It does NOT wait
It does NOT necessarily execute
↓
Java continues
```

**Puntos Clave:**
- El handler se **registra** pero no se ejecuta inmediatamente
- No bloquea la ejecución (no es asincrónico)
- Simplemente prepara Playwright para detectar el localizador en futuras acciones
- El handler permanece activo para todas las acciones posteriores

---

### Paso 5: fillAndSync(enterNameInput, name)
```
↓
Before Playwright performs/retries
the actionability checks:
↓
"Is friendlyReminderLabel visible?"
     │
     ├── NO → perform fill normally
     │
     └── YES → execute handler
                  ↓
                click OK
                  ↓
                continue fill
```

**Lo que ocurre:**
1. Antes de llenar el input, Playwright realiza checks de "actionability"
2. Verifica si el `friendlyReminderLabel` está visible
3. Si **SÍ está visible** → ejecuta el handler (click OK) → continúa con el fill
4. Si **NO está visible** → realiza el fill normalmente sin ejecutar el handler

---

### Paso 6: clickAndSync(submitButton)
```
↓
Before Playwright performs/retries
actionability checks:
↓
"Is friendlyReminderLabel visible?"
     │
     ├── NO → click Submit
     │
     └── YES → execute handler
                  ↓
                click OK
                  ↓
                continue Submit click
```

**Lo que ocurre:**
1. Antes de hacer click en Submit, Playwright realiza checks de "actionability"
2. Verifica si el `friendlyReminderLabel` está visible
3. Si **SÍ está visible** → ejecuta el handler (click OK) → continúa con el click en Submit
4. Si **NO está visible** → realiza el click normalmente sin ejecutar el handler

---

## Resumen Técnico

| Propiedad | Descripción |
|-----------|-------------|
| **Cuándo se registra** | Cuando se invoca `page.addLocatorHandler()` |
| **Cuándo se ejecuta** | Solo durante acciones posteriores (fill, click, etc.) |
| **Ejecución automática** | Sí, si el localizador es detectado antes de la acción |
| **Bloqueo de código** | NO - el registro es instantáneo |
| **Validez** | Permanece activo para todas las futuras interacciones |
| **Caso de uso** | Manejar pop-ups, modales o overlays inesperados |

---

## Código Implementado

```java
page.addLocatorHandler(friendlyReminderLabel, locator -> {
    log.info(">>> LOCATOR HANDLER EXECUTED <<<");
    okButton.click();
});
```

Este handler:
- Monitorea la visibilidad del `friendlyReminderLabel`
- Se ejecuta automáticamente si el label es visible ANTES de `fillAndSync()` o `clickAndSync()`
- Hace click en el botón OK para cerrar el modal/reminder
- Permite que la acción principal (fill o click) continúe sin interrupciones

