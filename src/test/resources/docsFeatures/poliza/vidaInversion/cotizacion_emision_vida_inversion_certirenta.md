# Feature: Cotización y Emisión de Póliza Vida Inversión Certirenta - Asegurado es Contratante con DNI

### 📌 Descripción
Este feature automatiza el flujo completo de cotización y emisión de una póliza Vida Inversión Certirenta
cuando el asegurado es el mismo contratante, identificado con DNI, desde el acceso a la plataforma OIM
hasta la confirmación del número de póliza emitida. Incluye configuración de datos bancarios para pago de renta.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Pólizas
3. Selección de la opción Vida Inversión
4. Selección de la opción Cotizar póliza de vida inversión
5. Ingreso de Datos de la Póliza:
   - Datos del Contratante (DNI, nombre, apellidos, teléfono, correo)
   - Datos del Asegurado (automático: iguales al contratante)
   - Datos del Asesor (Gestor/Supervisor y Agente)
   - Características del Seguro (producto, moneda, duración, porcentaje devolución, prima, diferimiento pago, periodicidad pago renta, código promoción)
6. Visualización del Resultado de Cotización
7. Inicio del flujo de Emisión
8. Ingreso de Datos Complementarios del Contratante y Asegurado:
   - Datos Principales (fecha nacimiento, estado civil, país de residencia fiscal)
   - Datos Laborales (sexo, profesión, ocupación)
   - Datos de Contacto (teléfonos, correo electrónico)
   - Datos de Dirección (país, departamento, provincia, distrito, tipo de vía, nombre de vía)
   - Centro de Trabajo
9. Ingreso de Datos Bancarios para Pago de Renta:
   - Entidad Financiera
   - Tipo de Cuenta
   - Número de Cuenta
   - Código del Gestor de la Entidad Financiera
10. Selección de Documentos Requeridos
11. Visualización del Resultado de Emisión con número de póliza generado

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo Pólizas
- Acceso correcto a Vida Inversión
- Carga correcta de datos del contratante con DNI
- Sincronización automática de datos del asegurado con el contratante
- Selección válida de asesor (Gestor/Supervisor y Agente)
- Validación de características del seguro con parámetros Certirenta
- Generación y visualización del número de cotización
- Carga correcta de datos complementarios en emisión
- Validación de datos bancarios para pago de renta
- Selección de documentos obligatorios
- Generación exitosa del número de póliza

### ❌ No cubre
- Cotización sin emisión
- Asegurado diferente al contratante
- Identificación con documentos diferentes a DNI
- Modificación de pólizas existentes
- Anulación o vencimiento de pólizas
- Descarga de documentos de póliza
- Modificación de datos bancarios post-emisión

## 📄 Feature asociado
- `oim_cotizacion_emision_vida_inversion_certirenta.feature`

## 📊 Datos de prueba

| Campo | Valor |
|-------|-------|
| Tipo Documento Contratante | DNI |
| Número Documento Contratante | 58282921 |
| Nombre | AutomatizacionNombre |
| Apellido Paterno | AutomatizacionPaterno |
| Apellido Materno | AutomatizacionMaterno |
| Teléfono Móvil | 945586698 |
| Correo Electrónico | EXTLUBA@MAPFRE.COM.PE |
| Gestor/Supervisor | TODOS |
| Agente | 9808 |
| Tipo Producto | CERTIRENTA |
| Tipo Moneda | SOLES |
| Años Duración Seguro | 10 |
| Porcentaje Devolución | 100 |
| Prima Comercial Única | 100000 |
| Diferimiento de Pago | 3 |
| Periodicidad Pago Renta | MENSUAL |
| Código Promoción | OPCIONAL |
| Fecha Nacimiento | 19/08/1193 |
| Estado Civil | SOLTERO |
| País Residencia Fiscal | PERU |
| Sexo | Masculino |
| Profesión | ABOGADO |
| Ocupación | ABOGADO |
| Prefijo Teléfono | 51 |
| Teléfono Casa | 5251298 |
| Teléfono Oficina | 5872563 |
| País Natal | PERU |
| Departamento | LIMA |
| Provincia | LIMA |
| Distrito | COMAS |
| Tipo Vía | AA.HH. |
| Nombre Vía | LAS FLORES |
| Centro Trabajo | MAPFRE PERU SAC |
| Entidad Financiera | BANCO DE CREDITO DEL PERU |
| Tipo Cuenta | AHORROS |
| Número Cuenta | 55555555555555 |

---

## 🔗 Referencias
- Workspace: `oim_automatizacion`
- Ubicación Feature: `src/test/resources/features/poliza/vidaInversion/`
- Ubicación Documentación: `src/test/resources/docsFeatures/`
- Última actualización: 2026-06-02
