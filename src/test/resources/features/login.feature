#language:es

@login @android @e2e
Característica: Login en TheApp
  Como usuario de la app demo TheApp
  Quiero autenticarme
  Para acceder al área secreta

  Antecedentes:
    Dado que el usuario abre la pantalla de Login de TheApp

  Escenario: Login exitoso con credenciales demo
    Cuando el usuario ingresa las credenciales validas
    Entonces el usuario debería ver el mensaje de sesión iniciada

  Escenario: Login fallido con credenciales inválidas
    Cuando el usuario ingresa las credenciales invalidas
    Entonces el usuario debería ver el mensaje de credenciales inválidas
