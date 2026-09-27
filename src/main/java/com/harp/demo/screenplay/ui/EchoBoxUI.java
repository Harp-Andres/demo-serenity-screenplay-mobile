package com.harp.demo.screenplay.ui;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * TheApp Echo Box demo screen (public AUT).
 */
public final class EchoBoxUI {

    private EchoBoxUI() {
    }

    public static final Target ECHO_BOX_ENTRY = Target.the("Echo Box entry on home")
            .located(AppiumBy.accessibilityId("Echo Box"));

    public static final Target MESSAGE_INPUT = Target.the("echo message input")
            .located(AppiumBy.accessibilityId("messageInput"));

    public static final Target SAVE_BUTTON = Target.the("save message button")
            .located(AppiumBy.accessibilityId("messageSaveBtn"));

    /**
     * Saved message label — TheApp exposes the value as visible text after Save
     * (testID varies by build; text match is stable for the demo assertion).
     */
    public static Target savedMessageShowing(String text) {
        String safe = text.replace("\"", "");
        return Target.the("saved echo message '" + safe + "'")
                .locatedForAndroid(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"" + safe + "\")"
                ))
                .locatedForIOS(By.xpath("//*[@name='" + safe + "' or @label='" + safe + "' or @value='" + safe + "']"));
    }
}
