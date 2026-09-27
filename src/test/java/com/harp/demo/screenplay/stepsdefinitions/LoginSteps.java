package com.harp.demo.screenplay.stepsdefinitions;

import com.harp.demo.screenplay.questions.IsElementVisible;
import com.harp.demo.screenplay.tasks.Login;
import com.harp.demo.screenplay.tasks.OpenLoginScreen;
import com.harp.demo.screenplay.ui.HomePageUI;
import com.harp.demo.screenplay.ui.LoginUI;
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

    @Dado("que el usuario abre la pantalla de Login de TheApp")
    public void openLoginScreen() {
        OnStage.theActorCalled("Andres").wasAbleTo(OpenLoginScreen.fromHome());
    }

    @Cuando("el usuario ingresa las credenciales validas")
    public void ingresarCredencialesValidas() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Login.withCredentials(DEMO_USER, DEMO_PASSWORD)
        );
    }

    @Cuando("el usuario ingresa las credenciales invalidas")
    public void ingresarCredencialesInvalidas() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Login.withCredentials("wrong", "badpass")
        );
    }

    @Entonces("el usuario debería ver el mensaje de sesión iniciada")
    public void verificarSesionIniciada() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(HomePageUI.LOGGED_IN_MESSAGE, isVisible()).forNoMoreThan(30).seconds(),
                Ensure.that(
                        "Secret area shows logged-in message",
                        IsElementVisible.forTarget(HomePageUI.LOGGED_IN_MESSAGE)
                ).isTrue()
        );
    }

    @Entonces("el usuario debería ver el mensaje de credenciales inválidas")
    public void verificarCredencialesInvalidas() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(LoginUI.INVALID_CREDENTIALS_ALERT, isVisible()).forNoMoreThan(20).seconds(),
                Ensure.that(
                        "Invalid credentials feedback is visible",
                        IsElementVisible.forTarget(LoginUI.INVALID_CREDENTIALS_ALERT)
                ).isTrue()
        );
    }
}
