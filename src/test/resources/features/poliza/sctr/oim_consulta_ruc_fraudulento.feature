@regresion @sctr @modulosctr @emisionsctr @periodoregular
Feature: Emision de polizas sctr periodo regular
  @clienterucfraudulentoperiodoregular
  Scenario Outline: Validar cliente fraudulento en la emision sctr periodo regular con tipo de documento ruc
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Sctr
    And en la pagina Sctr selecciona la opcion Emitir SCTR periodo regular
    And en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la empresa ingresamos tipo documento RUC y numero documento "<numero_documento>"

    Examples:
      |numero_documento|
      |20100128056     |
