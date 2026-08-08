# Feature: Validar cliente con póliza SCTR y RUC activo para pensión y salud mes adelantado

### 📌 Descripción
Este feature automatiza la validación de un cliente que posee una póliza SCTR vigente y un RUC activo, para los productos de pensión y salud en modalidad mes adelantado, desde el acceso a la plataforma OIM hasta la confirmación del estado de la póliza y el RUC.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Constancias SCTR y VL desde la página Home
3. Selección de la opción SCTR General en la página Constancias SCTR y VL
4. Ingreso del tipo y número de documento del cliente en la página Clientes
5. Validación en la página Perfil Cliente de que la póliza está vigente y el RUC está activo

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Constancias SCTR y VL
- Ingreso correcto de tipo y número de documento
- Validación de vigencia de la póliza SCTR
- Validación de estado activo del RUC

### ❌ No cubre
- Declaración de pólizas SCTR
- Emisión de nuevas pólizas
- Modificación de pólizas existentes
- Descarga de documentos
- Flujos de rechazo o anulación
- Consultas o reportes

## 📄 Feature asociado
- `oim_constancia_declaracion_sctr.feature`

## 📊 Datos de prueba
- Tipo de documento: RUC
- Número de documento: 20538995364
