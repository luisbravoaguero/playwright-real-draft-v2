Feature: Cotizacion y emision poliza transporte
  @emisionTransporte
  Scenario Outline: Cotizacion y emision de poliza Transporte
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Transporte
    And en la pagina Transporte selecciona la opcion Emitir poliza de Transporte
    And en la pagina Emitir poliza de Transporte se elige la poliza de grupo del agente "<agente>"
    And en la pagina Emitir poliza de Transporte, paso y seccion Datos del riesgo, ingresamos materia aseguradora "<materia_aseguradora>", tasa de mercaderia "<tasa_mercaderia>" y  descripcion materia aseguradora "<descripcion_materia_aseguradora>"
    And en la pagina Emitir poliza de Transporte, paso Datos del riesgo, seccion Lugar Origen ingresamos el pais de origen "<pais_origen>", compania de transporte "<compania_transporte>", nombre nave "<nombre_nave>", factura y guia "<factura_guia>" y nombre del proveedor "<nombre_proveedor>"
    And en la pagina Emitir poliza de Transporte, paso Datos del riesgo, seccion Lugar Destino ingresamos en el campo Otro "<otro_campo>", departamento "<departamento>", provincia "<provincia>", distrito "<distrito>" y almacen "<almacen>"
    And en la pagina Emitir poliza de Transporte, paso Calulo de prima, seccion Importes ingresamos la valuacion mercaderia "<valuacion_mercaderia>", valor mercaderia "<valor_mercaderia>", flete "<flete>", derecho de aduana "<derecho_aduana>" y porcentaje de sobreseguro "<porcentaje_sobreseguro>"
    And en la pagina Emitir poliza de Transporte, paso Datos del contratante, seccion Datos personales ingresamos los datos personales, caracteristicas personales, datos laborales y datos de direccion
    And en la pagina Emitir poliza de Transporte, paso Emitir poliza, seccion Resumen Prima seleccionamos el boton Emitir poliza
    Then en la pagina Poliza emitida se muestra la poliza de Transporte
    Examples:
      |agente  |materia_aseguradora                       |tasa_mercaderia|descripcion_materia_aseguradora|pais_origen|compania_transporte|nombre_nave  |factura_guia |nombre_proveedor|otro_campo                 |departamento|provincia|distrito|almacen|valuacion_mercaderia|valor_mercaderia|flete|derecho_aduana|porcentaje_sobreseguro|
      |9808    |PROPIOS DEL GIRO DEL NEGOCIO DEL ASEGURADO|0.3            |DESCRIPCION MATERIA ASEGURADORA|URUGUAY     |POR CONFIRMAR      |POR CONFIRMAR|POR CONFIRMAR|POR CONFIRMAR   |ALMAC.ASEG.LIMA VIA/CALLAO|LIMA        |LIMA     |COMAS   |LIMA   |F.O.B               |20000           |2    |2             |2                     |
