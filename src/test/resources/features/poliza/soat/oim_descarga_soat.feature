@regresion @soat
Feature: Descarga de documento pdf de poliza soat con estado vigente
  @descargapdfsoat @parallel @sprint1 @atprimergrupo
  Scenario Outline: Descarga exitosa de documento pdf de poliza soat
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Soat
    And en la pagina Soat selecciona la opcion Consulta documentos de Soat
    And en la pagina Documentos SOAT ingresa el numero de poliza "<poliza>" con estado activo
    And en la pagina Documentos SOAT seleccionamos la opcion descargar pdf de la poliza mostrada
    Then visualiza la poliza descargada en pdf
    Examples:
      | poliza |
      #| 3022600000031 |
      | 3022500292081 |
      #| 3022500000486 |
      #| NUEVO_CAMBIO |
        #| NUEVO_CAMBIO2_CESAR |
          #| NUEVO_CAMBIO_3_CESARR |