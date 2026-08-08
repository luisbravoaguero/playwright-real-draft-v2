@CotizarEmitirAccidentes @grupo1

Feature:Cotizar y emitir poliza de accidentes con cobertura anual

@CotizarEmitirAccidentes

  Scenario Outline:Cotizacion y Emision Poliza accidentes  con cobertura anual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Accidentes
    And en la pagina Accidentes selecciona la opcion COTIZAR POLIZA ACCIDENTES
    And en la pagina Cotizacion poliza Accidentes completamos los datos obligatorios del contratante con tipo documento "<tipo_documento>", numero documento "<numero_documento>" nombres "<nombres>", apellido paterno "<apellido_paterno>" y apellido materno "<apellido_materno>"
    And en la pagina Cotizacion guardada de Accidentes se selecciona la opcion Emitir Poliza
    And en la pagina Emision Poliza Accidentes completamos los campos obligatorios tipo documento "<tipo_documento_emi>", numero documento "<numero_documento_emi>", fecha nacimiento "<fecha_nacimiento_emi>"
    And en la pagina Emision Poliza Accidentes cargamos el archivo excel de asegurados y se emite la poliza accidentes
    Then en la pagina Poliza Emitida se muestra el resultado de la emisión de la poliza accidente

    Examples:
    |tipo_documento |numero_documento|nombres               |apellido_paterno     |apellido_materno     |tipo_documento_emi|numero_documento_emi|fecha_nacimiento_emi |
    |DNI            |58282921        |AutomatizacionNombre  |AutomatizacionPaterno|AutomatizacionMaterno|DNI               |58282921            |27/07/1988             |