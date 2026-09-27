package com.harp.demo.screenplay.ui;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * TheApp login form — same accessibility IDs on Android and iOS.
 */
public final class LoginUI {

    private LoginUI() {
    }

    public static final Target USERNAME = Target.the("username field")
            .located(AppiumBy.accessibilityId("username"));

    public static final Target PASSWORD = Target.the("password field")
            .located(AppiumBy.accessibilityId("password"));

    public static final Target BUTTON_SIGN_IN = Target.the("login button")
            .located(AppiumBy.accessibilityId("loginBtn"));

    /** Alert body from TheApp: "Invalid login credentials, please try again". */
    public static final Target INVALID_CREDENTIALS_ALERT = Target.the("invalid credentials alert")
            .locatedForAndroid(By.xpath("//*[contains(@text,'Invalid login credentials')]"))
            .locatedForIOS(By.xpath("//*[contains(@name,'Invalid login credentials') or contains(@label,'Invalid login credentials')]"));
}
