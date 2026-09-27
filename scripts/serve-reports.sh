#!/usr/bin/env bash
# Serve Serenity / Cucumber HTML locally (avoids blank pages when opening file:// on Windows).
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
PORT="${1:-8088}"

SERENITY="$ROOT/target/site/serenity"
CUCUMBER="$ROOT/target/cucumber-reports"

if [[ ! -f "$SERENITY/index.html" && ! -f "$CUCUMBER/cucumber.html" ]]; then
  echo "No reports found. Run first:"
  echo "  ./gradlew clean test aggregate"
  echo "  # or with farm:"
  echo "  ./scripts/farm-run-e2e.sh"
  exit 1
fi

echo "Serenity:  http://127.0.0.1:${PORT}/serenity/index.html"
if [[ -f "$CUCUMBER/cucumber.html" ]]; then
  echo "Cucumber:  http://127.0.0.1:${PORT}/cucumber/cucumber.html"
fi
echo "(Ctrl+C to stop)"

# Publish both trees under one HTTP root.
STAGE="$(mktemp -d)"
trap 'rm -rf "$STAGE"' EXIT
mkdir -p "$STAGE/serenity" "$STAGE/cucumber"
[[ -d "$SERENITY" ]] && cp -a "$SERENITY/." "$STAGE/serenity/" || true
[[ -d "$CUCUMBER" ]] && cp -a "$CUCUMBER/." "$STAGE/cucumber/" || true

cd "$STAGE"
python3 -m http.server "$PORT"
