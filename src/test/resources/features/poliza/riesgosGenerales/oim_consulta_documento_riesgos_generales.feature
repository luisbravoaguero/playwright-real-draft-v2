@riesgosgenerales @regresion  @consultadocumentoriesgosgenerales
Feature:Consulta de documentos del ramo riesgos generales
  @consultadocumentoriesgosgeneralesporfecha
  Scenario Outline: Consulta de documento de Riesgos Generales por rango de fechas
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Riesgos Generales
    And en la pagina Riesgos Generales selecciona la opcion Consulta Documentos de Riesgos Generales
    And en la pagina Documentos Riesgos Generales completamos el formulario fecha de inicion "<fecha_inicio>" y fecha fin "<fecha_fin>"
    Then el sistema muestra los documentos de Riesgos Generales

    Examples:
      |fecha_inicio  |fecha_fin |
      |01/04/2026    |30/04/2026|