# Diseño del Framework de Automatización Mobile

Este documento propone una arquitectura para un framework de automatización mobile que cumple los requisitos solicitados:
- Soporte multiplataforma: Android e iOS.
- Reutilización de código entre módulos.
- Escalabilidad para agregar futuras pruebas y funcionalidades.
- Uso de Java 25 como lenguaje principal.
- Uso de TestNG (se explica la opción recomendada).
- Uso de Appium para la automatización mobile.
- Gestión de dependencias con Gradle.
- Integración de logging mediante SLF4J + Log4j2.

Adicional:
- Uso de Serenity BDD para generacion de reportes y mejor implementacion de patron de diseño screenplay.

---

Checklist (lo que cubre este documento)
- [x] Diagrama/estructura del proyecto
- [x] Patrones de diseño y arquitectónicos
- [x] Gestión de configuración para plataformas
- [x] Estrategia de logging y reportes
- [x] Escalabilidad y mantenibilidad
- [x] Organización de dependencias y ejecución de tests


1. Diagrama/estructura del proyecto
-----------------------------
Objetivo: Adaptar la estructura del proyecto al patrón Screenplay y aprovechar Serenity BDD para gestión de actores, tareas, preguntas y reportes detallados.
Requisitos cumplidos: Android e iOS, reutilización entre módulos, escalabilidad, Java 25, TestNG, Appium, Gradle, logging (SLF4J/Log4j), Serenity BDD.

1) Diagrama / Estructura del proyecto 

Estructura propuesta (multi-módulo Gradle, orientada a Screenplay):

- root/
  - build.gradle
  - settings.gradle
  - gradle.properties
  - serenity.properties
  - common-core/                # utilidades y abstracciones independientes
  - serenity-screenplay/        # Actors, Abilities, Tasks, Interactions, Questions, UI Targets
  - mobile-android/             # implementaciones/capabilities Android
  - mobile-ios/                 # implementaciones/capabilities iOS
  - tests-e2e/                  # suites / runners / feature files (si aplica)

Explicación breve de carpetas:
- `common-core`: `DriverManager` (ThreadLocal), `ConfigManager`, helpers genéricos, logging wrapper.
- `serenity-screenplay`: todo lo relativo a Screenplay (actors, abilities, tasks, interactions, questions, targets).
- `mobile-android` / `mobile-ios`: código específico de plataforma (capabilities, locators alternativos si aplica).
- `tests-e2e`: tests que orquestan actores; aquí vive `serenity.properties` si prefieres por módulo.

2) Patrones de diseño y arquitectónicos

- Screenplay (obligatorio por el enfoque): Actors, Abilities, Tasks, Interactions, Questions, UI Targets.
- Factory / Builder: construir `AppiumDriver` y `DesiredCapabilities` de forma legible y configurable.
- ThreadLocal DriverManager: asegurar aislamiento en ejecución paralela.
- Adapter: encapsular diferencias entre APIs externas (Appium/Selenium) hacia interfaces del framework.
- Dependency Injection (ligera): constructor injection para facilitar tests unitarios y reutilización.




3. Componentes clave y responsabilidades
---------------------------------------
- DriverManager (common-core)
  - Responsable de inicializar y exponer el driver Appium.
  - Implementación orientada a hilos (ThreadLocal) para ejecución paralela.
  - Factory que elige la implementación AndroidDriver / IOSDriver según configuración.

- Capabilities Provider (módulo platform-specific)
  - Encapsula DesiredCapabilities / Options para cada plataforma.
  - Expone un POJO o builder con valores por entorno (local, device farm, emulador).

- Screen Objects / Page Objects
  - Pattern: Screen Object Model (SOM) — cada pantalla como clase con elementos y acciones.
  - Mantener solo interacción con UI aquí; lógica de negocio en un Service Layer si aplica.

- Service Layer / Interaction Layer
  - Encapsula flujos de negocio reutilizables (login, checkout), invocado por tests.

- Config Management
  - Centralizado en `common-core.config`.
  - Permitir múltiples fuentes: properties/YAML, variables de entorno, CLI args, y secretos (vault/CI variables).

- Test Runner
  - Tests escritos usando TestNG (recomendado por su manejo superior de paralelismo y parametrización) o JUnit 5.
  - Uso de listeners y hooks (BeforeSuite/BeforeClass/BeforeMethod/AfterMethod) para setup/teardown.

- Logging & Reporting
  - Logging via SLF4J + Log4j2 (SLF4J façade permite cambiar impl. si hace falta).
  - Reports: integrar Allure para reportes detallados (screenshots, attachments) y/o generar XML/JUnit reports para CI.


4. Patrones de diseño y arquitectónicos
--------------------------------------
- Page/Screen Object Pattern
  - Separación clara entre representación UI y tests.

- Factory + Strategy
  - Para crear el driver y elegir la estrategia de ejecución (local, remoto, cloud)

- Singleton (con cautela)
  - Para componentes globales inmutables (por ejemplo, configuración estática), preferir inyección o acceso thread-safe.

