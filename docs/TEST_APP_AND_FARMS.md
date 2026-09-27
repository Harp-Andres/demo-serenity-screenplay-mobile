# App under test + execution farms

## Recommended free apps (shared across mobile demos)

| App | Android | iOS | Best for | License |
| --- | --- | --- | --- | --- |
| **[TheApp](https://github.com/appium-pro/TheApp)** (Appium Pro) | `TheApp.apk` | `TheApp.app.zip` (simulator) | Screenplay login, lists, webviews; accessibility IDs designed for Appium | Free / open |
| **[Sauce Labs Sample App](https://github.com/saucelabs/sample-app-mobile)** | `.apk` | **Real-device `.ipa`** + sim zip | BrowserStack / AWS real iOS devices | Free sample |
| **ApiDemos** (`io.appium.android.apis`) | APK only | — | Android widget/deep navigation (cloud sibling repo) | Free / Appium |

### Decision for this monorepo of demos

1. **Default shared AUT = TheApp** for Android + iOS Simulator (this Serenity repo + local Appium).
2. **Real iOS on BrowserStack/AWS** → also download Sauce Sample `.ipa` (`WITH_SAUCE_IOS_IPA=1`) or use BrowserStack sample IPA temporarily.
3. **Do not** automate Play Store apps (Safety Hub, Rappi, etc.): not portable to farms, flaky, and often not redistributable.

Download:

```bash
./scripts/download-test-apps.sh
# optional real-device iOS:
WITH_SAUCE_IOS_IPA=1 ./scripts/download-test-apps.sh
```

TheApp login demo credentials: `alice` / `mypassword`  
Package / bundle id: `com.appiumpro.the_app`

---

## How each of your mobile repos should run

| Repo | Specialty | Local | BrowserStack | AWS Device Farm | Personal farm (Docker/K8s) |
| --- | --- | --- | --- | --- | --- |
| `appium-mobile-automation-framework` | **Local** Appium + Cucumber | Primary (keep working) | Out of scope (sibling) | Out of scope | Optional self-hosted GA runner |
| `appium-mobile-cloud-automation-framework` | **Multi-farm** cloud | Fallback only | Primary | Primary (restore) | Optional |
| `demo-serenity-screenplay-mobile` | **Serenity Screenplay** integration | Appium local / Docker Appium | Via `ENV=browserstack` | Via Device Farm custom test env | **Differential**: Appium Grid / Device Farmer |

### Important: do not break local

Local remains `ENV=local` (or default `serenity.conf`) pointing at `http://127.0.0.1:4723` + `apps/TheApp.apk`.  
Cloud profiles are **additive** (`serenity-browserstack.conf`, Gradle `-DENV=browserstack`).

---

## This repo (Serenity) — recommended path

1. **Unit tests** (always CI): `./gradlew test`
2. **Local E2E** (your laptop with emulator):  
   `./scripts/download-test-apps.sh && appium & ./gradlew e2e aggregate`
3. **Docker Appium** (no local Appium install; still needs a device/emulator endpoint or emulator image):  
   `docker compose up -d appium` then `./gradlew e2e`
4. **BrowserStack**: upload TheApp.apk → set secrets → `./gradlew e2e -DENV=browserstack`
5. **Personal farm (differential vs other repos)**: run Appium nodes per device + Selenium/Appium Grid or [Device Farmer](https://devicefarmer.github.io/) / OpenSTF; Serenity points `appium.hub` at the grid. Documented under `docker-compose.yml`.

Kubernetes note: K8s is excellent to **orchestrate Appium nodes + device workers**, but phones still must be physical USB/network attached or cloud-backed. Emulators in K8s are possible (Android) but heavy; treat as advanced personal-farm demo, not a replacement for BrowserStack/AWS for real-device coverage.
