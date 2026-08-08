@regresion @vidaley @modulovidaley

Feature: cotizar poliza vida ley y emitir poliza

  @q2 @q2sprint1 @cotizacionvidaleyruccoberturadocemeses @CotizarPolizayEmitirPoliza
  Scenario Outline: Cotizacion y Emision de una poliza vida ley con RUC y cobertura mensual de doce meses
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Vida Ley
    And en la pagina Vida Ley selecciona la opcion Cotizar vida ley
    And en la pagina Cotizacion poliza de vida ley completamos los datos del contratante y seleccionamos tipo documento "<tipo_documento>", numero documento "<numero_documento>", nombre completo "<nombre_completo>", telefono "<telefono>", correo electronico "<correo_electronico>"
    And en la pagina Cotizacion poliza de vida ley buscamos y seleccionamos una actividad "<actividad>"
    And en la pagina Cotizacion poliza de vida ley sección Duración y declaracion del seguro ingresamos frecuencia de declaracion "<frecuencia_declaracion>", fecha inicial "<fecha_inicial>", duracion de cobertura "<duracion_cobertura>". centro de riesgo "<centro_riesgo>"
    And en la pagina Cotizacion poliza de vida ley seccion datos del la cobertura y asegurados, se ingresa el registro manual del asegurado.
    And en la pagina Cotizacion poliza de vida ley se genera el numero de cotizacion y se inicia la emision de la poliza
    And en la pagina Emitir poliza de vida ley se confirman los datos del contratante para continuar con la emision
    And en la pagina Emitir poliza de vida ley se validan los datos de la cobertura y asegurados
    Then se muestra el resultado de poliza emitida con el numero de poliza generado


    Examples:
      |tipo_documento  |numero_documento|nombre_completo|telefono       |correo_electronico   |actividad|frecuencia_declaracion|fecha_inicial|duracion_cobertura|centro_riesgo|
      |RUC             |20605209344     |PRUEBAS        |945586698      |drpruebas@hotmail.com|3220     |MENSUAL                 |14/04/2026   |12 MESES        |MAPFRE       |