- Dependency Injection (ligera)
  - No es imprescindible Spring; usar un pequeño contenedor o constructor injection manual para facilitar testing unitario.

- Builder
  - Para generar DesiredCapabilities/Options de forma legible y composable.

- Adapter
  - Para adaptar librerías externas (por ejemplo, diferentes versiones de Appium/Selenium) a una interfaz estable del framework.


5. Manejo de configuración para diferentes plataformas
------------------------------------------------------
Requisitos: soportar múltiples entornos (local, CI, SauceLabs, BrowserStack), distintos devices y configuraciones por plataforma.

Estrategia:
- Archivo de configuración base (e.g., `config/default.properties` o `config/application.yaml`).
- Archivos por entorno (e.g., `config/local.properties`, `config/ci.properties`).
- Variables de entorno sobrescriben propiedades (ej: DEVICE_NAME, PLATFORM_NAME, APPIUM_SERVER).
- CLI: permitir pasar `-Dprofile=ci` o `-Dplatform=android` al ejecutar `gradle`.
- Secrets: no versionar, leer desde CI secrets/vault o variables de entorno.

Ejemplo de prioridad (orden de resolución):
1. Argumentos CLI / System properties (-D)
2. Variables de entorno
3. Archivo por perfil (config/ci.properties)
4. Archivo default (config/default.properties)

Mapeo a código:
- Clase ConfigManager (singleton thread-safe) que centraliza lectura y resolución de propiedades.
- CapabilitiesProvider lee configuración del ConfigManager para construir DesiredCapabilities.


6. Estrategia de logs y reportes
--------------------------------
Logging:
- API: SLF4J
- Implementación: Log4j2 (por rendimiento y configurabilidad)
- Estructura: Logs por nivel (ERROR, WARN, INFO, DEBUG); formato con timestamp, thread, testId y clase.
- Appenders: consola, archivo por ejecución y rolling file.
- En ejecución paralela, incluir identificador del hilo/test en cada línea (ej: usando MDC — Mapped Diagnostic Context).

Captura de pruebas fallidas:
- En listener (TestNG ITestListener o JUnit TestWatcher) capturar screenshot y logs cuando falla un test y adjuntarlos al reporte.

Reportes:
- Allure: integrar para ver pasos, attachments (screenshots, logs), y parametrización.
- También generar reportes JUnit XML para CI.
- Ubicación de artefactos: `build/reports/tests/` y `build/allure-results/`.

Retención y análisis:
- Subir artefactos a CI build (GitHub Actions, Jenkins, GitLab CI) para inspección.


7. Ejecución de tests y gestión de dependencias
-----------------------------------------------
Gestor de dependencias: Gradle (módulo raíz, con submódulos). Recomendado: usar `dependencyManagement` y versiones centralizadas (vars en `gradle.properties` o `libs.versions.toml`).

Dependencias clave recomendadas:
- io.appium:java-client
- org.seleniumhq.selenium:selenium-java
- org.slf4j:slf4j-api + org.apache.logging.log4j:log4j-slf4j-impl
- org.testng:testng  (o org.junit.jupiter:junit-jupiter)
- io.qameta.allure:allure-testng (si se usa TestNG)

Ejemplo de tareas de Gradle (concepto):
- `./gradlew clean test` - Ejecuta tests locales.
- `./gradlew -Pplatform=android -Dprofile=ci test` - Ejecuta pruebas para Android con perfil `ci`.
- Integración con dispositivos remotos: `-Dappium.server=https://...`.

Paralelismo:
- Configurar TestNG para ejecución paralela a nivel de métodos/clases con hilos, y asegurarse de que DriverManager sea thread-safe (ThreadLocal).


8. Escalabilidad y mantenibilidad
---------------------------------
Cómo lo aseguramos:
- Modularidad: separar `common-core`, `mobile-android`, `mobile-ios` y `tests` permite iterar sin romper otras partes.
- Abstracciones: tests y servicios usan interfaces del `common-core`; las implementaciones concretas están en módulos platform.
- Contratos y CI: añadir linters, análisis estático y reglas de codificación. PRs con revisiones obligatorias.
- Test Data Management: usar data-driven tests (CSV/JSON/DB) y factories para crear test data.
- Versionado de API y compatibilidad: aislar adaptadores cuando actualices Appium/Selenium.

Refactor-friendly:
- Cobertura de pruebas unitarias para utilidades y servicios.
- Uso de mockables para tests unitarios (Mockito o similar).


9. Manejo de secretos y seguridad
---------------------------------
- No versionar credenciales.
- Leer claves desde variables de entorno o desde secret manager del CI (GitHub Secrets, Jenkins Credentials, GitLab CI Variables).
- En local, usar `~/.config/framework/credentials` con permisos restrictivos.


