@regresion @auto @grupo1
Feature: Cotizacion de una poliza de auto
  @cotizacionautousadocondni @cotizacion @sprint1 @atprimergrupo
  Scenario Outline: Cotizacion exitosa de una poliza de auto usado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Auto
    And en la pagina Auto selecciona la opcion Cotizar poliza de Auto
    And en la pagina Cotización póliza de auto ingresa el numero de placa
    And en la pagina Cotización póliza de auto en la seccion Bien Asegurado se ingresa el tipo de vehiculo "<tipo_vehiculo>", marca y modelo "<marca_modelo>", anio de fabricacion "<anio_fabricacion>"
    And en la pagina Cotización póliza de auto en la seccion Contratante y circulación del riesgo se ingresa el tipo de documento "<tipo_doumento>", numero de documento "<numero_documento>", nombre "<nombre>", apellido paterno "<ap_paterno>", apellido materno "<ap_materno>", fecha nacimiento "<fecha_nacimiento>", correo electronico "<correo_electronico>", sexo "<sexo>", departmento "<departamento>", provincia "<provincia>", distrito "<distrito>"
    And en la pagina Cotización póliza de auto en la seccion Elige el producto a cotizar se ingresa el producto "<producto>" y tipo de uso "<tipo_uso>"
    Then el sistema muestra el resumen de la cotización
      #para automatizacion se debe de cambiar el numero de placa y numero de documento para evitar que se repita en cada ejecucion
    Examples:
      | marca_modelo| anio_fabricacion | tipo_vehiculo| tipo_doumento |numero_documento| nombre | ap_paterno | ap_materno | fecha_nacimiento | correo_electronico   | sexo     | departamento | provincia | distrito | producto      | tipo_uso  |
      | TOYOTA YARIS| 2020             | AUTOMOVIL    | DNI           |99762332| JOSE   | ROJAS      | COLCAS     | 19/08/1993       | EXTLUBA@MAPFRE.COM.PE| MASCULINO| LIMA         |LIMA       | COMAS    | DORADA / PREMIUM I | PARTICULAR|


  @cotizacionautonuevocondni @cotizacion @sprint2 @grupo1
  Scenario Outline: Cotizacion exitosa de una poliza de auto nuevo
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Auto
    And en la pagina Auto selecciona la opcion Cotizar poliza de Auto
    And en la pagina Cotización póliza de auto ingresa el numero de placa
    And en la pagina Cotización póliza de auto en la seccion Bien Asegurado se ingresa el tipo de vehiculo "<tipo_vehiculo>", marca y modelo "<marca_modelo>", anio de fabricacion "<anio_fabricacion>", estado del vehiculo "<estado_vehiculo>"
    And en la pagina Cotización póliza de auto en la seccion Contratante y circulación del riesgo se ingresa el tipo de documento "<tipo_doumento>", numero de documento "<numero_documento>", nombre "<nombre>", apellido paterno "<ap_paterno>", apellido materno "<ap_materno>", fecha nacimiento "<fecha_nacimiento>", correo electronico "<correo_electronico>", sexo "<sexo>", departmento "<departamento>", provincia "<provincia>", distrito "<distrito>"
    And en la pagina Cotización póliza de auto en la seccion Elige el producto a cotizar se ingresa el producto "<producto>" y tipo de uso "<tipo_uso>"
    Then el sistema muestra el resumen de la cotización
      #para automatizacion se debe de cambiar el numero de placa y numero de documento para evitar que se repita en cada ejecucion
    Examples:
      | marca_modelo| anio_fabricacion | tipo_vehiculo| estado_vehiculo |tipo_doumento |numero_documento| nombre | ap_paterno | ap_materno | fecha_nacimiento | correo_electronico   | sexo     | departamento | provincia | distrito | producto      | tipo_uso  |
      | TOYOTA AGYA | 2025             | AUTOMOVIL    | Nuevo           |DNI           |99765835| JUAN   | CALDAS      | BRUNO     | 19/08/1993       | EXTLUBA@MAPFRE.COM.PE| MASCULINO| LIMA         |LIMA       | COMAS    | DORADA / PREMIUM I | PARTICULAR|