@regresion @inspeccionautousado @grupo1
Feature: Inspeccion de una cotizacion de poliza de auto usado
  @inspeccionautousado @grupo1
  Scenario Outline: Inspeccion de una cotizacion de poliza auto usado
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
    #And en la pagina Detalle Solicitud de Inspeccion de Autos realizamos la programacion de la inspeccion del vehiculo y seleccionamos el inspector "<inspector>"
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
    #numero_cotizacion, numero_placa, numero_serie y numero_motor son datos requeridos

    Examples:
      | numero_cotizacion|fecha_inicio_cotizacion|fecha_fin_cotizacion|numero_placa|numero_serie     |numero_motor|numero_documento|nombre|ap_paterno|ap_materno|tipo_vehiculo|marca_modelo|anio_fabricacion|inspector|
      | 3934215          |OPCIONAL               |OPCIONAL            |DD5149      |DD5149JX7NIAPSD9Z|DD5149JX7CIA|99762332        |JOSE  |ROJAS     |COLCAS    |AUTOMOVIL    |TOYOTA YARIS|2020            |2        |
