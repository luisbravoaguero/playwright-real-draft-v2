@regresion @sctr @moduloconstacias @declaracionsctr
Feature: Declaracion de una poliza sctr para pension o salud
  @declaracionpensionsalud @declaracionsctr @declaracionpensionsaludmesadelantado @migraciondeclaracionpensionsalud
  Scenario Outline: Declaracion de una poliza sctr para pension y salud mes adelantado y tipo de carga individual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza
    And en la pagina Declaracion completamos el formulario Datos de los asegurados
    And en la pagina Declaracion completamos el formulario Informacion de Declaracion
    And en la pagina Declaracion seleccionamos el boton Declarar
    Then en la pagina Resumen Declaracion SCTR se muestra el resumen de la aplicacion declarada
    Examples:
      | numero_poliza |
      | 7012600003867 |

  @clienteconpolizayrucactivopensionsaludmesadelantado @migracionclienteconpolizayrucactivopensionsaludmesadelantado
  Scenario Outline: Validar cliente con poliza sctr y RUC activo para pension y salud mes adelantado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes ingresamos el tipo de documento "<tipo_documento>" y numero de documento "<numero_documento>"
    Then en la pagina Perfil Cliente se valida que la poliza esta vigente y el RUC esta activo
    Examples:
      | tipo_documento|numero_documento|
      | RUC           |20538995364     |


  @declaracionrenovacionsctrpensionsaludmesadelantado
  Scenario Outline: Declaracion de una poliza sctr con renovacion para pension y salud mes adelantado y tipo de carga individual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza con renovacion
    And en la pagina Declaracion completamos el formulario Datos de los asegurados
    And en la pagina Declaracion completamos el formulario Informacion de Declaracion
    And en la pagina Declaracion seleccionamos el boton Declarar
    Then en la pagina Resumen Declaracion SCTR se muestra el resumen de la aplicacion declarada
    Examples:
      | numero_poliza |
      | 7012600004255 |

    #EJEMPLO DE EMISION DE POLIZA SCTR PARA PENSION Y SALUD
    #When desde la pagina Home selecciona el modulo Polizas
    #And en la pagina Polizas selecciona la opcion Sctr
    #And en la pagina Sctr selecciona la opcion Emitir SCTR periodo regular
    #And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la empresa ingresamos tipo documento "<tipo_documento>" numero documento "<numero_documento>" razon social "<razon_social>" telefono de casa "<telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" representante "<representante>" cargo del representante "<cargo_representante>" departamento "<departamento>" provincia "<provincia>" distrito "<distrito>" tipo de via "<tipo_via>" nombre de la via "<nombre_via>" tipo_numero "<tipo_numero>" enumeracion "<enumeracion>" y actividad sunat "<actividad_sunat>"
    #And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad "<codigo_actividad>" el codigo de subactividad "<codigo_subactividad>" la frecuencia de declaracion "<frecuencia_declaracion>" la duracion de la cobertura "<duracion_cobertura>" y el centro de riesgo "<centro_riesgo>"
    #And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    #And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    #And en la pagina Emitir póliza SCTR Periodo Regular completamos el formulario Datos de los asegurados
    #And en la pagina Resumen Poliza Emitida se muestra el numero de poliza
    #Then en la pagina Resumen Poliza Emitida se descarga la constancia
    #Examples:
    #  |tipo_documento  |numero_documento|razon_social                           |telefono_casa|telefono_movil|correo_electronico   |representante|cargo_representante|departamento|provincia|distrito|tipo_via|nombre_via|tipo_numero|enumeracion|actividad_sunat|codigo_actividad|codigo_subactividad|frecuencia_declaracion|duracion_cobertura|centro_riesgo|numero_trabajadores|importe_planilla|
    #  |RUC             |20603614748     |CIVILSAPS INGENIEROS CONTRATISTAS S.A.C|5363839      |987654321     |EXTLUBA@MAPFRE.COM.PE|ADMINISTRADOR|ADMINISTRADOR      |LIMA        |LIMA     |COMAS   |AA.HH.  |PRINCIPAL |CASA       |885        |AGRICULTURA    |500002          |335                |MENSUAL               |12 MESES          |MAPFRE       |1                  |3000.25        |
