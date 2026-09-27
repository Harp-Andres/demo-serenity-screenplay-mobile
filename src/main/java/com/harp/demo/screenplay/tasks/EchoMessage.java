package com.harp.demo.screenplay.tasks;

import com.harp.demo.screenplay.interactions.EnterText;
import com.harp.demo.screenplay.ui.EchoBoxUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Open Echo Box from home and save a message. */
public class EchoMessage implements Task {

    private final String message;

    public EchoMessage(String message) {
        this.message = message;
    }

    public static EchoMessage withText(String message) {
        return new EchoMessage(message);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(EchoBoxUI.ECHO_BOX_ENTRY, isVisible()).forNoMoreThan(60).seconds(),
                Click.on(EchoBoxUI.ECHO_BOX_ENTRY),
                WaitUntil.the(EchoBoxUI.MESSAGE_INPUT, isVisible()).forNoMoreThan(30).seconds(),
                EnterText.into(EchoBoxUI.MESSAGE_INPUT, message),
                Click.on(EchoBoxUI.SAVE_BUTTON),
                WaitUntil.the(EchoBoxUI.SAVED_MESSAGE, isVisible()).forNoMoreThan(20).seconds()
        );
    }
}
