@technical @multitab-isolation
Feature: Aislamiento de nuevas pestañas por escenario
  Como equipo de automatización
  quiero capturar y cerrar cada pestaña dentro de su escenario
  para evitar contaminación durante la ejecución paralela

  Scenario Outline: El detalle conserva el contexto del escenario que lo abrió
    Given una página de resultados aislada para el escenario "<scenarioId>"
    When abre el detalle en una nueva pestaña
    Then el detalle pertenece únicamente al escenario actual
    When cierra la pestaña de detalle
    Then regresa a la pestaña original sin contaminación

    Examples:
      | scenarioId |
      | TAB-A-1001 |
      | TAB-B-2002 |
      | TAB-C-3003 |
      | TAB-D-4004 |
