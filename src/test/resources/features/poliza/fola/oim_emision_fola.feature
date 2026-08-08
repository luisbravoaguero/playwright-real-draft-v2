@regresion
Feature: Emision de poliza FOLA

  @emisionfola
  Scenario Outline: Emision de una poliza FOLA
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion FOLA
    And en la pagina FOLA selecciona la opcion Cotizar poliza FOLA
    And en la pagina Cotizador de Fola en la seccion Informacion general se ingresa el numero de RUC "<numero_ruc>" , la razon social "<razon_social>"y el tipo de fraccionamiento "<tipo_fraccionamiento>"
    And en la pagina Cotizador de Folar en la seccion Asegurados se ingresa la cantidad "<cantidad_grupo>" , actividad "<actividad>" y subvencion por persona "<subvencion_persona>"
    And en la pagina Cotizador de Fola se selecciona el boton de cotizar
    Then el sistema muestra la pagina Resumen Cotizacion Fola con el resumen de la cotizacion
    And en la pagina Resumen Cotizacion Fola selecciona el boton de emitir poliza
    And en la pagina de Emision de Poliza Fola selecciona el boton Descargar Formato y adjunta el formato completado
    And en la pagina de Emision de Poliza Fola ingresa el telefono "<telefono>",telefono movil "<telefono_movil>",nombre de representante "<nombre_representante>",cargo representante "<cargo_representante>"
    And en la pagina de Emision de Poliza Fola selecciona el boton Emision
    Then el sistema muestra en la pagina Documentos Fola el numero de poliza emitida


    Examples:
      | numero_ruc  | razon_social          | tipo_fraccionamiento | cantidad_grupo | actividad           | subvencion_persona | telefono | telefono_movil | nombre_representante | cargo_representante |
      | 20558476462 | EMPRESA DE PRUEBA S.A | SEMESTRAL            | 1              | EMPLEADO DE OFICINA | 350                | 5287449  | 981502678      | JUAN CALDAS BRUNO    | ANALISTA     |