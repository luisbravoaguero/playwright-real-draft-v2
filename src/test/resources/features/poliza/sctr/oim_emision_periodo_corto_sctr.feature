@regresion @sctr @modulosctr @emisionsctr @periodoregular
Feature: Emision de polizas sctr periodo corto
  @emisionsctrperiodocortopensionsalud
  Scenario Outline: Emision de una poliza SCTR de periodo corto mensual cobertura un mes para Pension y Salud
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Emitir SCTR periodo corto
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos de la empresa ingresamos tipo documento "<tipo_documento>" numero documento "<numero_documento>" razon social "<razon_social>" telefono de casa "<telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" representante "<representante>" cargo del representante "<cargo_representante>" departamento "<departamento>" provincia "<provincia>" distrito "<distrito>" tipo de via "<tipo_via>" nombre de la via "<nombre_via>" tipo_numero "<tipo_numero>" enumeracion "<enumeracion>" y actividad sunat "<actividad_sunat>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad "<codigo_actividad>" el codigo de subactividad "<codigo_subactividad>" la frecuencia de declaracion "<frecuencia_declaracion>" la duracion de la cobertura "<duracion_cobertura>" y el centro de riesgo "<centro_riesgo>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el formulario Datos de los asegurados
    Then en la pagina Resumen Poliza SCTR Emitida Periodo Corto se muestra el numero de poliza
     #numero de ruc fijo para este caso
    Examples:
      |tipo_documento  |numero_documento|razon_social                           |telefono_casa|telefono_movil|correo_electronico   |representante|cargo_representante|departamento|provincia|distrito|tipo_via|nombre_via|tipo_numero|enumeracion|actividad_sunat|codigo_actividad|codigo_subactividad|frecuencia_declaracion|duracion_cobertura|centro_riesgo|numero_trabajadores|importe_planilla|
      |RUC             |20454617186     |CIVILSAPS INGENIEROS CONTRATISTAS S.A.C|5363839      |987654321     |EXTLUBA@MAPFRE.COM.PE|ADMINISTRADOR|ADMINISTRADOR      |LIMA        |LIMA     |COMAS   |AA.HH.  |PRINCIPAL |CASA       |885        |AGRICULTURA    |500002          |335                |MENSUAL               |1 MES             |MAPFRE       |1                  |3000.25        |


  @descargardocumentosemisionsctrperiodocortopensionsalud
  Scenario Outline: Descargar los documentos generados al emitir una poliza SCTR de periodo corto mensual cobertura un mes para Pension y Salud
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Emitir SCTR periodo corto
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos de la empresa ingresamos tipo documento "<tipo_documento>" numero documento "<numero_documento>" razon social "<razon_social>" telefono de casa "<telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" representante "<representante>" cargo del representante "<cargo_representante>" departamento "<departamento>" provincia "<provincia>" distrito "<distrito>" tipo de via "<tipo_via>" nombre de la via "<nombre_via>" tipo_numero "<tipo_numero>" enumeracion "<enumeracion>" y actividad sunat "<actividad_sunat>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad "<codigo_actividad>" el codigo de subactividad "<codigo_subactividad>" la frecuencia de declaracion "<frecuencia_declaracion>" la duracion de la cobertura "<duracion_cobertura>" y el centro de riesgo "<centro_riesgo>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el formulario Datos de los asegurados
    Then en la pagina Resumen Poliza SCTR Emitida Periodo Corto se muestra el numero de poliza y descargamos los documentos generados
     #numero de ruc fijo para este caso
    Examples:
      |tipo_documento  |numero_documento|razon_social           |telefono_casa|telefono_movil|correo_electronico   |representante|cargo_representante|departamento|provincia|distrito|tipo_via|nombre_via|tipo_numero|enumeracion|actividad_sunat|codigo_actividad|codigo_subactividad|frecuencia_declaracion|duracion_cobertura|centro_riesgo|numero_trabajadores|importe_planilla|
      |RUC             |20600756371     |2813 CONSULTING E.I.R.L|5363839      |987654321     |EXTLUBA@MAPFRE.COM.PE|ADMINISTRADOR|ADMINISTRADOR      |LIMA        |LIMA     |COMAS   |AA.HH.  |PRINCIPAL |CASA       |885        |AGRICULTURA    |500002          |335                |MENSUAL               |1 MES             |MAPFRE       |1                  |3000.25        |


  @ajustetasaemisionsctrperiodocortopensionsalud
  Scenario Outline: Ajuste de tasa al emitir una poliza SCTR de periodo corto mensual cobertura un mes para Pension y Salud
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Emitir SCTR periodo corto
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos de la empresa ingresamos tipo documento "<tipo_documento>" numero documento "<numero_documento>" razon social "<razon_social>" telefono de casa "<telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" representante "<representante>" cargo del representante "<cargo_representante>" departamento "<departamento>" provincia "<provincia>" distrito "<distrito>" tipo de via "<tipo_via>" nombre de la via "<nombre_via>" tipo_numero "<tipo_numero>" enumeracion "<enumeracion>" y actividad sunat "<actividad_sunat>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad "<codigo_actividad>" el codigo de subactividad "<codigo_subactividad>" la frecuencia de declaracion "<frecuencia_declaracion>" la duracion de la cobertura "<duracion_cobertura>" y el centro de riesgo "<centro_riesgo>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>" para rechazar las condiciones
    When el usuario redirecciona a la pagina Home
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Ver documentos sctr
    And en la pagina Documentos SCTR buscamos el numero de documento SCTR y vemos el detalle
    Then en la pagina Emitir poliza SCTR Periodo Corto se muestra el mensaje TASAS RECHAZADAS al aceptar las condiciones de pago
     #numero de ruc fijo para este caso
    Examples:
      |tipo_documento  |numero_documento|razon_social  |telefono_casa|telefono_movil|correo_electronico   |representante|cargo_representante|departamento|provincia|distrito|tipo_via|nombre_via|tipo_numero|enumeracion|actividad_sunat|codigo_actividad|codigo_subactividad|frecuencia_declaracion|duracion_cobertura|centro_riesgo|numero_trabajadores|importe_planilla|
      |RUC             |20601154138     |28SIETE S.A.C.|5363839      |987654321     |EXTLUBA@MAPFRE.COM.PE|ADMINISTRADOR|ADMINISTRADOR      |LIMA        |LIMA     |COMAS   |AA.HH.  |PRINCIPAL |CASA       |885        |AGRICULTURA    |500002          |335                |MENSUAL               |1 MES             |MAPFRE       |1                  |3000.25        |
