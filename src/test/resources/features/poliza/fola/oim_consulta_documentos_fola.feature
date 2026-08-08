@fola @regresion @consultadocumentofola @grupo1
Feature: Consulta de documentos del ramo FOLA
  @consultadocumentofolaporfecha
  Scenario Outline: Consulta de documentos FOLA por rango de fechas
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion FOLA
    And en la pagina FOLA selecciona la opcion Consulta Documentos de FOLA
    And en la pagina Documentos FOLA completamos el formulario fecha de inicion "<fecha_inicio>" y fecha fin "<fecha_fin>"
    Then el sistema muestra los documentos de FOLA

    Examples:
      |fecha_inicio  |fecha_fin |
      |01/04/2026    |31/05/2026|