@popup @multitab-isolation
Feature: Abrir el homepage de Bondar Academy en una pestaña nueva
  Como equipo de automatización
  quiero capturar la pestaña abierta desde la página Window
  para validar su contenido y cerrarla sin contaminar otros escenarios

  Scenario Outline: El homepage se abre y se cierra dentro de su BrowserContext
    Given el escenario "<scenarioId>" está en la página Window de Bondar Academy
    When hace clic en Open homepage in a new tab
    Then el homepage se abre en una nueva pestaña del mismo escenario
    When cierra la nueva pestaña
    Then la pestaña Window original permanece abierta y aislada

    Examples:
      | scenarioId |
      | TAB-A-1001 |
      | TAB-B-2002 |
      | TAB-C-3003 |
      | TAB-D-4004 |
