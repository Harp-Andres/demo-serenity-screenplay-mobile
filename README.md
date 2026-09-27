# demo-serenity-screenplay-mobile

Educational demo for **mobile test automation** with **Java**, **Serenity BDD**, the **Screenplay pattern**, **Cucumber**, and **Appium**.

Specialty vs sibling repos:

| Repo | Focus |
| --- | --- |
| **This one** | Serenity Screenplay + reporting (`aggregate`) |
| `appium-mobile-automation-framework` | Local Appium/Cucumber (keep local green) |
| `appium-mobile-cloud-automation-framework` | BrowserStack + AWS Device Farm |

## App under test (free)

**[TheApp](https://github.com/appium-pro/TheApp)** — APK + iOS Simulator zip, accessibility IDs built for Appium.

```bash
./scripts/download-test-apps.sh
```

Demo login: `alice` / `mypassword`  
Details & farm strategy: [`docs/TEST_APP_AND_FARMS.md`](docs/TEST_APP_AND_FARMS.md)

## Stack

| Layer | Choice |
| --- | --- |
| Language | Java 21 |
| BDD UI | Cucumber + Serenity Screenplay |
| Driver | Appium Java Client |
| Logging | SLF4J → Log4j2 |

## Commands

```bash
# Unit tests (no Appium / no device) — CI default
./gradlew test

# Local E2E (Appium on :4723 + emulator/device + TheApp.apk)
./scripts/download-test-apps.sh
./gradlew e2e aggregate

# Appium via Docker (host must expose a device/emulator to the container)
docker compose up -d appium
./gradlew e2e aggregate

# BrowserStack (after uploading TheApp.apk — see docs)
export BROWSERSTACK_USERNAME=...
export BROWSERSTACK_ACCESS_KEY=...
export BROWSERSTACK_APP_URL=bs://...
./gradlew e2e -Dproperties=src/test/resources/serenity-browserstack.conf
```

## Why Docker/K8s here?

Other repos already cover local Appium and commercial farms. This repo’s differentiator is **Serenity Screenplay + a hub you control** (Docker Appium now; Device Farmer / Appium Grid later for a personal multi-device farm). Kubernetes can schedule Appium workers, but phones still need USB/network attachment or a cloud farm behind the hub.
