# Feature: Cotización Póliza FOLA

### 📌 Descripción
Este feature automatiza el flujo completo de cotización de una póliza FOLA,
desde el ingreso de datos del asegurado hasta la generación del resumen de cotización.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Pólizas desde la página Home
3. Selección de la opción FOLA en la página Pólizas
4. Selección de la opción Cotizar póliza FOLA en la página FOLA
5. Ingreso de datos en la sección Información General
6. Registro de datos en la sección Asegurados
7. Generación del resumen de cotización

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Navegación correcta al módulo de Pólizas y sección FOLA
- Validación de campos obligatorios en Información General
- Validación de datos ingresados en la sección Asegurados
- Generación correcta del resumen de cotización

### ❌ No cubre
- Pago de la póliza
- Emisión de la póliza
- Flujos alternos de rechazo o error en la cotización

## 📄 Feature asociado
- `oim_cotizacion_fola.feature`

## 📊 Datos de prueba
- Número de RUC: 20558476462
- Razón Social: EMPRESA DE PRUEBA S.A
- Tipo de Fraccionamiento: SEMESTRAL
- Cantidad de Asegurados: 3
- Actividad: EMPLEADO DE OFICINA
- Subvención por Persona: 350