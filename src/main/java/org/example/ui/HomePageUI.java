package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import io.appium.java_client.AppiumBy;

public class HomePageUI {
    // Definimos el Target con soporte multiplataforma
    public static final Target BUTTON_MENU = Target.the("botón de menú principal")
            .locatedForAndroid(AppiumBy.id("com.grability.rappi:id/menu_image"))
            .locatedForIOS(AppiumBy.accessibilityId("menu_button_ios"));
}