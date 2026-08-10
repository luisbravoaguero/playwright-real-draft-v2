@regression @conduit
Feature: Autenticación en Conduit

  @conduit_signin
  Scenario: Usuario inicia sesión exitosamente en Conduit
    Given el usuario accede a la pagina Conduit
    When desde la pagina Conduit hace clic en Sign in
    And en la pagina Sign in ingresa el email "test@example.com" y la contraseña "testpassword"

