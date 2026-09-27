package com.harp.demo.screenplay.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnterText implements Interaction {

    private static final Logger LOG = LoggerFactory.getLogger(EnterText.class);
    private final Target target;
    private final String text;

    public EnterText(Target target, String text) {
        this.target = target;
        this.text = text;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        LOG.debug("Entering text into target {}", target.getName());
        actor.attemptsTo(Enter.theValue(text).into(target));
    }

    public static EnterText into(Target target, String text) {
        return new EnterText(target, text);
    }
}
