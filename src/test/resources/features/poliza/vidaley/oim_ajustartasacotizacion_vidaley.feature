@regresion @vidaley @modulovidaley

Feature: Solicitar ajuste de tasa cotizacion vida ley

  @q2 @q2sprint1 @ajustartasacotizacion @atprimergrupo
  Scenario Outline: Ajustar tasa de una poliza vida ley con RUC y cobertura mensual de doce meses
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Vida Ley
    And en la pagina Vida Ley selecciona la opcion Cotizar vida ley
    And en la pagina Cotizacion poliza de vida ley completamos los datos del contratante y seleccionamos tipo documento "<tipo_documento>", numero documento "<numero_documento>", nombre completo "<nombre_completo>", telefono "<telefono>", correo electronico "<correo_electronico>"
    And en la pagina Cotizacion poliza de vida ley buscamos y seleccionamos una actividad "<actividad>"
    And en la pagina Cotizacion poliza de vida ley sección Duración y declaracion del seguro ingresamos frecuencia de declaracion "<frecuencia_declaracion>", fecha inicial "<fecha_inicial>", duracion de cobertura "<duracion_cobertura>". centro de riesgo "<centro_riesgo>"
    And en la pagina Cotizacion poliza de vida ley seccion datos del la cobertura y asegurados, se ingresa el registro manual del asegurado.
    And en la pagina Cotizacion poliza de vida ley se solicita el reajuste de la tasa de la cotizacion
    And en la pagina Vida Ley se selecciona la opcion Bandeja de documentos
    And en la bandeja de documentos de Vida Ley se filtra y se busca la cotizacion por el estado SOLICITUD EVALUACION y se selecciona ver cotizacion
    And en la pagina de evaluacion de tasa se ingresa la tasa final "<tasa_final>" y se acepta la solicitud
    And en la pagina Vida Ley se selecciona la opcion Bandeja de documentos
    And en la bandeja de documentos de Vida Ley se filtra y se busca la cotizacion por el estado SOLICITUD ATENDIDA y se selecciona ver cotizacion
    Then en la pagina Cotizacion poliza de vida ley se acepta la cotizacion y se genera el numero de cotización

    Examples:
      |tipo_documento  |numero_documento|nombre_completo   |telefono    |correo_electronico    |actividad|frecuencia_declaracion   |fecha_inicial|duracion_cobertura|centro_riesgo    |tasa_final |
      |RUC             |20525109969     |AUTOMATIZADOR     |945586698   |drpruebas@hotmail.com |3220     |MENSUAL                  |14/04/2026   |12 MESES          |MAPFRE           |2          |