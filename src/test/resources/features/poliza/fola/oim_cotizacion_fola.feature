@regresion @fola @cotizacionfola @grupo1
Feature: Cotizacion de poliza FOLA
  @cotizacionpolizafola @atprimergrupo
  Scenario Outline: Cotizacion de una poliza FOLA
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion FOLA
    And en la pagina FOLA selecciona la opcion Cotizar poliza FOLA
    And en la pagina Cotizador de Fola en la seccion Informacion general se ingresa el numero de RUC "<numero_ruc>" , la razon social "<razon_social>"y el tipo de fraccionamiento "<tipo_fraccionamiento>"
    And en la pagina Cotizador de Folar en la seccion Asegurados se ingresa la cantidad "<cantidad_grupo>" , actividad "<actividad>" y subvencion por persona "<subvencion_persona>"
    And en la pagina Cotizador de Fola se selecciona el boton de cotizar
    Then el sistema muestra la pagina Resumen Cotizacion Fola con el resumen de la cotizacion

    Examples:
      |numero_ruc  |razon_social         |tipo_fraccionamiento|cantidad_grupo|actividad           |subvencion_persona|
      |20558476462 |EMPRESA DE PRUEBA S.A|SEMESTRAL           |3 |EMPLEADO DE OFICINA            |350               |