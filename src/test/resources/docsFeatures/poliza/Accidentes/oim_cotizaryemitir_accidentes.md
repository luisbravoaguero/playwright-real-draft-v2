# Feature: Cotización y Emisión Póliza Accidentes

### 📌 Descripción

Este feature automatiza el flujo completo de **Cotización y Emisión de una póliza de Accidentes** en OIM,  
desde la creación de la cotización hasta la **emisión final de la póliza con asegurados cargados mediante archivo Excel**.

Incluye la gestión de:

- Datos del contratante
- Configuración del riesgo
- Coberturas principales
- Importación de planilla de asegurados
- Confirmación de emisión mediante modal
- Validación de la póliza emitida

Se valida el correcto procesamiento de la emisión considerando la **sincronización con procesos backend (spinner + cálculos)**.

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
6. Validación de autocompletado o ingreso manual de datos
7. Registro de datos del riesgo:
    - Actividad económica (ej. AGRICULTURA)
    - Número de asegurados
    - Exposición al riesgo
    - Moneda
    - Fecha de inicio de vigencia
8. Selección y registro de coberturas principales:
    - Muerte accidental
    - Invalidez permanente
    - Incapacidad temporal diaria
    - Gastos de curación
    - Gastos de sepelio
9. Agregado del riesgo a la cotización
10. Validación de la sección **Riesgos agregados**
11. Guardado de la cotización
12. Selección de la opción **Emitir Póliza**
13. Ingreso de datos adicionales en emisión:
- Tipo documento
- Número documento
- Fecha de nacimiento
14. Validación de pantalla **Cargar asegurados**
15. Importación de archivo Excel de asegurados
16. Subida de la planilla
17. Selección de opción **Emitir póliza**
18. Confirmación en modal **Emitir**
19. Espera de procesamiento:
- Manejo de spinner (UiSync)
- Espera de cálculo de prima
- Espera de carga de tabla de riesgos
20. Visualización de la pantalla **Póliza emitida**
21. Lectura y validación de resultados:
- Estado de emisión
- Condición de la póliza
- Prima total (incluye IGV)

---

### ✅ Validaciones clave

- Validación de acceso correcto a OIM
- Visualización de módulos y navegación correcta
- Ingreso correcto de datos del contratante
- Autocompletado funcional del cliente
- Registro correcto de datos del riesgo
- Selección válida de coberturas principales
- Agregado exitoso del riesgo
- Presencia de la sección **Riesgos agregados**
- Guardado correcto de la cotización
- Disponibilidad del botón **Emitir Póliza**
- Visualización correcta del paso **Cargar asegurados**
- Importación exitosa del archivo Excel
- Subida correcta de la planilla de asegurados
- Apertura y manejo del modal de confirmación de emisión
- Manejo correcto del spinner de carga
- Espera de finalización de procesos backend
- Validación de datos finales en la póliza emitida:
    - Texto **Póliza emitida**
    - Condición **VIGENTE**
    - Prima total diferente de **0.00**
- Lectura de valores reales desde UI (log de resultados)

---

# ❌ No cubre

- Validación de pagos de la póliza
- Generación o descarga de documentos PDF
- Envío de póliza por correo
- Flujos de error en la carga de planilla
- Reintentos de emisión fallida
- Validaciones backend o base de datos
- Anulación de póliza
- Endosos o modificaciones posteriores

---

## 📄 Feature asociado

- `oim_cotizaryemitir_accidentes.feature`