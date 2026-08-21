@popup @multitab-isolation
Feature: Abrir el homepage de Bondar Academy en una pestaña nueva
  Como equipo de automatización
  quiero capturar la pestaña abierta desde la página Window
  para validar su contenido y cerrarla sin contaminar otros escenarios

  Scenario Outline: El homepage se abre y se cierra dentro de su BrowserContext
    Given el escenario "<scenarioId>" está en la página Window de Bondar Academy
    When hace clic en Open homepage in a new tab
    Then el homepage se abre en una nueva pestaña del mismo escenario
    And "Roller Shades" es visible en el homepage
    And obtiene el estado de "Roller Shades" antes de hacer clic
    When hace clic en el elemento i.nb-roller-shades
    And obtiene el estado de "Roller Shades" después de hacer clic
    Then el estado de "Roller Shades" debe ser OFF
    When cierra la nueva pestaña
    Then la pestaña Window original permanece abierta y aislada

    Examples:
      | scenarioId |
      | TAB-A-1001 |
      #| TAB-B-2002 |
      #| TAB-C-3003 |
      #| TAB-D-4004 |
