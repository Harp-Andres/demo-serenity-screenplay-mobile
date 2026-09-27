# TheApp + where to run Serenity E2E

## App under test

| Item | Value |
| --- | --- |
| App | [TheApp](https://github.com/appium-pro/TheApp) (Appium Pro) |
| Android package | `com.appiumpro.the_app` |
| Demo login | `alice` / `mypassword` |
| Local APK path | `apps/TheApp.apk` (via `./scripts/download-test-apps.sh`) |

`serenity.conf` points at `${user.dir}/apps/TheApp.apk` and `http://127.0.0.1:4723`. Do not commit `apps/*.apk` (gitignored).

## Serenity execution options (this repo)

| Mode | Command |
| --- | --- |
| Unit (no device) | `./gradlew test` |
| Local Appium | `./scripts/download-test-apps.sh` then `./gradlew e2e aggregate` |
| Docker Appium | `docker compose up -d appium` then `./gradlew e2e aggregate` |
| BrowserStack | `./gradlew e2e -Dproperties=src/test/resources/serenity-browserstack.conf` (set `BROWSERSTACK_*` env vars) |

Cloud profiles are **additive**; local `serenity.conf` stays the default so laptop + emulator workflows keep working.

## Multi-farm depth (sibling repos)

| Repo | Focus |
| --- | --- |
| `appium-mobile-automation-framework` | Local Appium + Cucumber (keep local green) |
| `appium-mobile-cloud-automation-framework` | BrowserStack, AWS Device Farm, farm wiring |
| **This repo** | Serenity Screenplay + reporting; optional Docker Appium hub you control |

For Sauce sample IPA, ApiDemos, Device Farm custom environments, and grid/K8s notes, follow the cloud and local framework repos above—this demo stays Serenity-centric.
