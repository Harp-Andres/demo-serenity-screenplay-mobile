package org.example.stepsdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.data.UserLoader;
import org.example.models.UserModel;
import org.example.questions.IsElementVisible;
import org.example.tasks.Login;
import org.example.ui.LoginUI;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginSteps {

    @Dado("que el usuario está en la página de inicio de sesión de la aplicación")
    public void ingresarAlModalDeInicioDeLaApplicacion() {
        System.out.println("Estoy en el paso de inicio de sesión");
        OnStage.theActorCalled("Andres").wasAbleTo(
                //WaitUntil.the(LoginUI.BUTTON_SIGN_IN, isPresent()).forNoMoreThan(60).seconds()
                //Ensure.that("El botón de inicio es visible",
                  //      IsElementVisible.forTarget(LoginUI.BUTTON_SIGN_IN)).isTrue()
        );
    }

    @Cuando("el usuario ingresa las credenciales validas")
    public void ingresarCredencialesValidas() {
        UserModel userData = UserLoader.fromJson("credentials");
        OnStage.theActorInTheSpotlight().attemptsTo(
                Login.withCredentials(userData.getUser(), userData.getPassword())
        );
    }

    @Entonces("el usuario debería ver la Home page de la aplicación")
    public void verificarIngresoALaHomePage() {

    }
}
