import com.google.gson.Gson
import com.google.gson.JsonObject
import java.net.URI

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.rk.demo"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.rk.demo"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            // If you plan to enable ProGuard, then make sure to add @Keep on the main class. Otherwise, Xed-Editor won't be able to find it.
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Should match with Xed-Editor
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
}

kotlin { jvmToolchain(21) }

// Always try to match the versions of library to the versions used in Xed-Editor
dependencies {
    // Xed-Editor extension SDK, required to interact with the application, do NOT remove
    compileOnly(files("libs/sdk.jar"))

    // If a library is used in Xed-Editor and your extension is common, then you should use compileOnly. Otherwise, it slows down the app.
    compileOnly(libs.androidx.appcompat)
    compileOnly(libs.material)
    compileOnly(libs.androidx.constraintlayout)
    compileOnly(libs.androidx.navigation.fragment)
    compileOnly(libs.androidx.navigation.ui)
    compileOnly(libs.androidx.navigation.fragment.ktx)
    compileOnly(libs.androidx.navigation.ui.ktx)
    compileOnly(libs.androidx.activity)
    compileOnly(libs.androidx.lifecycle.viewmodel)
    compileOnly(libs.androidx.lifecycle.runtime)
    compileOnly(libs.androidx.activity.compose)
    compileOnly(platform(libs.androidx.compose.bom))
    compileOnly(libs.androidx.compose.ui)
    compileOnly(libs.androidx.compose.ui.graphics)
    compileOnly(libs.androidx.compose.material3)
    compileOnly(libs.androidx.navigation.compose)
    compileOnly(libs.utilcode)
    compileOnly(libs.coil.compose)
    compileOnly(libs.gson)
    compileOnly(libs.commons.net)
    compileOnly(libs.okhttp)
    compileOnly(libs.material.motion.compose)
    compileOnly(libs.nanohttpd)
    compileOnly(libs.photoview)
    compileOnly(libs.glide)
    compileOnly(libs.androidx.browser)
    compileOnly(libs.anrwatchdog)
    compileOnly(libs.lsp4j)
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.androidx.documentfile)
    compileOnly(libs.compose.dnd)
    compileOnly(libs.androidx.compose.material.icons.core)
    compileOnly(libs.pine.core)
    compileOnly(libs.androidx.lifecycle.process)
    compileOnly(libs.androidsvg.aar)
}

//  ---------------- below is the code for automatically updating the sdk.jar --------------------

val GITHUB_OWNER = "Xed-Editor"
val GITHUB_REPO = "Xed-Editor"
val TAG_NAME = "sdk-latest"
val ASSET_NAME = "sdk.jar"

val API_URL = "https://api.github.com/repos/$GITHUB_OWNER/$GITHUB_REPO/releases/tags/$TAG_NAME"
val DOWNLOAD_URL =
    "https://github.com/$GITHUB_OWNER/$GITHUB_REPO/releases/download/$TAG_NAME/$ASSET_NAME"

val timestampFile = project.layout.buildDirectory.file("sdk_updated_at.txt")
val outputFile = project.layout.projectDirectory.file("libs/$ASSET_NAME")

tasks.register<DefaultTask>("downloadLatestJar") {
    outputs.upToDateWhen { false }
    description = "Checks and downloads the latest $ASSET_NAME from GitHub."
    group = "build"

    outputs.file(outputFile)
    outputs.file(timestampFile)

    doLast {
        outputFile.asFile.parentFile.mkdirs()
        timestampFile.get().asFile.parentFile.mkdirs()

        val remoteUpdatedAt: String
        try {
            val json = URI.create(API_URL).toURL().readText()
            val release = Gson().fromJson(json, JsonObject::class.java)
            remoteUpdatedAt = release.get("updated_at")?.asString
                ?: throw GradleException("GitHub API response does not contain 'updated_at'.")
        } catch (e: Exception) {
            logger.error("Failed to fetch GitHub API at $API_URL", e)
            throw GradleException("Could not check latest release timestamp.", e)
        }

        val storedUpdatedAt = if (timestampFile.get().asFile.exists()) {
            timestampFile.get().asFile.readText().trim()
        } else {
            null
        }

        if (remoteUpdatedAt == storedUpdatedAt) {
            println("✅ $ASSET_NAME is up to date (Timestamp: $remoteUpdatedAt). Skipping download.")
            return@doLast
        }

        println("Release updated ($storedUpdatedAt -> $remoteUpdatedAt). Downloading new JAR...")

        try {
            URI.create(DOWNLOAD_URL).toURL().openStream().use { inputStream ->
                outputFile.asFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            timestampFile.get().asFile.writeText(remoteUpdatedAt)
            println("Successfully downloaded $ASSET_NAME to ${outputFile.asFile.path}")
        } catch (e: Exception) {
            logger.error("Failed to download JAR from $DOWNLOAD_URL", e)
            throw GradleException("Download failed.", e)
        }
    }
}

tasks.register<Delete>("cleanApkOutputs") {
    description = "Clears all generated files and subdirectories from the build/outputs/apk folder."
    group = "cleanup"
    delete(layout.buildDirectory.dir("outputs/apk"))
}

tasks.named("preBuild").configure {
    dependsOn("cleanApkOutputs")
    dependsOn("downloadLatestJar")
}

// --------------- generate the final zip file -----------------

tasks.register<Zip>("packageExtension") {
    outputs.upToDateWhen { false }
    description = "Archives the generated APK files into a single ZIP file."
    group = "build"

    val apkFiles = layout.buildDirectory
        .dir("outputs/apk")
        .get()
        .asFile
        .walk()
        .filter { it.extension == "apk" }
        .toList()

    if (apkFiles.size > 1) {
        throw GradleException("multiple apk files detected, this build system canot handle multiple apk files")
    }

    if (apkFiles.isEmpty()) {
        throw GradleException("No apk files found, run ./gradlew assembleRelease first")
    }

    val apk = apkFiles.first()
    val manifest = File(rootDir, "manifest.json")

    if (!manifest.exists()) {
        throw GradleException("manifest.json not found")
    }

    val manifestJson: JsonObject by lazy {
        val text = manifest.readText()
        Gson().fromJson(text, JsonObject::class.java)
    }

    val extensionName: String by lazy {
        manifestJson.get("name").asString
    }

    val iconFile = File(rootDir, "icon.png")
    val readmeFile = File(rootDir, "README.md")
    val changelogFile = File(rootDir, "CHANGELOG.md")
    val filesDir = File(rootDir, "files")

    if (!iconFile.exists()) {
        logger.warn("WARNING: No icon.png file found. It is recommended to include an icon so your extension has a recognizable visual identity.")
    }

    if (!readmeFile.exists()) {
        logger.warn("WARNING: No README.md file found. It is recommended to include one to document your extension, its features, and usage instructions.")
    }

    if (!changelogFile.exists()) {
        logger.warn("WARNING: No CHANGELOG.md file found. It is recommended to include one to help users track changes between releases.")
    }

    archiveFileName.set("$extensionName.zip")

    from(apk) { into("") }
    from(manifest) { into("") }
    from(iconFile) { into("") }
    from(readmeFile) { into("") }
    from(changelogFile) { into("") }
    from(filesDir) { into("files") }

    destinationDirectory.set(File(rootDir, "output"))
}
