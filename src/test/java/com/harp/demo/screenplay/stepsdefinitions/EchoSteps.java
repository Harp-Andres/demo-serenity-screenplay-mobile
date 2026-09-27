package com.harp.demo.screenplay.stepsdefinitions;

import com.harp.demo.screenplay.questions.IsElementVisible;
import com.harp.demo.screenplay.tasks.EchoMessage;
import com.harp.demo.screenplay.ui.EchoBoxUI;
import com.harp.demo.screenplay.ui.HomePageUI;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EchoSteps {

    @Dado("que el usuario está en la home de TheApp")
    public void onHome() {
        OnStage.theActorCalled("Andres").wasAbleTo(
                WaitUntil.the(HomePageUI.LOGIN_SCREEN_ENTRY, isVisible()).forNoMoreThan(60).seconds(),
                Ensure.that(
                        "TheApp home shows Login Screen entry",
                        IsElementVisible.forTarget(HomePageUI.LOGIN_SCREEN_ENTRY)
                ).isTrue()
        );
    }

    @Cuando("el usuario guarda el mensaje {string} en Echo Box")
    public void saveEcho(String message) {
        OnStage.theActorInTheSpotlight().attemptsTo(EchoMessage.withText(message));
    }

    @Entonces("debería ver el mensaje guardado {string}")
    public void seeSaved(String expected) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(EchoBoxUI.SAVED_MESSAGE, isVisible()).forNoMoreThan(20).seconds(),
                Ensure.that(Text.of(EchoBoxUI.SAVED_MESSAGE)).contains(expected)
        );
    }
}
