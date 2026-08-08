# Feature: Declaración de una Póliza SCTR con Renovación para Pensión y Salud Mes Adelantado y Tipo de Carga Individual

### 📌 Descripción
Este feature automatiza el flujo completo de declaración de una póliza SCTR existente con renovación,
cubriendo pensión y salud en modalidad mes adelantado con tipo de carga individual, desde el acceso a la plataforma OIM
hasta la confirmación del resumen de la declaración.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Constancias SCTR y Vida Ley
3. Selección de la opción SCTR General
4. Búsqueda y selección de la póliza por número para declarar con renovación
5. Selección de aplicación desde el Perfil Cliente (con renovación)
6. Completación del formulario Datos de los asegurados
7. Completación del formulario Información de Declaración
8. Selección del botón Declarar
9. Visualización del resumen de la declaración con renovación

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Constancias SCTR y VL
- Identificación correcta de la póliza por número
- Acceso correcto a opción SCTR General
- Selección de aplicación con renovación disponible
- Carga correcta de datos de asegurados
- Información de declaración validada
- Generación y visualización del resumen de declaración
- Confirmación de renovación para pensión y salud
- Validación de modalidad mes adelantado
- Confirmación de tipo de carga individual

### ❌ No cubre
- Emisión de nuevas pólizas SCTR
- Modificación de pólizas existentes
- Descarga de documentos
- Flujos de rechazo o anulación de declaraciones
- Consultas o reportes de declaraciones históricas
- Declaraciones sin renovación
- Declaraciones con tipo de carga diferente a individual

## 📄 Feature asociado
- `oim_constancia_declaracion_sctr.feature`

## 📊 Datos de prueba

| Campo | Valor |
|-------|-------|
| Número Póliza | 7012600002176 |
| Modalidad | Mes Adelantado |
| Tipo de Carga | Individual |
| Cobertura | Pensión y Salud |
| Tipo Flujo | Declaración con Renovación |

---

## 🔗 Referencias
- Workspace: `oim_automatizacion`
- Ubicación Feature: `src/test/resources/features/contaciasSctrVidaLey/sctr/`
- Ubicación Documentación: `src/test/resources/docsFeatures/`
- Última actualización: 2026-06-02
