@regresion @camposanto @grupo1
Feature: Cotizacion y Emisión de una poliza camposanto necesidad inmediata
  @cotizacioncamposantonecesidadInmediata @q2sp4 @CotizacionyEmisionPolizaCamposantoNecesidadInmediata @emisionpoliza401
  Scenario Outline: Cotizacion y Emisión camposanto con necesidad Inmediata al contado de un cliente con DNI que existe en BD y califíca con éxito
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Camposanto
    And en la pagina Camposanto selecciona la opcion Cotizar contrato
    And en la pagina Cotizador de Camposanto selecciona el ramo necesidad inmediata
    And en la pagina Cotizador de Camposanto ramo necesidad inmediata ingresamos los datos del producto camposanto "<camposanto_producto>" tipo de contrato "<tipo_contrato>" modalidad "<modalidad>" producto "<producto>"
    And en la pagina Cotizador de Camposanto ramo necesidad inmediata ingresamos los Datos del Cliente se ingresa el tipo de documento "<tipo_doumento>", numero de documento "<numero_documento>", nombre "<nombre>", apellido paterno "<ap_paterno>", apellido materno "<ap_materno>", fecha nacimiento "<dia>","<mes>","<anio>", estado civil "<estado_civil>", telefono de casa "<telefono_casa>", telefono movil "<telefono_movil>", correo electronico "<correo_electronico>", departmento "<departamento>", provincia "<provincia>", distrito "<distrito>" y direccion "<direccion>"
    And en la pagina Cotizador de Camposanto ramo necesidad inmediata el sistema muestra la pagina Cotizador de   Camposanto con el resumen de la cotizacion y se selecciona la opcion Ir a Bandeja
    And en la pagina Bandeja de Camposanto seleccionamos la opcion emitir del nro de cotizacion creado
    And en la pagina Cotizador de Camposanto ramo necesidad inmediata se ingresan LOS CHECK DE DOCUMENTOS, DATOS DEL TOMADOR, DATOS BENEFICIARIOS, DATOS ADICIONALES
    Then en la pagina Cotizador de Camposanto ramo necesidad inmediata emitimos la poliza

    Examples:
      |camposanto_producto |tipo_contrato    |modalidad |producto            |tipo_doumento |numero_documento| nombre | ap_paterno | ap_materno | dia |mes  |anio    | estado_civil | telefono_casa | telefono_movil | correo_electronico   | departamento | provincia | distrito | direccion          |
      |CAMPOSANTO HUACHIPA |SEPULTURA        |CLASICO   |SEXTUPLE FAMILIAR   |DNI           |99762332        | JOSE   | ROJAS      | COLCAS     |19  |08   |1993    | SOLTERO      | 012345678     | 987654321      | EXTLUBA@MAPFRE.COM.PE| LIMA         |LIMA       | COMAS    | AV. LOS ALAMOS 123 |