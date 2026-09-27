package com.harp.demo.screenplay.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.Actor;
import java.time.Duration;

public class IsElementVisible implements Question<Boolean> {

    private final Target target;
    private Duration timeout = Duration.ofSeconds(30); // Tiempo por defecto

    public IsElementVisible(Target target) {
        this.target = target;
    }

    public static IsElementVisible forTarget(Target target) {
        return new IsElementVisible(target);
    }

    public IsElementVisible withTimeoutOf(int seconds) {
        this.timeout = Duration.ofSeconds(seconds);
        return this;
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return target.waitingForNoMoreThan(timeout).resolveFor(actor).isCurrentlyVisible();
    }
}
