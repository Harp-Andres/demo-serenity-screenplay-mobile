package com.harp.demo.screenplay.tasks;

import com.harp.demo.screenplay.ui.HomePageUI;
import com.harp.demo.screenplay.ui.LoginUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** From TheApp home, open the Login Screen form. */
public class OpenLoginScreen implements Task {

    public static OpenLoginScreen fromHome() {
        return new OpenLoginScreen();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(HomePageUI.LOGIN_SCREEN_ENTRY, isVisible()).forNoMoreThan(60).seconds(),
                Click.on(HomePageUI.LOGIN_SCREEN_ENTRY),
                WaitUntil.the(LoginUI.USERNAME, isVisible()).forNoMoreThan(30).seconds()
        );
    }
}
