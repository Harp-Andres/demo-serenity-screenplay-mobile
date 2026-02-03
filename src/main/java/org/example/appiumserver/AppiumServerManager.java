package org.example.appiumserver;


import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

import java.time.Duration;
/*

public class AppiumServerManager {
    private static final ThreadLocal<AppiumDriverLocalService> threadLocalService = new ThreadLocal<>();
    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

    public static void startServer() {
        if (threadLocalService.get() != null && threadLocalService.get().isRunning()) {
            return;
        }

        int port = variables.getPropertyAsInteger("appium.port", 4723);
        String ip = variables.getProperty("appium.ip", "127.0.0.1");

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withIPAddress(ip)
                .usingPort(port)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                .withTimeout(Duration.ofSeconds(30)); // Añadir timeout para inicio

        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
        service.start();

        if (!service.isRunning()) {
            throw new RuntimeException("¡El servidor Appium no pudo iniciar en el puerto " + port + "!");
        }

        threadLocalService.set(service);

        // **CRÍTICO**: Sobreescribir la URL de Serenity en tiempo de ejecución
        System.setProperty("webdriver.appium.url", service.getUrl().toString());
    }

    public static void stopServer() {
        if (threadLocalService.get() != null) {
            threadLocalService.get().stop();
            threadLocalService.remove();
            // Limpiar la propiedad del sistema al cerrar
            System.clearProperty("webdriver.appium.url");
        }
    }
}

 */