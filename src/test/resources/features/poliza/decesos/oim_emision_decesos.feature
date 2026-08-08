@regresion @decesos
Feature: Emision de una poliza de decesos
  @emisionDecesos
  Scenario Outline: Emision de una poliza de decesos con DNI
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Decesos
    And en la pagina Decesos selecciona la opcion Cotizar Poliza Decesos
    And en la pagina Cotizador de Decesos en la seccion Datos de la poliza se ingresa el producto "<producto>", poliza grupo "<poliza_grupo>",modalidad "<modalidad>", medio de pago "<medio_pago>"
    And en la pagina Cotizador de Decesos en la seccion Datos del contratante se ingresa el tipo de documento "<tipo_documento>", numero de documento "<numero_documento>",estado civil "<estado_civil>",profesion "<profesion>", telefono de casa "<telefono_casa>", telefono movil "<telefono_movil>", correo electronico "<correo_electronico>", departmento "<departamento>", provincia "<provincia>", distrito "<distrito>" ,tipo_via "<tipo_via>", nombre_via "<nombre_via>", tipo_numero "<numero>", enumeracion "<enumeracion>"
    And en la pagina Cotizador de Decesos en la seccion Asegurados se ingresa el asegurado que es el mismo que el contratante
    And en la pagina cotizador de Decesos en la seccion Asegurados se ingresa el segundo asegurado con tipo de documento "<tipo_documento2>", numero de documento "<numero_documento2>",
    And en la pagina Cotizador de Decesos  selecciona el boton de cotizar
    Then el sistema muestra la pagina Resumen Cotizacion Decesos con el resumen de la cotizacion
    And en la pagina Resumen Cotizacion Decesos selecciona el boton de emitir poliza
    And en la pagina de Emision de Poliza de Decesos en la seccion Datos de Poliza selecciona el boton Siguiente
    And en la pagina de Emision de Poliza de Decesos en la seccion Carga de documentos selecciona el tipo de documento "<doc_decesos>" y adjunta el documento
    And en la pagina de Emision de Poliza de Decesos en la seccion Carga de documentos selecciona el boton Emision
    When el usuario redirecciona a la pagina Home
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Decesos
    And en la pagina Decesos selecciona la opcion Consultar Cotizaciones de Decesos
    Then el sistema muestra en la pagina Documentos Decesos el numero de poliza emitida


    Examples:
      |producto         |poliza_grupo                 |modalidad              |medio_pago|tipo_documento|numero_documento|estado_civil|profesion|telefono_casa|telefono_movil|correo_electronico   |departamento |provincia |distrito |tipo_via |nombre_via  |numero |enumeracion |tipo_documento2|numero_documento2|doc_decesos|
      |seguro de sepelio| INTEGRAL (BAS/6TO/CLASIC 2017) |Plan Integral - 3 Aseg.|Cobradores|DNI           |11111111| SOLTERO | ADMINISTRADOR |5287449|981502678|pruebas89@gmail.com|LIMA|LIMA|COMAS| AVDA. |UNIVERSITARIA| NRO |1234| DNI |22222222|Solicitud Decesos|