#!/usr/bin/env bash
# Run Serenity Cucumber e2e against an already-booted Android emulator + Appium on :4723.
set -eu
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
  if [ "$i" -eq 60 ]; then
    echo "ERROR: Appium not ready"
    exit 1
  fi
done

adb devices -l || true
adb -s emulator-5554 wait-for-device shell getprop sys.boot_completed || true

set +e
# -k kills the whole process group if Gradle outlives the timeout.
timeout -k 20s 15m ./gradlew --no-daemon e2e aggregate \
  -Dproperties=serenity.conf \
  -Dwebdriver.driver=appium \
  -Dappium.hub=http://127.0.0.1:4723/ \
  -Dappium.platformName=Android \
  -Dappium.automationName=UiAutomator2 \
  -Dappium.udid=emulator-5554 \
  -Dappium.deviceName="Android Emulator" \
  -Dappium.process.desired.capabilities=true \
  -Dappium.additional.capabilities=app,appActivity,appPackage,autoGrantPermissions,automationName,deviceName,newCommandTimeout,noReset,udid \
  -Dserenity.restart.browser.for.each=scenario
STATUS=$?
set -e

# Best-effort cleanup so the Actions step can finish.
# crashpad_handler orphans keep reactivecircus/android-emulator-runner hung after emu kill
# (https://github.com/ReactiveCircus/android-emulator-runner/issues/385).
pkill -f 'GradleWorkerMain|GradleDaemon' 2>/dev/null || true
pkill -TERM -f '[c]rashpad_handler' 2>/dev/null || true
sleep 1
pkill -KILL -f '[c]rashpad_handler' 2>/dev/null || true

echo "Serenity: target/site/serenity/index.html"
echo "Cucumber: target/cucumber-reports/cucumber.html"
if [ -f target/site/serenity/summary.txt ]; then
  cat target/site/serenity/summary.txt
fi
exit "$STATUS"
