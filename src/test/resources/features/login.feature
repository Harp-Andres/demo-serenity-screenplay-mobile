#language:es

@login
Característica: Funcionalidad de inicio de sesión
    Como usuario registrado
    Quiero iniciar sesión en la aplicación
    Para acceder a mi cuenta y utilizar sus funcionalidades

  Antecedentes:
    Dado que el usuario está en la página de inicio de sesión de la aplicación

    Escenario: Inicio de sesión exitoso
        Cuando el usuario ingresa las credenciales validas
        Entonces el usuario debería ver la Home page de la aplicación