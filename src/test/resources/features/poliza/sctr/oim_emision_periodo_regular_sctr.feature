@regresion @sctr @modulosctr @emisionsctr @periodoregular
Feature: Emision de polizas sctr periodo regular
  @emisionsctrperiodoregularpensionsalud
  Scenario Outline: Emision de una poliza SCTR de periodo regular mensual cobertura doce meses para Pension y Salud
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Emitir SCTR periodo regular
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la empresa ingresamos tipo documento "<tipo_documento>" numero documento "<numero_documento>" razon social "<razon_social>" telefono de casa "<telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" representante "<representante>" cargo del representante "<cargo_representante>" departamento "<departamento>" provincia "<provincia>" distrito "<distrito>" tipo de via "<tipo_via>" nombre de la via "<nombre_via>" tipo_numero "<tipo_numero>" enumeracion "<enumeracion>" y actividad sunat "<actividad_sunat>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad "<codigo_actividad>" el codigo de subactividad "<codigo_subactividad>" la frecuencia de declaracion "<frecuencia_declaracion>" la duracion de la cobertura "<duracion_cobertura>" y el centro de riesgo "<centro_riesgo>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el formulario Datos de los asegurados
    Then en la pagina Resumen Poliza Emitida se muestra el numero de poliza
    #numero de ruc fijo para este caso
    Examples:
      |tipo_documento  |numero_documento|razon_social                           |telefono_casa|telefono_movil|correo_electronico   |representante|cargo_representante|departamento|provincia|distrito|tipo_via|nombre_via|tipo_numero|enumeracion|actividad_sunat|codigo_actividad|codigo_subactividad|frecuencia_declaracion|duracion_cobertura|centro_riesgo|numero_trabajadores|importe_planilla|
      |RUC             |20604298602     |CIVILSAPS INGENIEROS CONTRATISTAS S.A.C|5363839      |987654321     |EXTLUBA@MAPFRE.COM.PE|ADMINISTRADOR|ADMINISTRADOR      |LIMA        |LIMA     |COMAS   |AA.HH.  |PRINCIPAL |CASA       |885        |AGRICULTURA    |500002          |335                |MENSUAL               |12 MESES          |MAPFRE       |1                  |3000.25        |

  @descargarconstanciadeunaemisionsctrperiodoregular
  Scenario Outline: Descargar la constancia al emitir una póliza SCTR de periodo regular para Pension y Salud con RUC valido
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Emitir SCTR periodo regular
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la empresa ingresamos tipo documento "<tipo_documento>" numero documento "<numero_documento>" razon social "<razon_social>" telefono de casa "<telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" representante "<representante>" cargo del representante "<cargo_representante>" departamento "<departamento>" provincia "<provincia>" distrito "<distrito>" tipo de via "<tipo_via>" nombre de la via "<nombre_via>" tipo_numero "<tipo_numero>" enumeracion "<enumeracion>" y actividad sunat "<actividad_sunat>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad "<codigo_actividad>" el codigo de subactividad "<codigo_subactividad>" la frecuencia de declaracion "<frecuencia_declaracion>" la duracion de la cobertura "<duracion_cobertura>" y el centro de riesgo "<centro_riesgo>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el formulario Datos de los asegurados
    And en la pagina Resumen Poliza Emitida se muestra el numero de poliza
    Then en la pagina Resumen Poliza Emitida se descarga la constancia
    #numero de ruc fijo para este caso
    Examples:
      |tipo_documento  |numero_documento|razon_social                                 |telefono_casa|telefono_movil|correo_electronico   |representante|cargo_representante|departamento|provincia|distrito|tipo_via|nombre_via|tipo_numero|enumeracion|actividad_sunat|codigo_actividad|codigo_subactividad|frecuencia_declaracion|duracion_cobertura|centro_riesgo|numero_trabajadores|importe_planilla|
      |RUC             |20407929889     |PROVEEDOR DE MATERIALES Y CONSTRUCCION S.A.C.|5363839      |987654321     |EXTLUBA@MAPFRE.COM.PE|ADMINISTRADOR|ADMINISTRADOR      |LIMA        |LIMA     |COMAS   |AA.HH.  |PRINCIPAL |CASA       |885        |AGRICULTURA    |500002          |335                |MENSUAL               |12 MESES          |MAPFRE       |1                  |3000.25        |

  @enviarcorreocondocumentosdeunaemisionsctrperiodoregular
  Scenario Outline: Enviar correo electronico con los documentos generados de una poliza SCTR emitida periodo regular mensual cobertura doce meses para Pension y Salud
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Ver documentos sctr
    And en la pagina Documentos SCTR completamos el formulario tipo de producto "<tipo_producto>", numero de poliza "<numero_poliza>", estado de la poliza "<estado_poliza>", fecha de inicion "<fecha_inicio>", fecha fin "<fecha_fin>" y origen "<origen>"
    And el la pagina Documentos SCTR seleccionamos Ver Detalle
    And en la pagina Resumen Poliza Emitida se muestra el numero de poliza
    Then en la pagina Resumen Poliza Emitida se envia la informacion de la poliza al correo electronico del asegurado "<correo_electronico>"
    #numero de ruc fijo para este caso
    Examples:
      |tipo_producto|numero_poliza|estado_poliza|fecha_inicio|fecha_fin |correo_electronico   |origen|
      |Largo        |7012600004083|EMITIDA      |06/07/2026  |08/07/2026|EXTLUBA@MAPFRE.COM.PE|EMISA |


  @emisionsctrperiodoregularpension
  Scenario Outline: Emision de una poliza SCTR de periodo regular mensual cobertura doce meses para el riesgo Pension
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Emitir SCTR periodo regular
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la empresa ingresamos tipo documento "<tipo_documento>" numero documento "<numero_documento>" razon social "<razon_social>" telefono de casa "<telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" representante "<representante>" cargo del representante "<cargo_representante>" departamento "<departamento>" provincia "<provincia>" distrito "<distrito>" tipo de via "<tipo_via>" nombre de la via "<nombre_via>" tipo_numero "<tipo_numero>" enumeracion "<enumeracion>" y actividad sunat "<actividad_sunat>"
    And en la pagina Emitir póliza SCTR Periodo Regular seleccionamos el riesgo Mapfre Pension
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad "<codigo_actividad>" el codigo de subactividad "<codigo_subactividad>" la frecuencia de declaracion "<frecuencia_declaracion>" la duracion de la cobertura "<duracion_cobertura>" y el centro de riesgo "<centro_riesgo>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Regular, formulario Datos del riesgo seccion Mapfre Pension procesamos los datos ingresados
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el formulario Datos de los asegurados
    Then en la pagina Resumen Poliza Emitida se muestra el numero de poliza
    #numero de ruc fijo para este caso
    Examples:
      |tipo_documento  |numero_documento|razon_social                      |telefono_casa|telefono_movil|correo_electronico   |representante|cargo_representante|departamento|provincia|distrito|tipo_via|nombre_via|tipo_numero|enumeracion|actividad_sunat|codigo_actividad|codigo_subactividad|frecuencia_declaracion|duracion_cobertura|centro_riesgo|numero_trabajadores|importe_planilla|
      |RUC             |20498578021     |1000 ESTRELLAS TOURS TAXI E.I.R.L.|5363839      |987654321     |EXTLUBA@MAPFRE.COM.PE|ADMINISTRADOR|ADMINISTRADOR      |LIMA        |LIMA     |COMAS   |AA.HH.  |PRINCIPAL |CASA       |885        |AGRICULTURA    |500002          |335                |MENSUAL               |12 MESES          |MAPFRE       |1                  |3000.25        |


  @emisionsctrperiodoregularsalud
  Scenario Outline: Emision de una poliza SCTR de periodo regular mensual cobertura doce meses para el riesgo Salud
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Emitir SCTR periodo regular
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la empresa ingresamos tipo documento "<tipo_documento>" numero documento "<numero_documento>" razon social "<razon_social>" telefono de casa "<telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" representante "<representante>" cargo del representante "<cargo_representante>" departamento "<departamento>" provincia "<provincia>" distrito "<distrito>" tipo de via "<tipo_via>" nombre de la via "<nombre_via>" tipo_numero "<tipo_numero>" enumeracion "<enumeracion>" y actividad sunat "<actividad_sunat>"
    And en la pagina Emitir póliza SCTR Periodo Regular seleccionamos el riesgo Mapfre Salud
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad "<codigo_actividad>" el codigo de subactividad "<codigo_subactividad>" la frecuencia de declaracion "<frecuencia_declaracion>" la duracion de la cobertura "<duracion_cobertura>" y el centro de riesgo "<centro_riesgo>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores "<numero_trabajadores>" y el importe planilla "<importe_planilla>"
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el formulario Datos de los asegurados
    Then en la pagina Resumen Poliza Emitida se muestra el numero de poliza
    #numero de ruc fijo para este caso
    Examples:
      |tipo_documento  |numero_documento|razon_social                                             |telefono_casa|telefono_movil|correo_electronico   |representante|cargo_representante|departamento|provincia|distrito|tipo_via|nombre_via|tipo_numero|enumeracion|actividad_sunat|codigo_actividad|codigo_subactividad|frecuencia_declaracion|duracion_cobertura|centro_riesgo|numero_trabajadores|importe_planilla|
      |RUC             |20508863366     |DEVELOPMENT MEDICAL GROUP SOCIEDAD ANONIMA CERRADA S.A.C.|5363839      |987654321     |EXTLUBA@MAPFRE.COM.PE|ADMINISTRADOR|ADMINISTRADOR      |LIMA        |LIMA     |COMAS   |AA.HH.  |PRINCIPAL |CASA       |885        |AGRICULTURA    |500002          |335                |MENSUAL               |12 MESES          |MAPFRE       |1                  |3000.25        |
