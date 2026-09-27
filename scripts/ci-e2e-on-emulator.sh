#!/usr/bin/env bash
# Run Serenity Cucumber e2e against an already-booted Android emulator + Appium on :4723.
# Owns Appium start/stop when START_APPIUM=1 (CI). Keep this as a single bash entrypoint:
# android-emulator-runner executes its `script` line-by-line under /bin/sh (no functions).
set -eu
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

APPIUM_PID=""
START_APPIUM="${START_APPIUM:-0}"

cleanup() {
  if [[ -n "${APPIUM_PID}" ]]; then
    kill "${APPIUM_PID}" 2>/dev/null || true
    sleep 1
    kill -9 "${APPIUM_PID}" 2>/dev/null || true
  fi
  pkill -f 'GradleWorkerMain|GradleDaemon' 2>/dev/null || true
  # crashpad_handler orphans keep reactivecircus/android-emulator-runner hung after emu kill
  # (https://github.com/ReactiveCircus/android-emulator-runner/issues/385).
  timeout 5 adb -s emulator-5554 emu kill >/dev/null 2>&1 || true
  pkill -TERM -f '[c]rashpad_handler' 2>/dev/null || true
  sleep 2
  pkill -KILL -f '[c]rashpad_handler' 2>/dev/null || true
  pkill -KILL -f '[q]emu-system' 2>/dev/null || true
  pkill -KILL -f '[e]mulator -avd' 2>/dev/null || true
}
trap cleanup EXIT

chmod +x gradlew scripts/*.sh
./scripts/download-test-apps.sh
test -f apps/TheApp.apk

if [[ "${START_APPIUM}" == "1" ]]; then
  appium --address 127.0.0.1 --port 4723 --base-path / --log appium-ci.log &
  APPIUM_PID=$!
fi

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

echo "Serenity: target/site/serenity/index.html"
echo "Cucumber: target/cucumber-reports/cucumber.html"
if [ -f target/site/serenity/summary.txt ]; then
  cat target/site/serenity/summary.txt
fi
exit "$STATUS"
