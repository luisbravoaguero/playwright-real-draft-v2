# Feature: Constancia Manual SCTR Minería para Pensión y Salud

### 📌 Descripción
Este feature automatiza el flujo completo de generación de una constancia manual de una póliza SCTR Minería
para pensión y salud en modalidad mes adelantado, desde el acceso a la plataforma OIM
hasta la confirmación del número de constancia manual generada.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Constancias SCTR y VL desde la página Home
3. Selección de la opción SCTR Minería en la página Constancias SCTR y VL
4. Búsqueda y selección de la póliza por número en la página Clientes
5. Selección de la opción Constancia Manual en la página Perfil Cliente
6. Apertura del modal Generar Nueva Constancia Manual
7. Completación de la fecha de cobertura y carga de asegurados minería
8. Generación de la constancia manual

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Constancias SCTR y VL
- Identificación correcta de la póliza SCTR Minería
- Carga correcta de datos de asegurados minería
- Generación y visualización del número de constancia manual

### ❌ No cubre
- Emisión de nuevas pólizas SCTR
- Modificación de pólizas existentes
- Descarga de documentos
- Flujos de rechazo o anulación de constancias
- Consultas o reportes
- Constancias de pólizas SCTR General

## 📄 Feature asociado
- `oim_constancia_manual_sctr.feature`

## 📊 Datos de prueba
- Número de póliza: 7012600000900
- Tipo: SCTR Minería
- Modalidad: Mes Adelantado
