@regresion @accidentes @grupo1
Feature: Consulta de documentos del ramo accidente
  @consultadocumentoaccidentesporfecha @consultadocumento @sprint5 @atprimergrupo
  Scenario Outline: Consulta de documento Accidentes por rango de fechas
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Accidentes
    And en la pagina Accidentes selecciona la opcion Consulta Documentos de Accidentes
    And en la pagina Documentos Accidentes completamos el formulario fecha de inicion "<fecha_inicio>" y fecha fin "<fecha_fin>"
    Then el sistema muestra los documentos Accidentes
    Examples:
      |fecha_inicio  |fecha_fin |
      |01/02/2026    |10/02/2026|