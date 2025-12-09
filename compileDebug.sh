set -e

rm -rf output

./gradlew assembleDebug
./gradlew :app:createFinalZip