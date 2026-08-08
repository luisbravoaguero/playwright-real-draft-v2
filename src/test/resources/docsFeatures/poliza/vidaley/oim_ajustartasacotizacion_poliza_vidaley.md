# Feature: Ajuste de Tasa Cotización Póliza Vida Ley

### 📌 Descripción

Este feature automatiza el flujo de **ajuste de tasa de una cotización de Vida Ley**,
desde la generación inicial de la cotización hasta la **evaluación, aprobación de la nueva tasa**
y la **aceptación final de la cotización ajustada**.

Incluye navegación por la **Bandeja de Documentos**, manejo de **modales de confirmación**
y validación de los **estados de la cotización** durante el proceso de evaluación.

---

### 🧭 Flujo cubierto

1. Ingreso a OIM con credenciales válidas
2. Navegación al módulo **Pólizas → Vida Ley**
3. Inicio de cotización Vida Ley
4. Ingreso de datos del contratante
5. Selección de actividad económica
6. Configuración de duración y declaración del seguro
7. Registro manual del asegurado
8. Generación de la cotización inicial
9. Solicitud de **reajuste de tasa**
10. Confirmación del modal de evaluación de tasa
11. Navegación a **Bandeja de Documentos**
12. Búsqueda de la cotización con estado **SOLICITUD EVALUACION**
13. Selección de **Ver Cotización**
14. Ingreso de la **tasa final**
15. Aceptación de la solicitud de evaluación
16. Confirmación del modal **“Cotización aceptada”**
17. Navegación nuevamente a **Bandeja de Documentos**
18. Búsqueda de la cotización con estado **SOLICITUD ATENDIDA**
19. Selección de **Ver Cotización**
20. Aceptación final de la cotización
21. Generación y visualización del **número de cotización ajustada**

---

### ✅ Validaciones clave

- Campos obligatorios del contratante y contacto
- Generación de la cotización inicial
- Habilitación del botón **Solicitar reajuste**
- Manejo correcto del modal de evaluación de tasa
- Persistencia del estado **SOLICITUD EVALUACION**
- Acceso correcto a la **Bandeja de Documentos**
- Ingreso y validación de la **tasa final**
- Manejo del modal **“Cotización aceptada”**
- Cambio de estado a **SOLICITUD ATENDIDA**
- Aceptación final de la cotización
- Generación del número de cotización ajustada

---

# ❌ No cubre

- Emisión de la póliza
- Pago de la póliza
- Descarga de documentos (PDF, constancias)
- Envío de cotización o póliza por correo
- Flujos alternos de rechazo de tasa
- Anulación de cotizaciones

---

## 📄 Feature asociado

- `oim_ajustartasacotizacion_vidaley.feature`