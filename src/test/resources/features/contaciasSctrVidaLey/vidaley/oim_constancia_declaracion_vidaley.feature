@regresion @vidaley @moduloconstacias @declaracionvidaley @grupo1
Feature: Declaracion de una poliza vidaley
  @declaracionvidaleycargaindividualmesadelantado @migraciondeclaracionvidaley
  Scenario Outline: Declaracion de una poliza vidaley mes adelantado y tipo de carga individual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion Vida Ley
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza vidaley
    And en la pagina Declaracion completamos el formulario Datos de los asegurados vidaley
    And en la pagina Declaracion completamos el formulario Informacion de Declaracion
    And en la pagina Declaracion seleccionamos el boton Declarar
    Then en la pagina Resumen Declaracion Vida Ley se muestra el resumen de la aplicacion declarada
    Examples:
      | numero_poliza   |
      | 6102600001534   |

