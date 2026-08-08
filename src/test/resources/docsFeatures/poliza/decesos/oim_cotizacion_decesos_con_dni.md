# Feature: Cotización de una Póliza de Decesos con DNI

### 📌 Descripción
Este feature automatiza el flujo completo de cotización de una póliza de decesos
con documento de identidad (DNI), desde el ingreso de datos de la póliza y contratante
hasta la visualización del resumen de cotización final.

### 🧭 Flujo cubierto
1. Acceso a la plataforma OIM con credenciales válidas
2. Selección del módulo Pólizas desde la página Home
3. Selección de la opción Decesos en la página Pólizas
4. Selección de la opción Cotizar Póliza Decesos en la página Decesos
5. Completación de la sección Datos de la Póliza (producto, póliza grupo, modalidad, medio de pago)
6. Completación de la sección Datos del Contratante (documento, datos personales, dirección)
7. Completación de la sección Asegurados (primer asegurado igual al contratante, segundo asegurado)
8. Selección del botón Cotizar
9. Visualización del Resumen de Cotización Decesos

### ✅ Validaciones clave
- Autenticación exitosa en OIM
- Acceso correcto al módulo de Pólizas
- Navegación correcta a la sección Decesos
- Carga correcta de datos de la póliza
- Validación de datos del contratante con DNI
- Carga correcta de asegurados
- Generación y visualización del resumen de cotización

### ❌ No cubre
- Emisión de nuevas pólizas de Decesos
- Modificación de pólizas existentes
- Descarga de documentos
- Flujos de rechazo o anulación de cotizaciones
- Consultas o reportes

## 📄 Feature asociado
- `oim_cotizacion_decesos.feature`

## 📊 Datos de prueba
- Producto: Seguro de Sepelio
- Póliza Grupo: TEMPORAL (BAS/7MO/CLASI PISCO)
- Modalidad: Plan Integral - 3 Aseg.
- Medio de Pago: Afiliación a Cuenta Bancaria
- Tipo de Documento: DNI
- Número de Documento (Contratante): 11111111
- Estado Civil: Soltero
- Profesión: Administrador
- Teléfono Casa: 5287449
- Teléfono Móvil: 981502678
- Correo Electrónico: pruebas89@gmail.com
- Departamento: Lima
- Provincia: Lima
- Distrito: Comas
- Tipo de Vía: Avda.
- Nombre de Vía: Universitaria
- Número: Nro
- Enumeración: 1234
- Tipo de Documento Asegurado 2: DNI
- Número de Documento Asegurado 2: 22222222
