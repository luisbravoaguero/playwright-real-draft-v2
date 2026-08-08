@regresion @consultacotizacionesdecesos @grupo1
Feature: Consulta de cotizaciones del ramo decesos
  @consultacotizacionesdecesosporfecha @atprimergrupo
  Scenario Outline: Consulta de cotizaciones de Decesos por rango de fechas
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Decesos
    And en la pagina Decesos selecciona la opcion Consultar Cotizaciones de Decesos
    And en la pagina Documentos Decesos completamos el formulario fecha de inicio "<fecha_inicio>" y fecha fin "<fecha_fin>"
    Then el sistema muestra las cotizaciones de Decesos
    Examples:
      |fecha_inicio  |fecha_fin |
      |01/04/2026    |24/04/2026|