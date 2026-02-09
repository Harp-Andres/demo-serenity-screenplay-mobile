package org.example.stepsdefinitions.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;
import io.appium.java_client.android.AndroidDriver;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;


public class HooksSteps {

    @Managed(driver = "appium")
    WebDriver hisMobileDevice; // Cambiado a WebDriver para mejor compatibilidad con el Facade

    @Before
    public void prepareActor() {
        OnStage.setTheStage(new OnlineCast());
        // Forzamos la inicialización llamando al driver antes de asignarlo
        hisMobileDevice.manage().window();
        // ...
        WebDriver driver = getDriver();

        OnStage.theActorCalled("Andres").can(BrowseTheWeb.with(hisMobileDevice));
    }

    @After
    public void tearDown() {
        OnStage.drawTheCurtain();
    }
}