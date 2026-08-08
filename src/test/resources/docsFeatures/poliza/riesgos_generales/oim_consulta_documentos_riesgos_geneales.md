# Feature: Consulta de Documentos del Ramo Riesgos Generales por Rango de Fechas

### 📌 Descripción
Este feature automatiza el flujo completo de consulta de documentos del ramo Riesgos Generales
por rango de fechas, desde el acceso a la plataforma OIM hasta la visualización
de los documentos generados en el período especificado.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Pólizas desde la página Home
3. Selección de la opción Riesgos Generales en la página Pólizas
4. Selección de la opción Consulta Documentos de Riesgos Generales en la página Riesgos Generales
5. Completación del formulario con fechas de inicio y fin
6. Visualización de los documentos de Riesgos Generales generados

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Pólizas
- Navegación correcta a la sección Riesgos Generales
- Carga correcta del formulario de consulta
- Rango de fechas validado correctamente
- Visualización correcta de los documentos de Riesgos Generales

### ❌ No cubre
- Generación de nuevos documentos de Riesgos Generales
- Modificación de documentos existentes
- Descarga de reportes
- Flujos de rechazo o error en consultas
- Emisión de pólizas

## 📄 Feature asociado
- `oim_consulta_documento_riesgos_generales.feature`

## 📊 Datos de prueba
- Fecha de inicio: 01/04/2026
- Fecha de fin: 30/04/2026
```