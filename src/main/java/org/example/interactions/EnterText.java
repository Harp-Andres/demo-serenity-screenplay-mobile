package org.example.interactions;

import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.Actor;

public class EnterText implements Interaction {
    private final Target target;
    private final String text;

    public EnterText(Target target, String text) {
        this.target = target;
        this.text = text;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(text).into(target));
    }

    public static EnterText into(Target target, String text) {
        return new EnterText(target, text);
    }
}
