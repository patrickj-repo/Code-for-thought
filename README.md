
# Itinerary APK (Auto-sign workflow)

This project builds a **release-signed APK** and **auto-generates a new keystore** in GitHub Actions.

## Steps
1. Create a **private GitHub repo** and upload this project (push to `main`).
2. Go to **Actions → Android Auto-Sign Release APK → Run workflow**.
3. When the job completes, download **Artifacts**:
   - `app-release.apk` (installable)
   - `itinerary-release.jks` (your keystore)
   - `signing-credentials` (TXT with store/key passwords)
4. Keep the keystore and credentials **safe**; use them for future updates.

> No Secrets needed. The workflow creates a fresh keystore and signs the APK for you.

## Local build (optional)
If you want to build locally instead:
```bash
./gradlew clean :app:assembleRelease
```
Place a `keystore.properties` at project root with:
```
RELEASE_STORE_FILE=app/keystore/itinerary-release.jks
RELEASE_STORE_PASSWORD=your-store-pass
RELEASE_KEY_ALIAS=itinerary
RELEASE_KEY_PASSWORD=your-key-pass
```
And create the JKS via:
```bash
keytool -genkeypair -v -keystore app/keystore/itinerary-release.jks   -storetype JKS -keyalg RSA -keysize 2048 -validity 10000 -alias itinerary
```
