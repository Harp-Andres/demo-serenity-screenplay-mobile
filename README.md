# demo-serenity-screenplay-mobile

Teaching demo for **Serenity BDD Screenplay + Appium**, with a **free personal device farm** (Docker / Kubernetes + virtual Android).

That farm is the specialty of this repo:

| Sibling | Devices |
| --- | --- |
| [`appium-mobile-automation-framework`](https://github.com/Harp-Andres/appium-mobile-automation-framework) | Mature Windows **self-hosted** runner |
| [`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework) | BrowserStack / AWS |
| **This repo** | **Free farm**: Docker/K8s + emulator + Appium + Serenity |

AUT: **[TheApp](https://github.com/appium-pro/TheApp)** — login `alice` / `mypassword`.

## Free farm (Docker) — recommended

Needs Linux + `/dev/kvm` + Docker.

```bash
./scripts/download-test-apps.sh
./scripts/farm-up.sh          # budtmo emulator + Appium on :4723 (VNC :6080)
./scripts/farm-run-e2e.sh     # Serenity e2e against the farm
./scripts/farm-down.sh
```

Details: [`docs/FREE_DEVICE_FARM.md`](docs/FREE_DEVICE_FARM.md) · K8s manifests: `k8s/android-farm.yaml`

## Other ways to run

```bash
# Unit tests (no device)
./gradlew test

# Laptop Appium already on :4723
./scripts/download-test-apps.sh
./gradlew e2e aggregate

# BrowserStack (optional paid farm)
./gradlew e2e -Dproperties=src/test/resources/serenity-browserstack.conf
```

## Profiles

| Conf | Hub | App path |
| --- | --- | --- |
| `serenity.conf` | host `127.0.0.1:4723` | `apps/TheApp.apk` on host |
| `serenity-farm.conf` | Docker/K8s farm `:4723` | `/farm/apps/TheApp.apk` in container |
| `serenity-browserstack.conf` | BrowserStack cloud | `bs://...` |
