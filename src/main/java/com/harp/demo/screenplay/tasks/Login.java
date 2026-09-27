package com.harp.demo.screenplay.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import com.harp.demo.screenplay.ui.LoginUI;

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
        actor.attemptsTo(Click.on(LoginUI.BUTTON_SIGN_IN));


    }
}
