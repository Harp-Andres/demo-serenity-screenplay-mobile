package com.harp.demo.screenplay.stepsdefinitions;

import com.harp.demo.screenplay.questions.IsElementVisible;
import com.harp.demo.screenplay.tasks.Login;
import com.harp.demo.screenplay.ui.HomePageUI;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * TheApp credentials (public demo): alice / mypassword
 */
public class LoginSteps {

    private static final String DEMO_USER = "alice";
    private static final String DEMO_PASSWORD = "mypassword";

    @Dado("que el usuario está en la página de inicio de sesión de la aplicación")
    public void ingresarAlModalDeInicioDeLaApplicacion() {
        OnStage.theActorCalled("Andres").wasAbleTo(
                WaitUntil.the(HomePageUI.LOGIN_SCREEN_ENTRY, isVisible()).forNoMoreThan(60).seconds(),
                Ensure.that(
                        "Login Screen entry is visible on TheApp home",
                        IsElementVisible.forTarget(HomePageUI.LOGIN_SCREEN_ENTRY)
                ).isTrue()
        );
    }

    @Cuando("el usuario ingresa las credenciales validas")
    public void ingresarCredencialesValidas() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Login.withCredentials(DEMO_USER, DEMO_PASSWORD)
        );
    }

    @Entonces("el usuario debería ver la Home page de la aplicación")
    public void verificarIngresoALaHomePage() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(HomePageUI.LOGGED_IN_MESSAGE, isVisible()).forNoMoreThan(30).seconds(),
                Ensure.that(
                        "Secret area shows logged-in message",
                        IsElementVisible.forTarget(HomePageUI.LOGGED_IN_MESSAGE)
                ).isTrue()
        );
    }
}
