package com.harp.demo.screenplay.stepsdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import com.harp.demo.screenplay.questions.IsElementVisible;
import com.harp.demo.screenplay.tasks.Login;
import com.harp.demo.screenplay.ui.LoginUI;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginSteps {

    @Dado("que el usuario está en la página de inicio de sesión de la aplicación")
    public void ingresarAlModalDeInicioDeLaApplicacion() {
        OnStage.theActorCalled("Andres").wasAbleTo(
                WaitUntil.the(LoginUI.BUTTON_SIGN_IN, isVisible()).forNoMoreThan(60).seconds(),
                Ensure.that("El botón de inicio es visible",
                        IsElementVisible.forTarget(LoginUI.BUTTON_SIGN_IN)).isTrue()
        );
    }

    @Cuando("el usuario ingresa las credenciales validas")
    public void ingresarCredencialesValidas() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Login.withCredentials("usuario@test.com", "12345")
        );
    }

    @Entonces("el usuario debería ver la Home page de la aplicación")
    public void verificarIngresoALaHomePage() {

    }
}
