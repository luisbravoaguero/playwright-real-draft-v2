# Feature: Cotización y Emisión Póliza Vida Ley

### 📌 Descripción
Este feature automatiza el flujo completo de cotización y emisión de una póliza de Vida Ley,
desde el ingreso de datos del contratante hasta la generación final del número de póliza emitida.

Incluye un proceso de emisión backend pesado, que puede demorar varios minutos.

### 🧭 Flujo cubierto
1. Selección de actividad económica
2. Registro manual del asegurado
3. Obtención de coberturas
4. Manejo de observaciones RENIEC
5. Confirmación de guardado
6. Aceptación de cotización
7. Generación del número de cotización
8. Confirmación de datos para emisión
9. Proceso de emisión de la póliza
10. Generación del número de póliza emitida

### ✅ Validaciones clave
- Campos obligatorios del contratante y contacto
- Manejo de modales (RENIEC y confirmación)
- Avance correcto del wizard de cotización
- Habilitación del botón Emitir
- Ejecución correcta del proceso de emisión
- Generación y visualización del número de póliza

# ❌ No cubre
- Pago de la póliza
- Descarga de documentos (PDF, recibos, constancias)
- Envío de póliza por correo
- Flujos alternos de rechazo o anulación

## 📄 Feature asociado
- `oim_cotizaryemitir_vidaley.feature`