# Free device farm (Docker / Kubernetes) — specialty of this repo

This is what makes **demo-serenity-screenplay-mobile** different from the other mobile demos:

| Repo | Devices |
| --- | --- |
| `appium-mobile-automation-framework` | Your Windows **self-hosted** physical/emulator setup |
| `appium-mobile-cloud-automation-framework` | Paid/cloud farms (BrowserStack, AWS) |
| **This repo** | **Free personal farm**: Docker or K8s + virtual Android + Appium + Serenity Screenplay |

## Stack (all free / OSS)

- [budtmo/docker-android](https://github.com/budtmo/docker-android) — Android emulator + Appium inside the container
- Docker Compose (1 node) or Kubernetes (scale nodes)
- TheApp APK mounted at `/farm/apps/TheApp.apk`
- Serenity profile: `src/test/resources/serenity-farm.conf`

## Requirements

- Linux host (or Linux VM) with **KVM** (`/dev/kvm`)
- Docker Engine + Compose plugin
- ~4–6 GB RAM free per emulator
- Optional: `kubectl` for the K8s manifests under `k8s/`

macOS/Windows: run the farm inside a Linux VM with nested virtualization, or use a Linux CI agent with KVM.

## Docker Compose (recommended start)

```bash
./scripts/download-test-apps.sh
./scripts/farm-up.sh                 # waits until Appium /status is OK
./scripts/farm-run-e2e.sh            # Serenity e2e + aggregate report
./scripts/farm-down.sh
```

- Appium: `http://127.0.0.1:4723`
- Watch emulator (noVNC): `http://127.0.0.1:6080`

Second virtual device:

```bash
docker compose --profile multi up -d
# second Appium on :4724 — point another conf hub there if needed
```

## Kubernetes

```bash
./scripts/download-test-apps.sh
sudo mkdir -p /opt/serenity-farm/apps
sudo cp apps/TheApp.apk /opt/serenity-farm/apps/
kubectl apply -f k8s/android-farm.yaml
kubectl -n serenity-farm port-forward svc/android-farm 4723:4723
./gradlew e2e -Dproperties=src/test/resources/serenity-farm.conf
```

Scale: increase `replicas` in the Deployment (each replica needs KVM capacity on a node).

## Why `serenity-farm.conf` uses `/farm/apps/...`

Appium runs **inside** the container. The host folder `./apps` is mounted to `/farm/apps`, so the `app` capability must be the container path, not your laptop path.

## Local laptop without Docker farm

Keep using `serenity.conf` + host Appium on `:4723` (unchanged).

## Cloud CI — ephemeral AVD (GitHub Actions)

GitHub-hosted runners boot a **throwaway Android emulator** with KVM via
[`reactivecircus/android-emulator-runner`](https://github.com/ReactiveCircus/android-emulator-runner),
then Appium + Serenity Cucumber (`scripts/ci-e2e-on-emulator.sh`).

That is the path that publishes **device** reports to Pages. Docker/K8s above is the
self-hosted / laptop farm specialty of this repo.

## Kubernetes ephemeral Job

One-shot device node (auto TTL cleanup):

```bash
kubectl apply -f k8s/android-farm.yaml          # namespace
kubectl apply -f k8s/android-farm-ephemeral-job.yaml
kubectl -n serenity-farm port-forward job/android-farm-e2e 4723:4723
./gradlew e2e -Dproperties=src/test/resources/serenity-farm.conf
```
