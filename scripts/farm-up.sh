#!/usr/bin/env bash
# Start the free Docker Android farm (emulator + Appium).
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

if [[ ! -e /dev/kvm ]]; then
  echo "ERROR: /dev/kvm not found. Free x86 emulators need KVM (Linux with virtualization)."
  exit 1
fi

if ! command -v docker >/dev/null 2>&1; then
  echo "ERROR: docker not installed"
  exit 1
fi

./scripts/download-test-apps.sh
if [[ ! -f apps/TheApp.apk ]]; then
  echo "ERROR: apps/TheApp.apk missing after download"
  exit 1
fi

DOCKER=(docker)
if ! docker info >/dev/null 2>&1; then
  if sudo docker info >/dev/null 2>&1; then
    DOCKER=(sudo docker)
  else
    echo "ERROR: cannot talk to Docker daemon"
    exit 1
  fi
fi

echo "Starting free farm (budtmo/docker-android + Appium)..."
"${DOCKER[@]}" compose up -d android-farm

echo "Waiting for Appium /status on :4723 (emulator boot can take several minutes)..."
for i in $(seq 1 80); do
  if curl -sf http://127.0.0.1:4723/status >/dev/null 2>&1; then
    echo "Farm ready: Appium http://127.0.0.1:4723  |  VNC http://127.0.0.1:6080"
    curl -s http://127.0.0.1:4723/status | head -c 400; echo
    exit 0
  fi
  sleep 5
  echo "  ... still booting ($((i * 5))s)"
done

echo "ERROR: Appium did not become ready. Check: ${DOCKER[*]} compose logs android-farm"
exit 1
