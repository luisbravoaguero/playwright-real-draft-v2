@regresion @vidaley
Feature: Consulta de documentos del ramo vida ley
  @consultadocumentovidaley @q2 @q2sprint1 @atprimergrupo
  Scenario Outline: Consulta de documentos del ramo vida ley por rango de fecha
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Vida Ley
    And en la pagina Vida Ley selecciona la opcion Bandeja de documentos
    And en la pagina Documentos de Vida Ley completamos el formulario estado de la poliza "<estado_poliza>", tipo de documento "<tipo_documento>", numero de documento "<numero_documento>", numero de solicitud "<numero_solicitud>", fecha de inicion "<fecha_inicio>" y fecha fin "<fecha_fin>"
    Then el sistema muestra los documentos Vida Ley

    Examples:
    |estado_poliza|tipo_documento|numero_documento|numero_solicitud|fecha_inicio|fecha_fin |
    |OPCIONAL     |OPCIONAL      |OPCIONAL        |OPCIONAL        |01/04/2026  |09/04/2026|
    #|COTIZACION EMITIDA|RUC           |20603087624     |OPCIONAL        |01/12/2025  |19/02/2026|