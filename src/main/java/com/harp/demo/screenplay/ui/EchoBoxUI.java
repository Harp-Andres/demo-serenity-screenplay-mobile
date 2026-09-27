package com.harp.demo.screenplay.ui;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

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

    public static final Target SAVED_MESSAGE = Target.the("saved echo message")
            .located(AppiumBy.accessibilityId("@TheApp:savedEcho"));
}
