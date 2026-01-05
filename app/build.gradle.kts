import com.google.gson.Gson
import java.net.URL
import com.google.gson.JsonObject

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.rk.demo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.rk.demo"
        minSdk = 26

        //noinspection ExpiredTargetSdkVersion
        targetSdk = 28
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
    kotlin { jvmToolchain(21) }
    buildFeatures {
        compose = true
    }
}


// Always try to match the versions of library to the versions used in Xed-Editor
dependencies {
    // Xed-Editor extension SDK, required to interact with the application, do NOT remove
    compileOnly(files("libs/sdk.jar"))

    // If a library is used in Xed-Editor and your plugin is common. then you should use compileOnly. Otherwise, it slows down the app.
    compileOnly(libs.appcompat)
    compileOnly(libs.material)
    compileOnly(libs.constraintlayout)
    compileOnly(libs.navigation.fragment)
    compileOnly(libs.navigation.ui)
    compileOnly(libs.navigation.fragment.ktx)
    compileOnly(libs.navigation.ui.ktx)
    compileOnly(libs.activity)
    compileOnly(libs.lifecycle.viewmodel.ktx)
    compileOnly(libs.lifecycle.runtime.ktx)
    compileOnly(libs.activity.compose)
    compileOnly(platform(libs.compose.bom))
    compileOnly(libs.ui)
    compileOnly(libs.ui.graphics)
    compileOnly(libs.material3)
    compileOnly(libs.navigation.compose)
    compileOnly(libs.utilcode)
    compileOnly(libs.coil.compose)
    compileOnly(libs.gson)
    compileOnly(libs.commons.net)
    compileOnly(libs.okhttp)
    compileOnly(libs.material.motion.compose.core)
    compileOnly(libs.nanohttpd)
    compileOnly(libs.photoview)
    compileOnly(libs.glide)
    compileOnly(libs.media3.ui)
    compileOnly(libs.browser)
    compileOnly(libs.quickjs.android)
    compileOnly(libs.anrwatchdog)
    compileOnly(libs.lsp4j)
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.androidx.documentfile)
    compileOnly(libs.compose.dnd)
    compileOnly(libs.androidx.material.icons.core)
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
            val json = URL(API_URL).readText()
            val releaseMap = Gson().fromJson(json, Map::class.java) as Map<String, Any>
            remoteUpdatedAt = releaseMap["updated_at"] as String
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
            URL(DOWNLOAD_URL).openStream().use { inputStream ->
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
    delete( layout.buildDirectory.dir("outputs/apk"))
}


tasks.named("preBuild").configure {
    dependsOn("cleanApkOutputs")
    dependsOn("downloadLatestJar")
}


// --------------- generate the final zip file -----------------

tasks.register<Zip>("createFinalZip") {
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

    if (apkFiles.size > 1){
        throw GradleException("multiple apk files detected, this build system canot handle multiple apk files")
    }

    if (apkFiles.isEmpty()){
        throw GradleException("No apk files found, run ./gradlew assembleRelease first")
    }

    val apk = apkFiles.first()
    val manifest = File(rootDir,"manifest.json")

    val pluginName: String by lazy {
        val text = manifest.readText()
        val json = Gson().fromJson(text, JsonObject::class.java)
        json.get("name").asString
    }

    archiveFileName.set("$pluginName.zip")

    from(apk) {
        into("")
    }

    from(manifest) {
        into("")
    }

    destinationDirectory.set(File(rootDir,"output"))
}

