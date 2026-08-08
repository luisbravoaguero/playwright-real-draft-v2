@regresion @camposanto @grupo1
Feature: Cotizacion de una poliza de campo santo necesidad futura
  @cotizacioncamposanto @cotizacioncamposantonecesidadfuturacondni @sprint4 @atprimergrupo
  Scenario Outline: Cotizacion camposanto con necesidad futura al contado de un cliente con DNI que existe en BD y califica con exito
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Camposanto
    And en la pagina Camposanto selecciona la opcion Cotizar contrato
    And en la pagina Cotizador de Camposanto selecciona el ramo necesidad futura al contado
    And en la pagina Cotizador de Camposanto los Datos del Producto camposanto "<camposanto_producto>" tipo de contrato "<tipo_contrato>" modalidad "<modalidad>" producto "<producto>"
    And en la pagina Cotizador de Camposanto los Datos del Cliente se ingresa el tipo de documento "<tipo_doumento>", numero de documento "<numero_documento>", nombre "<nombre>", apellido paterno "<ap_paterno>", apellido materno "<ap_materno>", fecha nacimiento "<fecha_nacimiento>", estado civil "<estado_civil>", telefono de casa "<telefono_casa>", telefono movil "<telefono_movil>", correo electronico "<correo_electronico>", departmento "<departamento>", provincia "<provincia>", distrito "<distrito>" y direccion "<direccion>"
    Then el sistema muestra la pagina Cotizador de Camposanto con el resumen de la cotizacion
    And en la pagina Cotizador de Camposanto se envia el resumen de la cotización al cliente "<correo_electronico>"
    And en la pagina Cotizador de Camposanto se realiza la descarga del PDF de la cotización

    Examples:
      |camposanto_producto |tipo_contrato       |modalidad       |producto              |tipo_doumento |numero_documento| nombre | ap_paterno | ap_materno | fecha_nacimiento | estado_civil | telefono_casa | telefono_movil | correo_electronico   | departamento | provincia | distrito | direccion          |
      |CAMPOSANTO ICA      |AMPLIACION INTEGRAL |CLASICO INTEGRAL|AMPLIACION EN 1 NIVEL |DNI           |99762332        | JOSE   | ROJAS      | COLCAS     | 19/08/1993       | SOLTERO      | 012345678     | 987654321      | EXTLUBA@MAPFRE.COM.PE| LIMA         |LIMA       | COMAS    | AV. LOS ALAMOS 123 |