10. Ejemplo de flujo de inicialización (alto nivel)
--------------------------------------------------
1. Test runner invocado con parámetros `platform` y `profile`.
2. ConfigManager resuelve la configuración.
3. Test listener (BeforeClass/BeforeMethod) pide una instancia al DriverFactory.
4. DriverFactory usa CapabilitiesProvider y crea AppiumDriver (AndroidDriver / IOSDriver).
5. Test ejecuta pasos usando Screen Objects y Services.
6. OnFailure: listener captura screenshot, logs y los adjunta a Allure.
7. Teardown: driver.quit() y flush logs.


11. Contrato: entradas/salidas y modos de error
----------------------------------------------
Inputs:
- Parámetros: platform (android/ios), profile (local/ci), deviceName, platformVersion, app path, appium.server.
Outputs:
- Reports (Allure + JUnit XML), logs, screenshots.
Error modes:
- Conexión Appium fallida -> modo retry configurable + fallo limpio con logs.
- Capability mal formateadas -> validación temprana en CapabilitiesProvider.
- Recursos compartidos en paralelismo -> evitar globals; usar ThreadLocal y sincronización donde aplique.


12. Casos borde a considerar
---------------------------
- Execution en paralelo en la misma máquina con emuladores: consumo elevado de recursos.
- Flakiness de tests: añadir reintentos y estabilización (esperas explícitas y reducidas, robust locators).
- Diferencias de UI entre plataformas: abstraer flujos comunes y tener implementaciones específicas por plataforma.


13. Buenas prácticas y recomendaciones adicionales
--------------------------------------------------
- Localizar test data y strings en recursos que puedan parametrizarse por idioma/país.
- Locator strategy: preferir accesibility id / resource-id; evitar XPath complejos cuando sea posible.
- Timeouts y esperas: usar ExpectedConditions con tiempo configurable desde ConfigManager.
- Versiones fijas y centralizadas de dependencias para evitar roturas inesperadas.


14. Ejemplos de comandos de uso
-------------------------------
(En Windows PowerShell)

```powershell
# Ejecutar todos los tests con gradle (módulo app en este repo de ejemplo)
./gradlew clean test

# Ejecutar tests para Android con perfil CI
./gradlew -Pplatform=android -Dprofile=ci test

# Generar reporte allure (si está configurado)
./gradlew allureServe
```


15. Integración continua sugerida
---------------------------------
- Pipeline steps:
  1. Checkout
  2. Build (gradle assemble)
  3. Ejecutar pruebas en emuladores/real devices (o device cloud)
  4. Subir artefactos (Allure results, logs, screenshots)
  5. Publicar reporte

- Recomendación: ejecutar pruebas críticas en cada PR y suites completas en nightly.


16. Mapa rápido de responsabilidades por paquete
-------------------------------------------------
- com.company.framework.config -> lectura y resolución de configuraciones
- com.company.framework.driver -> DriverFactory, DriverManager (ThreadLocal)
- com.company.framework.screens -> base ScreenObject
- com.company.android.screens -> Implementaciones Android
- com.company.ios.screens -> Implementaciones iOS
- com.company.framework.report -> Integración con Allure/Report adapters


17. Roadmap / próximas mejoras (opcionals)
------------------------------------------
- Integrar un pequeño DI (Dagger/Hilt) si se vuelve complejo.
- Añadir maven/gradle publishing para artefactos comunes.
- Crear dashboards de health/analytics (tests flakiness, duración promedio).
- Soporte para versiones de dispositivos/OS matrix-driven.


18. Recomendaciones finales
---------------------------
- Empezar por crear `common-core` con DriverManager y ConfigManager; luego agregar `mobile-android` y `mobile-ios` con ejemplos mínimos de screen objects.
- Mantener las dependencias centralizadas y documentadas.
- Usar TestNG si se prioriza paralelismo y parametrización; JUnit 5 es válido si se prefiere su ecosistema.


19. Cobertura de requisitos (resumen)
------------------------------------
- Soporte multiplataforma: Hecho (arquitectura multi-módulo + abstractions).
- Reutilización de código: Hecho (common-core y adapters).
- Escalabilidad: Hecho (modularidad y paralelismo soportado).
- Java 11+: Recomendado (Gradle toolchain en build.gradle).
- JUnit/TestNG: Recomendado TestNG por paralelismo, JUnit 5 opcional.
- Appium: Integrado como motor de tests.
- Gradle: Uso recomendado y mostrado en ejemplos.
- Logging: SLF4J + Log4j2 y uso de MDC para paralelismo.


Anexos: enlaces y snippets
-------------------------
- Allure: https://docs.qameta.io/allure/
- Appium Java client: https://github.com/appium/java-client
- SLF4J / Log4j2 docs: https://logging.apache.org/log4j/2.x/


---

Si quieres, puedo:
- Generar el esqueleto de carpetas/módulos y archivos Java/Gradle iniciales en tu repo.
- Crear ejemplos concretos de DriverManager, ConfigManager y un Screen Object.
- Proveer un archivo `libs.versions.toml` o actualizar `app/build.gradle` con las versiones recomendadas.

Dime cuál de las tareas adicionales quieres que haga y la implemento en el repo.
