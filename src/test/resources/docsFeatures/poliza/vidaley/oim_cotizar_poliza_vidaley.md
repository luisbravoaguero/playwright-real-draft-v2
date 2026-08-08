# Feature: Cotización Póliza Vida Ley

### 📌 Descripción
Este feature automatiza el flujo completo de cotización de una póliza de Vida Ley,
desde el ingreso de datos del asegurado hasta la generación del número de cotización.

### 🧭 Flujo cubierto
1. Selección de actividad económica
2. Registro manual del asegurado
3. Obtención de coberturas
4. Manejo de observaciones RENIEC
5. Confirmación de guardado
6. Aceptación de cotización
7. Generación del número de cotización

### ✅ Validaciones clave
- Campos obligatorios del asegurado
- Manejo de modales (RENIEC y confirmación)
- Habilitación de botones según estado
- Generación correcta del número de cotización

# ❌ No cubre
- Pago de la póliza
- Emisión de la póliza
- Flujos alternos de rechazo o anulación

## 📄 Feature asociado
- `oim_cotizar_poliza_vidaley.feature`