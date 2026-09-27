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
adb -s emulator-5554 wait-for-device shell getprop sys.boot_completed || true

# Bound the Gradle/Appium run so the CI job cannot hang until the 60m timeout.
set +e
timeout 25m ./gradlew --no-daemon e2e aggregate \
  -Dproperties=src/test/resources/serenity.conf \
  -Dwebdriver.driver=appium \
  -Dappium.hub=http://127.0.0.1:4723/ \
  -Dappium.platformName=Android \
  -Dappium.automationName=UiAutomator2 \
  -Dappium.udid=emulator-5554 \
  -Dappium.deviceName="Android Emulator" \
  "$@"
STATUS=$?
set -e

echo "Serenity: target/site/serenity/index.html"
echo "Cucumber: target/cucumber-reports/cucumber.html"
if [ -f target/site/serenity/summary.txt ]; then
  cat target/site/serenity/summary.txt
fi
exit "$STATUS"
