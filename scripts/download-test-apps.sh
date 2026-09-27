#!/usr/bin/env bash
# Downloads free demo apps used by this project (and sibling mobile demos).
# Primary: TheApp (Appium Pro) — Android APK + iOS Simulator .app.zip
# Optional: Sauce Labs Sample App — real-device iOS .ipa for BrowserStack/AWS
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
OUT="${ROOT}/apps"
mkdir -p "$OUT"

THEAPP_VERSION="${THEAPP_VERSION:-v1.12.0}"
SAUCE_VERSION="${SAUCE_VERSION:-2.7.1}"

download() {
  local url="$1"
  local dest="$2"
  if [[ -f "$dest" ]]; then
    echo "OK (cached): $dest"
    return
  fi
  echo "Downloading $(basename "$dest")..."
  curl -fL --retry 3 -o "$dest.partial" "$url"
  mv "$dest.partial" "$dest"
  echo "OK: $dest"
}

download \
  "https://github.com/appium-pro/TheApp/releases/download/${THEAPP_VERSION}/TheApp.apk" \
  "${OUT}/TheApp.apk"

download \
  "https://github.com/appium-pro/TheApp/releases/download/${THEAPP_VERSION}/TheApp.app.zip" \
  "${OUT}/TheApp.app.zip"

if [[ "${WITH_SAUCE_IOS_IPA:-0}" == "1" ]]; then
  download \
    "https://github.com/saucelabs/sample-app-mobile/releases/download/${SAUCE_VERSION}/iOS.RealDevice.SauceLabs.Mobile.Sample.app.${SAUCE_VERSION}.ipa" \
    "${OUT}/SauceLabs-Sample.ipa"
fi

echo
echo "Apps ready under ${OUT}"
echo "  Android (local/BrowserStack/AWS): TheApp.apk"
echo "  iOS Simulator (local/Docker):     TheApp.app.zip"
echo "  iOS Real Device (optional):       set WITH_SAUCE_IOS_IPA=1"
