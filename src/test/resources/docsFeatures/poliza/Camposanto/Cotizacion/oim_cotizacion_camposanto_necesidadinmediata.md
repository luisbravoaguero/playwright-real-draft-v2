# Feature: Cotización Camposanto Necesidad Inmediata

### 📌 Descripción

Este feature automatiza el flujo de **cotización y emisión de una póliza de Camposanto - Necesidad Inmediata** en OIM,  
desde el ingreso al cotizador hasta la **generación de la cotización, validación del resultado y emisión de la póliza**.

Incluye el registro de:

- Datos del producto
- Datos del cliente (autocompletado o manual)
- Configuración de información requerida
- Generación de la cotización
- Validación del resultado y número generado
- Emisión de la póliza

El flujo valida la correcta interacción con componentes dinámicos de Angular  
(selects, autocompletados, inputs dependientes) y asegura que el sistema procese correctamente la información hasta la emisión final.

---

### 🧭 Flujo cubierto

1. Ingreso al módulo **Cotizador Camposanto**
2. Validación de la pantalla **Ramo: Necesidad Inmediata**
3. Registro de datos del producto:
   - Camposanto
   - Tipo de contrato
   - Modalidad
   - Producto
4. Registro de datos del cliente:
   - Tipo de documento
   - Número de documento
5. Validación de:
   - Autocompletado de cliente desde BD  
     **o**
   - Ingreso manual de datos:
      - Nombres
      - Apellidos
      - Fecha de nacimiento (día, mes, año)
      - Estado civil
      - Celular
      - Correo
      - Ubicación (departamento, provincia, distrito)
      - Dirección
6. Validación y completado de campos faltantes (si aplica):
   - Estado civil
   - Celular
7. Generación de la cotización:
   - Click en **Generar Cotización**
8. Manejo del modal de confirmación
9. Visualización de la pantalla de resultado:
   - Detalle de cotización: Necesidad Inmediata
10. Validación del número de cotización generado
11. Validación del botón **Ir a bandeja**
12. Navegación al flujo de emisión
13. Registro de datos complementarios para emisión:
   - Check de documentos
   - Datos del tomador
   - Datos del beneficiario
   - Datos adicionales
14. Ejecución de la emisión de la póliza:
   - Click en **Emitir**
15. Manejo de escenarios de negocio:
   - Reintento en caso de número de contrato duplicado
16. Validación del resultado de emisión:
   - Visualización del mensaje **"Póliza Emitida"**
   - Confirmación mediante modal
17. Finalización del flujo de emisión

---

### ✅ Validaciones clave

- Visualización correcta del ramo **Necesidad Inmediata**
- Correcta selección de datos del producto
- Ingreso válido del número de documento
- Funcionamiento del autocompletado del cliente
- Registro correcto de datos manuales (cuando aplica)
- Validación de campos obligatorios y dependientes
- Funcionamiento de selects dinámicos Angular
- Ejecución correcta de la generación de cotización
- Aparición del modal de confirmación
- Visualización del detalle de cotización
- Generación correcta del número de cotización
- Disponibilidad del botón **Ir a bandeja**
- Registro correcto de datos en la etapa de emisión
- Manejo de errores de negocio (contrato duplicado)
- Ejecución correcta de la emisión
- Visualización del mensaje **Póliza Emitida**

---

# ❌ No cubre

- Validación de primas finales
- Flujo de pago
- Generación de documentos
- Integración con sistemas externos
- Validación avanzada de reglas de suscripción
- Modificación de póliza emitida
- Anulación de póliza
- Envío de documentos por correo

---

## 📄 Feature asociado

- `oim_cotizacion_camposanto_necesidadinmediata.feature`
