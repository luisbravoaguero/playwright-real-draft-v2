# Feature: Declaración de Póliza Vida Ley Mes Adelantado - Carga Individual

### 📌 Descripción
Este feature automatiza el flujo completo de declaración de una póliza Vida Ley en modalidad mes adelantado y tipo de carga individual, desde el acceso a la plataforma OIM hasta la confirmación del resumen de la declaración.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Constancias SCTR y Vida Ley
3. Selección de la opción Vida Ley
4. Búsqueda y selección de la póliza por número
5. Selección de aplicación desde el Perfil Cliente
6. Completación del formulario Datos de los asegurados
7. Completación del formulario Información de Declaración
8. Selección del botón Declarar
9. Visualización del resumen de la declaración

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Constancias SCTR y VL
- Identificación correcta de la póliza
- Carga correcta de datos de asegurados
- Información de declaración validada
- Generación y visualización del resumen de declaración

### ❌ No cubre
- Emisión de nuevas pólizas Vida Ley
- Modificación de pólizas existentes
- Descarga de documentos
- Flujos de rechazo o anulación de declaraciones
- Consultas o reportes

## 📄 Feature asociado
- `oim_constancia_declaracion_vidaley.feature`

## 📊 Datos de prueba
- Tipo de carga: Individual
