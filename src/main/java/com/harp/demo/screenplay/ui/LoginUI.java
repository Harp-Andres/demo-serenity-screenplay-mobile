package com.harp.demo.screenplay.ui;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class LoginUI {

    // Definimos el Target con soporte multiplataforma
    public static final Target BUTTON_SIGN_IN = Target.the("botón iniciar sesion")
            .locatedForAndroid(AppiumBy.id("com.google.android.apps.safetyhub:id/sign_in_button"))
            .locatedForIOS(AppiumBy.accessibilityId("menu_button_ios"));//Es inventado
}
