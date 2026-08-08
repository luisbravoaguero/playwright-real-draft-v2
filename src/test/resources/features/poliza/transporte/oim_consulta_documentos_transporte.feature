@regresion @transporte
Feature: Consulta de documentos del ramo transporte
  @consultadocumentotransporteporfecha @consultadocumento @sprint5 @atprimergrupo
  Scenario Outline: Consulta de documento Transporte por rango de fechas
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Transporte
    And en la pagina Transporte selecciona la opcion Consulta Documentos de Transporte
    And en la pagina Documentos Transporte completamos el formulario fecha de inicion "<fecha_inicio>" y fecha fin "<fecha_fin>"
    Then el sistema muestra los documentos Transporte
    Examples:
      |fecha_inicio  |fecha_fin |
      |01/02/2026    |10/02/2026|