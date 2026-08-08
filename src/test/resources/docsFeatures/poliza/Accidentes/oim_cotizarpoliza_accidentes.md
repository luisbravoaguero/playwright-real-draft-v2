# Feature: Cotización Póliza Accidentes

### 📌 Descripción

Este feature automatiza el flujo de **cotización de una póliza de Accidentes** en OIM,  
desde el ingreso al sistema hasta la **generación y guardado de la cotización**.

Incluye el registro de:

- Datos del contratante
- Configuración del riesgo
- Selección de coberturas principales
- Validación de la cotización generada

El flujo valida la correcta interacción con componentes dinámicos de Angular  
y asegura que el sistema procese adecuadamente la información ingresada.

---

### 🧭 Flujo cubierto

1. Ingreso a OIM con credenciales válidas
2. Navegación al módulo **Pólizas**
3. Selección de la opción **Accidentes**
4. Acceso a la opción **Cotizar Póliza Accidentes**
5. Registro de datos del contratante:
    - Tipo de documento
    - Número de documento
    - Nombres y apellidos
6. Validación de autocompletado de cliente o ingreso manual de datos
7. Registro de datos del riesgo:
    - Actividad económica (ej. AGRICULTURA)
    - Número de asegurados
    - Exposición al riesgo
    - Moneda
    - Fecha de inicio de vigencia
8. Registro de coberturas principales:
    - Muerte accidental
    - Invalidez permanente
    - Incapacidad temporal diaria
    - Gastos de curación
    - Gastos de sepelio
9. Agregado del riesgo a la cotización
10. Validación de la sección **Riesgos agregados**
11. Guardado de la cotización
12. Visualización de la pantalla de **Cotización guardada**
13. Obtención del número de cotización generado

---

### ✅ Validaciones clave

- Acceso correcto al sistema OIM
- Navegación adecuada al módulo Accidentes
- Correcto ingreso de datos del contratante
- Funcionamiento del autocompletado del cliente
- Registro correcto de la actividad económica
- Configuración correcta de los datos del riesgo
- Ingreso válido de coberturas principales
- Agregado exitoso del riesgo
- Visualización de la sección **Riesgos agregados**
- Ejecución correcta del guardado de cotización
- Visualización de la pantalla de cotización guardada
- Generación del número de cotización

---

# ❌ No cubre

- Emisión de la póliza
- Carga de asegurados mediante Excel
- Validación de primas finales
- Pago de la póliza
- Generación de documentos PDF
- Envío de la cotización por correo
- Flujos de rechazo o modificación de cotización
- Anulación de cotizaciones

---

## 📄 Feature asociado

- `oim_cotizarpoliza_accidentes.feature`