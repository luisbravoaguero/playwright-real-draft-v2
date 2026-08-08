@regresion @sctr @moduloconstacias @inclusionsctr @grupo1
Feature: Inclusion de una poliza sctr para pension o salud
  @inclusionpensionsalud @inclusionsctr @inclusionsctrfacturadapensionsaludmesadelantado @migracioninclusionnpensionsalud
  Scenario Outline: Inclusion de una poliza sctr para pension y salud mes adelantado y tipo de carga individual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente seleccionamos una aplicacion para incluir la poliza
    And en la pagina Incluir Planilla completamos el formulario Datos de los asegurados
    And en la pagina Incluir Planilla completamos el formulario Informacion de Inclusion
    And en la pagina Incluir Planilla seleccionamos el boton Generar
    Then en la pagina Inclusion de Planilla se muestra el resumen de la aplicacion incluida
    Examples:
      | numero_poliza |
      | 7012600004455 |
