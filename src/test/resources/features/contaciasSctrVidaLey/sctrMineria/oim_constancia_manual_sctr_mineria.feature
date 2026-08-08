@regresion @sctr @moduloconstacias @constanciamanual @constanciamanualsctr @grupo1
Feature: Constancia manual de una poliza sctr mineria para pension o salud
  @constanciamanualsctrmineriapensionsalud @constanciamanualsctrmineriapensionsaludmesadelantado @migracionconstanciamanualsctrmineriapensionsalud
  Scenario Outline: Constancia Manual de una poliza sctr mineria para pension y salud mes adelantado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR Mineria
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente selecciona la opcion Constancia Manual para abrir el modal Generar Nueva Constancia Manual
    And en el modal Generar Nueva Constancia Manual completamos la fecha de cobertura y cargamos los asegurados mineria
    Then en el modal Generar Nueva Constancia Manual se muestra el numero de la constancia manual generada
    Examples:
      | numero_poliza |
      | 7012600002839 |