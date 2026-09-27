#!/usr/bin/env bash
# Run Serenity E2E against the free Docker farm profile.
# Always finalizes Serenity aggregate; Cucumber HTML/JSON/XML written under target/cucumber-reports.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

./scripts/farm-up.sh
set +e
./gradlew e2e aggregate -Dproperties=src/test/resources/serenity-farm.conf "$@"
STATUS=$?
set -e

echo ""
echo "=== Demo reports ==="
echo "Serenity:  file://${ROOT}/target/site/serenity/index.html"
echo "Cucumber:  file://${ROOT}/target/cucumber-reports/cucumber.html"
echo "Cucumber JSON: ${ROOT}/target/cucumber-reports/cucumber.json"
echo "Serve all: ./scripts/serve-reports.sh"
exit "$STATUS"
