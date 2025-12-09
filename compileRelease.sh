set -e

rm -rf output

./gradlew assembleRelease
./gradlew :app:createFinalZip