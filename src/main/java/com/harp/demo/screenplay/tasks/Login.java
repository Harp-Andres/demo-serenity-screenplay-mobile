package com.harp.demo.screenplay.tasks;

import com.harp.demo.screenplay.interactions.EnterText;
import com.harp.demo.screenplay.ui.LoginUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Submit credentials on TheApp Login Screen (form must already be open).
 */
public class Login implements Task {

    private final String user;
    private final String pass;

    public Login(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public static Login withCredentials(String user, String pass) {
        return new Login(user, pass);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(LoginUI.USERNAME, isVisible()).forNoMoreThan(20).seconds(),
                EnterText.into(LoginUI.USERNAME, user),
                EnterText.into(LoginUI.PASSWORD, pass),
                Click.on(LoginUI.BUTTON_SIGN_IN)
        );
    }
}
