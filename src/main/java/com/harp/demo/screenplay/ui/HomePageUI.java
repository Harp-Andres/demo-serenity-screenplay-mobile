package com.harp.demo.screenplay.ui;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * TheApp (com.appiumpro.the_app) home + post-login secret area.
 * Locators aligned with https://github.com/appium-pro/TheApp
 */
public final class HomePageUI {

    private HomePageUI() {
    }

    public static final Target LOGIN_SCREEN_ENTRY = Target.the("Login Screen entry on home")
            .located(AppiumBy.accessibilityId("Login Screen"));

    public static final Target LOGGED_IN_MESSAGE = Target.the("logged in confirmation message")
            .locatedForAndroid(By.xpath("//android.widget.TextView[contains(@text, 'You are logged in as')]"))
            .locatedForIOS(By.xpath("//XCUIElementTypeStaticText[contains(@name, 'You are logged in as')]"));

    public static final Target LOGOUT_BUTTON = Target.the("Logout button")
            .locatedForAndroid(By.xpath("//*[@text='Logout']"))
            .locatedForIOS(By.xpath("//*[@name='Logout']"));
}
