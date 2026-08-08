@regresion @vidaInversion @cotizacionEmisionVidaInversion
Feature: Cotizacion y Emisión de Poliza Vida Inversion Certivida
  @cotizacionEmisionVidaInversionCertividaConDNI
  Scenario Outline: Cotizacion y Emisión de Poliza Vida Inversion Certivida - Asegurado es Contratante con DNI
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Vida Inversion
    And en la pagina Vida Inversion selecciona la opcion Cotizar poliza de vida inversion
    And en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Datos del Contratante ingresamos el tipo de documento "<tipo_documento_contrante>", numero de documento "<numero_documento_contrante>", nombre "<nombre_contrante>", apellido paterno "<apellido_paterno_contrante>", apellido materno, "<apellido_materno_contrante>", telefono movil "<telefono_movil_contrante>" y correo electronico "<correo_electronico_contrante>"
    And en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Datos del Asegurado activamos la opcion Los datos del asegurado son los mismos que los del contratante
    And en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Datos del Asesor ingresamos el GestorSupervisor "<gestor_supervisor>" y agente "<agente>"
    And en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Caracteristicas del Seguro ingresamos el tipo del producto "<tipo_producto>", tipo de moneda "<tipo_moneda>", anios de duracion del seguro "<anios_duracion_seguro>" modalidad "<modalidad>", prima comercial unica "<prima_comercia_unica>" y codigo de promocion "<codigo_promocion>"
    Then en la pagina Cotizacion Poliza Vida Inversion, en el paso Resultado cotizacion, se muestra el numero de cotizacion
    When se inicia la emision desde el Resultado de la Cotizacion
    And en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Contratante, Datos Principales ingresamos la fecha de nacimiento "<fecha_nacimiento>", estado civil "<estado_civil>", el pais de residencia fiscal "<pais_residencia_fiscal>"
    And en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Contratante, Datos Laborales ingresamos sexo "<sexo>", profesion "<profesion>" y ocupacion "<ocupacion>"
    And en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Contratante, Datos de Contacto ingresamos el prefijo telefono "<prefijo_telefono>", telefono casa "<telefono_casa>", telefono de oficina "<telefono_oficina>", telefono movil "<telefono_movil>" y correo electronico "<correo_electronico>"
    And en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Contratante, Datos de Direccion ingresamos el pais natal "<pais_natal>", departamento "<departamento>", provincia "<provincia>", distrito "<distrito>", tipo de via "<tipo_via>" y nombre de via "<nombre_via>"
    And en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Asegurado activamos la opcion Asegurado es Contratante e ingreamos el centro de trabajo certivida "<centro_trabajo>"
    And en la pagina Emisión Póliza Vida, en el paso Datos de la Poliza, seccion Beneficiarios en caso de muerte pasamos al paso Declaracion Personal de Salud
    And en la pagina Emisión Póliza Vida, en el paso Declaracion Personal de Salud, llenamos el Formulario Declaracion Personal de Salud
    And en la pagina Emisión Póliza Vida, en el paso Documentos Requeridos, seleccionamos los documentos obligatorios para la emision de la poliza
    Then en la pagina Resultado de Emisión Póliza Vida Inversion se muestra el numero de poliza generado

    Examples:
      |tipo_documento_contrante|numero_documento_contrante|nombre_contrante     |apellido_paterno_contrante|apellido_materno_contrante|telefono_movil_contrante|correo_electronico_contrante|gestor_supervisor|agente|tipo_producto|tipo_moneda|anios_duracion_seguro|modalidad|prima_comercia_unica|codigo_promocion|fecha_nacimiento|estado_civil|pais_residencia_fiscal|sexo     |profesion|ocupacion|prefijo_telefono|telefono_casa|telefono_oficina|telefono_movil|correo_electronico   |pais_natal|departamento|provincia|distrito|tipo_via|nombre_via |centro_trabajo |
      |DNI                     |71518178                  |AutomatizacionNombre |AutomatizacionPaterno     |AutomatizacionMaterno     |945586698               |EXTLUBA@MAPFRE.COM.PE       |TODOS            |9808  |CERTIVIDA    |SOLES      |5                    |A        |300000              |OPCIONAL        |19/08/1193      |SOLTERO     |PERU                  |Masculino|ABOGADO  |ABOGADO  |51              |5251298      |5872563         |945586698     |EXTLUBA@MAPFRE.COM.PE|PERU      |LIMA        |LIMA     |COMAS   |AA.HH.  |LAS FLORES |MAPFRE PERU SAC|