# Feature: Inclusión de Póliza SCTR para Pensión y Salud

### 📌 Descripción
Este feature automatiza el flujo completo de inclusión de una póliza SCTR para pensión y salud
en modalidad mes adelantado con tipo de carga individual, desde el acceso a la plataforma OIM
hasta la confirmación del resumen de la aplicación incluida.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Constancias SCTR y Vida Ley
3. Selección de la opción SCTR General
4. Búsqueda y selección de la póliza por número
5. Selección de aplicación desde el Perfil Cliente
6. Completación del formulario Datos de los asegurados
7. Completación del formulario Información de Inclusión
8. Selección del botón Generar
9. Visualización del resumen de la aplicación incluida

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Constancias SCTR y VL
- Identificación correcta de la póliza
- Carga correcta de datos de asegurados
- Información de inclusión validada
- Generación y visualización del resumen de la aplicación incluida

### ❌ No cubre
- Emisión de nuevas pólizas SCTR
- Modificación de pólizas existentes
- Descarga de documentos
- Flujos de rechazo o anulación de inclusiones
- Consultas o reportes
- Declaraciones de pólizas

## 📄 Feature asociado
- `oim_constancia_inclusion_sctr.feature`

## 📊 Datos de prueba
- Número de póliza: 7012600002199
