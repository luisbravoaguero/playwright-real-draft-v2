@regresion @constanciasSctrMineria
Feature: Inclusion de una poliza sctr mineria para pension o salud
  @inclusionSctrMineriaPensionSaludMesAdelantadoFacturada
  Scenario Outline: Inclusion de una poliza sctr mineria para pension y salud mes adelantado facturada
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR Mineria
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente seleccionamos una aplicacion para incluir la poliza
    And en la pagina Incluir completamos los datos de los asegurados de forma masiva sctr mineria
    And en la pagina Incluir Planilla completamos el formulario Informacion de Inclusion
    And en la pagina Incluir Planilla seleccionamos el boton Generar
    Then en la pagina Inclusion de Planilla se muestra el resumen de la aplicacion incluida
    Examples:
      | numero_poliza |
      | 7012600001843 |
