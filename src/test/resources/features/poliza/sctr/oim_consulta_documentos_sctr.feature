@regresion @sctr
Feature: Consulta de documentos del ramo sctr
  @consultadocumentosctrporfecha @parallel @sprint2 @atprimergrupo
  Scenario Outline: Consulta exitosa de documentos sctr por fecha
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Ver documentos sctr
    And en la pagina Documentos SCTR ingresa la fecha de inicio "<fecha_inicio>" y fecha fin "<fecha_fin>"
    Then el sistema muestra los documentos sctr
    Examples:
      |fecha_inicio|fecha_fin |
      |20/01/2026  |25/01/2026|

  @consultadocumentoydescargasctrpornumeropoliza @descargapdfconstancia @sprint4 @atprimergrupo
  Scenario Outline: Descargar PDFs de bandeja de SCTR periodo regular
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Ver documentos sctr
    And en la pagina Documentos SCTR completamos el formulario tipo de producto "<tipo_producto>", numero de poliza "<numero_poliza>", estado de la poliza "<estado_poliza>", fecha de inicion "<fecha_inicio>", fecha fin "<fecha_fin>" y origen "<origen>"
    And el la pagina Documentos SCTR seleccionamos Ver Detalle
    Then en la pagina Emitir poliza SCTR Periodo Regular se descarga el Recibo de Pension Recibo de Salud Poliza de Pension y Poliza de Salud

    Examples:
      |tipo_producto|numero_poliza|estado_poliza|fecha_inicio|fecha_fin |origen|
      |Largo        |7012600004314|EMITIDA      |17/07/2026  |17/07/2026|EMISA |