package org.example.stepsdefinitions.hooks;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class HooksSteps {

    @Before
    public void prepareActor() {
        // Inicializa el escenario con un reparto que puede usar la web/móvil
        OnStage.setTheStage(new OnlineCast());

        // Al llamar al actor, Serenity abrirá el AppiumDriver
        // automáticamente basándose en tu archivo serenity.conf
        OnStage.theActorCalled("Andres");
    }

    @After
    public void tearDown() {
        // Cierra el driver y limpia el escenario
        OnStage.drawTheCurtain();

        // Si manejas el servidor manualmente, descomenta la siguiente línea:
        // AppiumServerManager.stopServer();
    }
}
