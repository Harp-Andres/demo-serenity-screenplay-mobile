#!/usr/bin/env bash
# Run Serenity E2E against the free Docker farm profile.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

./scripts/farm-up.sh
./gradlew e2e aggregate -Dproperties=src/test/resources/serenity-farm.conf "$@"
echo "Serenity report: file://${ROOT}/target/site/serenity/index.html"
