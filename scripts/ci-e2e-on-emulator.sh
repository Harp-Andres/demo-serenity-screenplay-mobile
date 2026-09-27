#!/usr/bin/env bash
# Run Serenity Cucumber e2e against an already-booted Android emulator + Appium on :4723.
# Used by GitHub Actions (android-emulator-runner) and local host farms.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

chmod +x gradlew scripts/*.sh
./scripts/download-test-apps.sh
test -f apps/TheApp.apk

echo "Waiting for Appium on :4723..."
for i in $(seq 1 60); do
  if curl -sf http://127.0.0.1:4723/status >/dev/null 2>&1; then
    echo "Appium ready"
    break
  fi
  sleep 2
  if [[ "$i" -eq 60 ]]; then
    echo "ERROR: Appium not ready"
    exit 1
  fi
done

adb devices -l || true

./gradlew --no-daemon e2e aggregate -Dproperties=src/test/resources/serenity.conf "$@"

echo "Serenity: target/site/serenity/index.html"
echo "Cucumber: target/cucumber-reports/cucumber.html"
test -f target/site/serenity/summary.txt && cat target/site/serenity/summary.txt
