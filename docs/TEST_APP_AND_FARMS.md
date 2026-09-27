# TheApp + where to run Serenity E2E

## App under test

| Item | Value |
| --- | --- |
| App | [TheApp](https://github.com/appium-pro/TheApp) (Appium Pro) |
| Android package | `com.appiumpro.the_app` |
| Demo login | `alice` / `mypassword` |
| APK | `apps/TheApp.apk` via `./scripts/download-test-apps.sh` |

## Execution options (this repo)

| Mode | Command / conf |
| --- | --- |
| Unit (no device) | `./gradlew test` |
| Laptop Appium | `serenity.conf` + host `:4723` |
| **Free Docker/K8s farm** | `./scripts/farm-up.sh` + `serenity-farm.conf` — see [`FREE_DEVICE_FARM.md`](FREE_DEVICE_FARM.md) |
| BrowserStack (optional) | `serenity-browserstack.conf` |

## Sibling specialties

| Repo | Focus |
| --- | --- |
| `appium-mobile-automation-framework` | Mature Windows **self-hosted** runner + TheApp |
| `appium-mobile-cloud-automation-framework` | BrowserStack / AWS Device Farm |
| **This repo** | Serenity Screenplay + **free personal farm** (Docker/K8s virtual devices) |
