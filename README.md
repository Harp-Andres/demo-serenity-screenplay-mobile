# demo-serenity-screenplay-mobile

Teaching demo for **Serenity BDD Screenplay + Appium**, with a **free personal device farm** (Docker / Kubernetes + virtual Android).

That farm is the specialty of this repo:

| Sibling | Devices |
| --- | --- |
| [`appium-mobile-automation-framework`](https://github.com/Harp-Andres/appium-mobile-automation-framework) | Mature Windows **self-hosted** runner |
| [`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework) | BrowserStack / AWS |
| **This repo** | **Free farm**: Docker/K8s + emulator + Appium + Serenity |

AUT: **[TheApp](https://github.com/appium-pro/TheApp)** — login `alice` / `mypassword`.

## CI (GitHub Actions)

On every push/PR (same shape as [mi-portafolio](https://github.com/Harp-Andres/mi-portafolio)):

| Job | What it does |
| --- | --- |
| `lint` | Compiles main + test sources (Java 21) |
| `test-unit` | Domain unit tests + Serenity aggregate (**serenity-report-unit**) |
| `test-e2e` | **Ephemeral cloud device**: KVM Android Emulator (API 29) + Appium + Cucumber on TheApp → **serenity-report-e2e** + **cucumber-reports** |
| `build` | Assembles compiled classes |
| `notify` | Gates green/red (lint + unit + **e2e** + build) |
| `publish-report` | On `main`: publishes **e2e** Serenity HTML to GitHub Pages (devices / screenshots) |

Local Docker/K8s farm (budtmo) remains available: `./scripts/farm-run-e2e.sh` · ephemeral Job: `k8s/android-farm-ephemeral-job.yaml`.

### Cómo ver el reporte (estilo Azure: en el run + en la web)

| Dónde | Qué ves | Cuándo |
| --- | --- | --- |
| **Mismo Action → Summary** | Tabla Serenity (scenarios / passed / failed) en el Job Summary del run | Cada push/PR |
| **Mismo Action → Checks** | Resultados JUnit anotados (`Unit tests (JUnit)`), similar a la pestaña Tests de Azure | Cada push/PR |
| **Mismo Action → Artifacts** | ZIP HTML completo (`serenity-report-unit`) | Cada push/PR (éxito o fallo) |
| **GitHub Pages (web)** | HTML Serenity navegable en el browser | Tras CI verde en `main` |

**Live:** https://harp-andres.github.io/demo-serenity-screenplay-mobile/

En PRs: Summary + Checks + Artifacts. Pages se actualiza al mergear a `main`.

`main` is protected like mi-portafolio: required status checks (strict), dismiss stale reviews, conversation resolution, enforce admins, no force-push/delete.

## Demo reports (Serenity + Cucumber)

| Report | When | Path / artifact |
| --- | --- | --- |
| Serenity HTML | unit or e2e | `target/site/serenity/index.html` → artifacts `serenity-report-unit` / `serenity-report-e2e` |
| Cucumber HTML / JSON / JUnit XML | e2e only | `target/cucumber-reports/` → artifact `cucumber-reports` |
| Gradle HTML + JUnit XML | unit | `build/reports/tests/`, `build/test-results/` |

**Devices / Environment** (how many devices, capabilities) appear only after **e2e** against Appium (farm or laptop). The unit artifact is Serenity-backed domain tests — it will not list emulators.

Open reports over HTTP (avoids blank `file://` pages on Windows):

```bash
./scripts/farm-run-e2e.sh   # or: ./gradlew e2e aggregate
./scripts/serve-reports.sh  # http://127.0.0.1:8088/serenity/ and .../cucumber/
```

If you already unzipped a CI artifact on Windows:

```powershell
cd E:\UnidadPrincipal\Descargas\serenity-report
python -m http.server 8088
# then open http://127.0.0.1:8088/index.html
```

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
