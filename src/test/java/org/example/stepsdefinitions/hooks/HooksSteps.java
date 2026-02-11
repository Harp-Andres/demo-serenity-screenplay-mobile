package org.example.stepsdefinitions.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;

public class HooksSteps {

    @Before
    public void prepareActor() {
        OnStage.setTheStage(new OnlineCast());
        System.out.println("Inicializando el actor para pruebas móviles");
        OnStage.theActorCalled("Andres");
        System.out.println("Actor inicializado para pruebas móviles");
        // Cambia la lógica de extracción para que sea compatible con cualquier driver
        WebDriver proxiedDriver = BrowseTheWeb.as(OnStage.theActorInTheSpotlight()).getDriver();
        WebDriver realDriver = ((WebDriverFacade) proxiedDriver).getProxiedDriver();

        // Para imprimir capabilities sin que el cast falle:
        if (realDriver instanceof HasCapabilities) {
            System.out.println("CAPABILITIES CARGADAS: " + ((HasCapabilities) realDriver).getCapabilities());
        }
    }

    @After
    public void tearDown() {
        WebDriver driver = BrowseTheWeb.as(OnStage.theActorInTheSpotlight()).getDriver();
        if (driver != null) {
            driver.quit(); // Fuerza el cierre de la sesión en el servidor Appium
        }
        // Cierra el driver y limpia el escenario
        OnStage.drawTheCurtain();
    }
}
