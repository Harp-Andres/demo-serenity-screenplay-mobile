# Serenity BDD Screenplay — Mobile teaching demo

Educational sample project for **mobile test automation** using **Java**, **Serenity BDD**, the **Screenplay pattern**, **Cucumber**, and **Appium**. It shows how to structure actors, tasks, questions, UI targets, and hooks for Android/iOS without coupling tests to `org.example`-style placeholders.

## Stack

| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Build | Gradle (wrapper) |
| BDD / reporting | Serenity BDD 5.x |
| Pattern | Screenplay (actors, tasks, questions, interactions) |
| Scenarios | Cucumber (Spanish features) |
| Driver | Appium Java Client |
| Logging | SLF4J → Log4j2 (`src/test/resources/log4j2.xml`) |

## Project layout

```
src/main/java/com/harp/demo/screenplay/
  abilities/       # Custom abilities (e.g. mobile)
  appiumserver/    # Optional local Appium lifecycle helpers
  interactions/    # Low-level Screenplay interactions
  models/          # Plain domain models (e.g. User)
  questions/       # Screenplay questions
  tasks/           # Business-facing tasks
  ui/              # Targets (page/screen objects)
  utils/           # Pure helpers (unit-tested)
src/test/java/com/harp/demo/screenplay/
  runners/         # JUnit Platform Cucumber suites
  stepsdefinitions/# Step definitions + hooks
  unit/            # Fast unit tests (no device required)
src/test/resources/
  features/        # Gherkin features
  serenity.conf    # Appium capabilities
  log4j2.xml
```

## Prerequisites

- JDK 21+
- Android SDK / emulator or iOS simulator (for E2E only)
- Appium 2.x server reachable at `http://127.0.0.1:4723` (see `serenity.conf`)

## Commands

```bash
# Unit tests (default `test` task — no Appium required)
./gradlew test

# Mobile E2E (Cucumber + Serenity; requires device + Appium)
./gradlew e2e aggregate
```

## Learning path

1. Read `login.feature` and trace glue in `LoginSteps`.
2. Follow the Screenplay flow: **Task** (`Login`) → **UI target** (`LoginUI`) → **Question** (`IsElementVisible`).
3. Inspect `HooksSteps` for actor lifecycle and logging.
4. Extend `utils.Util` with small pure functions and cover them under `unit/`.

## Suggested GitHub repository name

`demo-serenity-screenplay-mobile`

## License

MIT (see repository settings).
