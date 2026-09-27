#language:es

@echo @android @e2e
Característica: Echo Box en TheApp
  Como usuario de la app demo
  Quiero guardar un mensaje en Echo Box
  Para verificar que la UI refleja el texto

  Escenario: Guardar y ver un mensaje eco
    Dado que el usuario está en la home de TheApp
    Cuando el usuario guarda el mensaje "hola granja efimera" en Echo Box
    Entonces debería ver el mensaje guardado "hola granja efimera"
