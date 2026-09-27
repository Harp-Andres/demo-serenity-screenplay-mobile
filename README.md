# demo-serenity-screenplay-mobile

Teaching demo for **mobile automation with Serenity BDD and the Screenplay pattern** (Actor, Task, Question, UI targets) on **Appium**, with rich **Serenity reports** (`aggregate`). The app under test is **[TheApp](https://github.com/appium-pro/TheApp)** (`com.appiumpro.the_app`); demo login is `alice` / `mypassword`. This repo is not a multi-farm framework and not classic POM—for those, use sibling repos [`appium-mobile-automation-framework`](https://github.com/Harp-Andres/appium-mobile-automation-framework) (local Appium/Cucumber) and [`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework) (BrowserStack / AWS Device Farm).

## Run

```bash
# Unit tests (no Appium / no device) — CI default
./gradlew test

# Local E2E (Appium on :4723 + emulator/device)
./scripts/download-test-apps.sh   # → apps/TheApp.apk (see serenity.conf)
./gradlew e2e aggregate

# Docker Appium (host still needs a device/emulator reachable from the container)
docker compose up -d appium
./gradlew e2e aggregate

# BrowserStack (upload TheApp.apk first — see docs/TEST_APP_AND_FARMS.md)
export BROWSERSTACK_USERNAME=...
export BROWSERSTACK_ACCESS_KEY=...
export BROWSERSTACK_APP_URL=bs://...
./gradlew e2e -Dproperties=src/test/resources/serenity-browserstack.conf
```

App download details and cloud profiles: [`docs/TEST_APP_AND_FARMS.md`](docs/TEST_APP_AND_FARMS.md).
