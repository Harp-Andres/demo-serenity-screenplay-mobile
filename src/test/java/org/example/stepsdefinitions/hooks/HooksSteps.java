package org.example.stepsdefinitions.hooks;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.WebDriver;

public class HooksSteps {

    @Before
    public void prepareActor() {
        OnStage.setTheStage(new OnlineCast());
        System.out.println("Inicializando el actor para pruebas móviles");
        OnStage.theActorCalled("Andres");
        System.out.println("Actor inicializado para pruebas móviles");
        // ... dentro de prepareActor()
        WebDriver proxiedDriver = BrowseTheWeb.as(OnStage.theActorInTheSpotlight()).getDriver();
        // LA SOLUCIÓN: Desempaquetar el driver real
        AppiumDriver realDriver = (AppiumDriver) ((WebDriverFacade) proxiedDriver).getProxiedDriver();

        System.out.println("CAPABILITIES CARGADAS: " + realDriver.getCapabilities());
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
