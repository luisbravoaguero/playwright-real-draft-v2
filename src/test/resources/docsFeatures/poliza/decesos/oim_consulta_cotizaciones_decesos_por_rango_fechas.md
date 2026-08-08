# Feature: Consulta de Cotizaciones del Ramo Decesos por Rango de Fechas

### 📌 Descripción
Este feature automatiza el flujo completo de consulta de cotizaciones del ramo decesos
por rango de fechas, desde el acceso a la plataforma OIM hasta la visualización
de las cotizaciones generadas en el período especificado.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Pólizas desde la página Home
3. Selección de la opción Decesos en la página Pólizas
4. Selección de la opción Consultar Cotizaciones de Decesos en la página Decesos
5. Completación del formulario con fechas de inicio y fin
6. Visualización de las cotizaciones de Decesos generadas

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Pólizas
- Navegación correcta a la sección Decesos
- Carga correcta del formulario de consulta
- Rango de fechas validado correctamente
- Visualización correcta de las cotizaciones de Decesos

### ❌ No cubre
- Generación de nuevas cotizaciones de Decesos
- Modificación de cotizaciones existentes
- Descarga de reportes
- Flujos de rechazo o error en consultas
- Emisión de pólizas

## 📄 Feature asociado
- `oim_consulta_cotizaciones_decesos.feature`

## 📊 Datos de prueba
- Fecha de inicio: 01/04/2026
- Fecha de fin: 24/04/2026
