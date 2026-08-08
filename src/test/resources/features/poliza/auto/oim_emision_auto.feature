@regresion @auto
Feature: Emision de una poliza de auto
  @emisionautonuevo @emision @sprint3 @atprimergrupo
  Scenario Outline: Emitir una poliza de un AUTO nuevo con DNI desde la busqueda de una cotizacion guardada
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Auto
    And en la pagina Auto selecciona la opcion Emitir poliza de Auto
    And en la pagina Cotizaciones Auto realiza la busqueda del numero de cotizacion "<numero_cotizacion>" entre el rango de fecha "<fecha_inicio>" y "<fecha_fin>"
    And en la pagina Cotizacion Guardada de Autos selecciona el boton Emitir poliza
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos de la Pliza e ingresamos el numero de placa "<numero_placa>"
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos Principales ingresamos el nombre "<nombre_contratante>" apellido paterno "<apellido_paterno>" apellido materno "<apellido_materno>" fecha de nacimiento "<fecha_nacimiento>" sexo "<sexo>" y profesion "<profesion>"
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos de Contacto ingresamos el numero de telefono de casa "<numero_telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" departamento "<departamento>" procinvia "<procinvia>" distrito "<distrito>" tipo de via "<tipo_de_via>" y nombre de la via "<nombre_de_via>"
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos del Vehiculo ingresamos la frecuencia de uso "<frecuencia_de_uso>" el numero de siniestros en los ultimos dos anios "<numero_de_siniestros_anteriores>" los anios de antiguedad de la licencia de conducir "<anios_de_antiguedad_de_la_licencia>" y responder con un SI o NO en los casos si el auto es conducido por una persona "<responder_auto_conducido_por_una_persona>" y responder si usualmente guarda el auto en un garaje "<responder_guarda_el_auto_en_un_garaje>"
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante ingresamos el tipo de financiamiento "<tipo_financiamiento>"
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante selecciona el boton Emitir Poliza
    Then en la pagina Póliza Emitida de Autos se muestra el resumen de la poliza

    Examples:
      | numero_cotizacion | fecha_inicio | fecha_fin |numero_placa|nombre_contratante|apellido_paterno|apellido_materno|fecha_nacimiento|sexo        |profesion|numero_telefono_casa|telefono_movil|correo_electronico   |departamento|procinvia|distrito|tipo_de_via|nombre_de_via|frecuencia_de_uso|numero_de_siniestros_anteriores|anios_de_antiguedad_de_la_licencia|responder_auto_conducido_por_una_persona|responder_guarda_el_auto_en_un_garaje|tipo_financiamiento|
      |3924227            |12/01/2026    |12/02/2026 |XJS383      |JUAN              |CALDAS          |BRUNO           |19/08/1993      |Masculino   |ABOGADO  |12354432            |987654321     |EXTLUBA@MAPFRE.COM.PE|LIMA        |LIMA     |COMAS    |AA.HH.    |VIA PRINCIPIAL|TODOS LOS DIAS  |NINGUN EVENTO                  |1                                 |S                                       |S                                   |Al contado          |
      #|3923701            |04/02/2026    |04/02/2026 |ABC123      |JUAN              |CALDAS          |BRUNO           |19/08/1993      |Masculino   |ABOGADO  |12354432            |987654321     |EXTLUBA@MAPFRE.COM.PE|LIMA        |LIMA     |COMAS    |AA.HH.    |VIA PRINCIPIAL|SOLO FINES DE SEMANA  |1 EVENTO                  |2                                 |SI                                      |SI                                   |
      #|3923701            |04/02/2026    |04/02/2026 |ABC123      |JUAN              |CALDAS          |BRUNO           |19/08/1993      |Masculino   |ABOGADO  |12354432            |987654321     |EXTLUBA@MAPFRE.COM.PE|LIMA        |LIMA     |COMAS    |AA.HH.    |VIA PRINCIPIAL|DE LUNES A VIERNES  |2 EVENTOS                  |3                                 |SI                                      |SI                                   |
      #|3923701            |04/02/2026    |04/02/2026 |ABC123      |JUAN              |CALDAS          |BRUNO           |19/08/1993      |Masculino   |ABOGADO  |12354432            |987654321     |EXTLUBA@MAPFRE.COM.PE|LIMA        |LIMA     |COMAS    |AA.HH.    |VIA PRINCIPIAL|TODOS LOS DIAS  |NINGUN EVENTO                  |4                                 |SI                                      |SI                                   |
      #|3923701            |04/02/2026    |04/02/2026 |ABC123      |JUAN              |CALDAS          |BRUNO           |19/08/1993      |Masculino   |ABOGADO  |12354432            |987654321     |EXTLUBA@MAPFRE.COM.PE|LIMA        |LIMA     |COMAS    |AA.HH.    |VIA PRINCIPIAL|TODOS LOS DIAS  |NINGUN EVENTO                  |4                                 |SI                                      |SI                                   |


  @emisionautousado
  Scenario Outline: Emision de poliza de auto usado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Auto
    And en la pagina Auto selecciona la opcion Emitir poliza auto usado
    And en la pagina Emision poliza auto usado, en el paso Seleccionar inspeccion, seccion Elige un auto inspeccionado para emitir, ingresamos y buscamos el numero de placa "<numero_placa>" del vehiculo inspeccionado
    And en la pagina Emision poliza auto usado, en el paso Datos de la poliza, seccion Elige el producto a emitir, ingresamos el producto "<producto>" y tipo de uso "<tipo_uso>"
    #And en la pagina Emision Poliza auto usado, en el paso Datos del contratante, ingresamos la informacion del contratante y los datos del vehiculo
    And en la pagina Emision Poliza auto usado, en el paso Datos del contratante, ingresamos la informacion del contratante numero documento "<numero_documento>", nombre "<nombre>", apellido paterno "<ap_paterno>", materno "<ap_materno>" y los datos del vehiculo
    And en la pagina Emision Poliza auto usado, en el paso Datos de financiamiento ingresamos el tipo de financiamiento al contado para la calcular la prima de la poliza
    And en la pagina Emision Poliza auto usado, en el paso Emitir poliza seleccionamos el boton Emitir poliza
    Then en la pagina Poliza Emitida de Autos se muestra el resumen de la poliza auto usado
    #todos los campos son requeridos

    Examples:
      |numero_placa|producto            |tipo_uso  |numero_documento| nombre | ap_paterno | ap_materno|
      |SBE214      |DORADA / PREMIUM I  |PARTICULAR|89762332        | JOSE   | ROJAS      | COLCAS    |


  @inspeccionyemisionautousado
  Scenario Outline: Inspeccion y emision de poliza auto usado
    Given el usuario accede a la pagina OIM con credenciales validas
    #IR AL MODULO DE INSPECCION DE AUTOS PARA CREAR LA SOLICITUD DE INSPECCION
    When desde la pagina Home selecciona el modulo Inspeccion de Autos
    And en la pagina Inspeccion de Autos selecciona la opcion Cotizaciones
    And en la pagina Cotizaciones de Inspeccion de Autos seleccionamos la cotizacion "<numero_cotizacion>" en el rango de fechas "<fecha_inicio_cotizacion>" y "<fecha_fin_cotizacion>" para realizar la inspeccion del vehiculo
    And en la pagina Detalle Cotizacion de Autos selecciona el boton Solicitar Inspeccion para crear la solicitud de inspeccion
    Then en la pagina Nueva Solicitud de Inspeccion se muestra el paso Solicitante y Vehiculo

    #CREAR SOLICITUD DE INSPECCION
    #When en la pagina Nueva Solicitud de Inspeccion, en el paso Solicitante y vehiculo, en la seccion Informacion del solicitante completamos el correo electronico y  en la seccion Datos del vehiculo completamoe el numero de placa, serie y cantidad de accesorios
    When en la pagina Nueva Solicitud de Inspeccion, en el paso Solicitante y vehiculo, en la seccion Informacion del solicitante completamos el correo electronico y  en la seccion Datos del vehiculo completamoe el numero de placa "<numero_placa>", numero de serie "<numero_serie>", numero de motor "<numero_motor>" y cantidad de accesorios
    #And en la pagina Nueva Solicitud de Inspeccion, en el paso Contratante Contacto, en la seccion Datos del contratante completamos la fecha de nacimiento, estado civil, nacionalidad, sexo y profesion
    And en la pagina Nueva Solicitud de Inspeccion, en el paso Contratante Contacto, en la seccion Datos del contratante completamos el numero de documento "<numero_documento>", nombre "<nombre>", apellido paterno "<ap_paterno>" y materno "<ap_materno>" la fecha de nacimiento, estado civil, nacionalidad, sexo y profesion
    And en la pagina Nueva Solicitud de Inspeccion, en el paso Contratante Contacto, en la seccion Datos de contacto completamos el tefono de casa, oficina y movil, correo electronico personal y de oficina
    And en la pagina Nueva Solicitud de Inspeccion, en el paso Contratante Contacto, en la seccion Datos de direccion completamos el departamento, provincia y distrito, y la direccion de su residencia
    And en la pagina Nueva Solicitud de Inspeccion, en el paso Datos de la emision, en la seccion Financiamiento completamos el tipo de financiamiento al contado
    And en la pagina Nueva Solicitud de Inspeccion, en el paso Confirmar solicitud, en la seccion Confirmar solicitud seleccionamos el boton Confirmar solicitud
    And en la pagina Nueva Solicitud de Inspeccion, seleccionamos la opcion Inspeccion Presencial dentro del modal
    Then en la pagina Nueva Solicitud de Inspeccion, se crea el numero de solicitud de inspeccion y redirecciona a la pagina Inspeccion de Autos

    #PROGRAMAR INSPECCION - ESTADO INICIAL POR PROGRAMAR
    When el usuario redirecciona a la pagina Home
    When desde la pagina Home selecciona el modulo Inspeccion de Autos
    When en la pagina Inspeccion de Autos selecciona la opcion Solicitudes
    And en la pagina Solicitudes de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado POR PROGRAMAR
    #And en la pagina Detalle Solicitud de Inspeccion de Autos realizamos la programacion de la inspeccion del vehiculo
    And en la pagina Detalle Solicitud de Inspeccion de Autos ingresamos al modulo Programacion de la inspeccion y seleccionamos el inspector "<inspector>"
    And en la pagina Detalle Solicitud de Inspeccion de Autos realizamos la programacion de la inspeccion del vehiculo en una fecha disponible
    Then se muestra el mensaje de confirmacion de la programacion de la inspeccion del vehiculo y redirecciona a la pagina Inspeccion de Autos

    #REGISTRAR INSPECCION Buttonn - ESTADO INICIAL PROGRAMADA
    #ESTADO NUMERO 1 EN PROCESO DE INSPECCION
    When el usuario redirecciona a la pagina Home
    When desde la pagina Home selecciona el modulo Inspeccion de Autos
    When en la pagina Inspeccion de Autos selecciona la opcion Programaciones
    And en la pagina Programaciones de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado PROGRAMADA
    #And en la pagina Detalle Solicitud de Inspeccion de Autos realizamos el registro de inspeccion del vehiculo con estado En Proceso de Inspeccion
    And en la pagina Detalle Solicitud de Inspeccion de Autos realizamos el registro de inspeccion del vehiculo con estado En Proceso de Inspeccion, ingresamos el tipo de vehiculo "<tipo_vehiculo>", modelo del vehiculo "<marca_modelo>", anio de fabricacion "<anio_fabricacion>", numero de placa "<numero_placa>", numero de serie "<numero_serie>" y numero de motor "<numero_motor>"
    Then se muestra el mensaje de confirmacion de inspeccion guardada correctamente y redirecciona a la pagina Inspeccion de Autos

    #ESTADO NUMERO 1 EN EVALUACION FOTOS DEL VEHICULO
    When el usuario redirecciona a la pagina Home
    When desde la pagina Home selecciona el modulo Inspeccion de Autos
    When en la pagina Inspeccion de Autos selecciona la opcion Solicitudes
    And en la pagina Solicitudes de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado EN EVALUACION
    And en la pagina Detalle Solicitud de Inspeccion de Autos finalizamos el registro de inspeccion, seccion Fotos del vehiculo, con estado EN EVALUACION
    Then se muestra el mensaje de confirmacion de inspeccion guardada correctamente y redirecciona a la pagina Inspeccion de Autos

    ##ESTADO NUMERO 1 EN EVALUACION PASOS PARA DESACTIVAR EL TOGGLE
    When el usuario redirecciona a la pagina Home
    When desde la pagina Home selecciona el modulo Inspeccion de Autos
    When en la pagina Inspeccion de Autos selecciona la opcion Solicitudes
    And en la pagina Solicitudes de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado EN EVALUACION
    And en la pagina Detalle Solicitud de Inspeccion de Autos desactivamos el toggle EN EVALUACION para finalizar la inspeccion del vehiculo
    Then se muestra la pagina Inspeccion de Autos

    #ESTADO NUMERO 2 TERMINADA
    When el usuario redirecciona a la pagina Home
    When desde la pagina Home selecciona el modulo Inspeccion de Autos
    When en la pagina Inspeccion de Autos selecciona la opcion Solicitudes
    And en la pagina Solicitudes de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado TERMINADA
    Then en la pagina Solicitudes de Inspeccion de Autos se muestra el estado de la inspeccion del vehiculo con estado TERMINADA

    When el usuario redirecciona a la pagina Home
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Auto
    And en la pagina Auto selecciona la opcion Emitir poliza auto usado
    And en la pagina Emision poliza auto usado, en el paso Seleccionar inspeccion, seccion Elige un auto inspeccionado para emitir, ingresamos y buscamos el numero de placa "<numero_placa>" del vehiculo inspeccionado
    And en la pagina Emision poliza auto usado, en el paso Datos de la poliza, seccion Elige el producto a emitir, ingresamos el producto "<producto>" y tipo de uso "<tipo_uso>"
    #And en la pagina Emision Poliza auto usado, en el paso Datos del contratante, ingresamos la informacion del contratante y los datos del vehiculo
    And en la pagina Emision Poliza auto usado, en el paso Datos del contratante, ingresamos la informacion del contratante numero documento "<numero_documento>", nombre "<nombre>", apellido paterno "<ap_paterno>", materno "<ap_materno>" y los datos del vehiculo
    And en la pagina Emision Poliza auto usado, en el paso Datos de financiamiento ingresamos el tipo de financiamiento al contado para la calcular la prima de la poliza
    And en la pagina Emision Poliza auto usado, en el paso Emitir poliza seleccionamos el boton Emitir poliza
    Then en la pagina Poliza Emitida de Autos se muestra el resumen de la poliza auto usado
    #los datos que dicen opcional no son requeridos

    Examples:
      | numero_cotizacion|fecha_inicio_cotizacion|fecha_fin_cotizacion|numero_placa|numero_serie    |numero_motor|numero_documento| nombre | ap_paterno | ap_materno | producto           | tipo_uso  |tipo_vehiculo|marca_modelo|anio_fabricacion|inspector|
      | 3933830          |OPCIONAL               |OPCIONAL            |SBE214      |SBE214DF4233FRF |SBE214DF342S|99762332        | JOSE   | ROJAS      | COLCAS     | DORADA / PREMIUM I | PARTICULAR|AUTOMOVIL    |TOYOTA YARIS|2020            |4        |
