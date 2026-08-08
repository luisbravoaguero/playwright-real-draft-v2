@CotizarAccidentes

Feature:Cotizacion poliza accidentes  con cobertura anual
  @CotizarAccidentes @atprimergrupo
  Scenario Outline:Cotizacion Poliza accidentes con cobertura anual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Accidentes
    And en la pagina Accidentes selecciona la opcion COTIZAR POLIZA ACCIDENTES
    And en la pagina Cotizacion poliza Accidentes completamos los datos obligatorios del contratante con tipo documento "<tipo_documento>", numero documento "<numero_documento>" nombres "<nombres>", apellido paterno "<apellido_paterno>" y apellido materno "<apellido_materno>"
    Then en la pagina Cotizacion guardada se genera la cotizacion correctamente

    Examples:
      |tipo_documento |numero_documento|nombres               |apellido_paterno     |apellido_materno     |
      |DNI            |58282921        |AutomatizacionNombre  |AutomatizacionPaterno|AutomatizacionMaterno|
