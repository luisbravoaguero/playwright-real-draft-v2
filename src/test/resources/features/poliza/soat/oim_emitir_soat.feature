@RegresionSoat
Feature:Cotizar y emitir una póliza soat con DNI

 @EmitirSoatAutomovil @Regresion
  Scenario Outline: Cotizar y emitir una póliza soat con DNI
   Given el usuario accede a la pagina OIM con credenciales validas
   When desde la pagina Home selecciona el modulo Polizas
   And en la pagina Polizas selecciona la opcion Soat
   And en la pagina SOAT selecciona la opcion Emitir póliza soat
   And en la pagina Emision poliza SOAT se ingresa los datos numero de placa "<placa>", tipo de vehiculo "<tipo_vehiculo>", marca y modelo "<marca_modelo>", numero de chasis "<chasis>", anio de fabricacion "<anio_fabricacion>", producto "<producto>", tipo de uso "<tipo_uso>", Nro asientos "<Nro_asientos>"
   And en la pagina Emision poliza SOAT se ingresa los datos del contratante tipo de documento "<tipo_documento>", Nro documento "<nro_documento>"
   And en la pagina Emision poliza SOAT se ingresa los datos principales
   And en la pagina Emision poliza SOAT se ingresa los datos de contacto
   And en la pagina Emision poliza SOAT se ingresa los datos de direccion
   And en la pagina Emision poliza SOAT seleccionar la opcion CALCULAR PRIMA
   And en la pagina Emision poliza SOAT se EMITE SOAT
   Then en la pagina Emision poliza SOAT se muestra los datos de la EMISION DEL SOAT

   Examples:
    |placa   |tipo_vehiculo|marca_modelo|chasis                 |anio_fabricacion   |producto         |tipo_uso    |Nro_asientos |tipo_documento|nro_documento|
    |        | AUTOMOVIL   |KIA RIO     |                       |2024               |SOAT ELECTRONICO |PARTICULAR  |5            |DNI           |45228115     |
  #Los campos PLACA y CHASIS se generarán automáticamente cuando se envíen con valores nulos o en blanco; en caso contrario,
  # se utilizarán los valores proporcionados en el example.


 @EmitirSoatMotocicleta @Regresion
 Scenario Outline: Cotizar y emitir una póliza soat MOTOCLICLETA con DNI
  Given el usuario accede a la pagina OIM con credenciales validas
  When desde la pagina Home selecciona el modulo Polizas
  And en la pagina Polizas selecciona la opcion Soat
  And en la pagina SOAT selecciona la opcion Emitir póliza soat
  And en la pagina Emision poliza SOAT se ingresa los datos numero de placa "<placa>", tipo de vehiculo "<tipo_vehiculo>", marca y modelo "<marca_modelo>", numero de chasis "<chasis>", anio de fabricacion "<anio_fabricacion>", producto "<producto>", tipo de uso "<tipo_uso>", Nro asientos "<Nro_asientos>"
  And en la pagina Emision poliza SOAT se ingresa los datos del contratante tipo de documento "<tipo_documento>", Nro documento "<nro_documento>"
  And en la pagina Emision poliza SOAT se ingresa los datos principales
  And en la pagina Emision poliza SOAT se ingresa los datos de contacto
  And en la pagina Emision poliza SOAT se ingresa los datos de direccion
  And en la pagina Emision poliza SOAT seleccionar la opcion CALCULAR PRIMA
  And en la pagina Emision poliza SOAT se EMITE SOAT
  Then en la pagina Emision poliza SOAT se muestra los datos de la EMISION DEL SOAT

  Examples:
   |placa   |tipo_vehiculo|marca_modelo|chasis                 |anio_fabricacion   |producto         |tipo_uso    |Nro_asientos |tipo_documento|nro_documento|
   |        | MOTOCICLETA |HONDA CBR   |                       |2024               |SOAT OIM         |PARTICULAR  |1            |DNI           |45228115     |
  #Los campos PLACA y CHASIS se generarán automáticamente cuando se envíen con valores nulos o en blanco; en caso contrario,
  # se utilizarán los valores proporcionados en el example.

