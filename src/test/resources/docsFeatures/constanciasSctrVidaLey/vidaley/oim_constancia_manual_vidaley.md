# Feature: Constancia Manual de Póliza Vida Ley

### 📌 Descripción
Este feature automatiza el flujo completo de generación de una constancia manual de una póliza Vida Ley
en modalidad mes adelantado, desde el acceso a la plataforma OIM
hasta la confirmación del número de constancia manual generada.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Constancias SCTR y VL desde la página Home
3. Selección de la opción Vida Ley en la página Constancias SCTR y VL
4. Búsqueda y selección de la póliza por número en la página Clientes
5. Selección de la opción Constancia Manual en la página Perfil Cliente
6. Apertura del modal Generar Nueva Constancia Manual
7. Completación de la fecha de cobertura y carga de asegurados Vida Ley
8. Generación de la constancia manual

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Constancias SCTR y VL
- Identificación correcta de la póliza Vida Ley
- Carga correcta de datos de asegurados Vida Ley
- Generación y visualización del número de constancia manual

### ❌ No cubre
- Emisión de nuevas pólizas Vida Ley
- Modificación de pólizas existentes
- Descarga de documentos
- Flujos de rechazo o anulación de constancias
- Consultas o reportes
- Constancias de pólizas SCTR

## 📄 Feature asociado
- `oim_constancia_manual_vidaley.feature`

## 📊 Datos de prueba
- Número de póliza: 6102500002576
- Tipo: Vida Ley
- Modalidad: Mes Adelantado
