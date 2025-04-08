plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.rk.xed_editor_plugin_demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rk.xed_editor_plugin_demo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            //proguard must be disabled
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        //should match with xed-editor
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        //should match with xed-editor
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}


//always try to match the versions of library to the versions used in xed-editor
dependencies {
    //very important
    compileOnly(files("libs/sdk.jar"))

    //if a library is common in both your plugin and xed-editor then you should use compileOnly() instead of implementation()
    compileOnly(libs.androidx.core.ktx)
    compileOnly(libs.androidx.lifecycle.runtime.ktx)
    compileOnly(libs.androidx.activity.compose)
    compileOnly(platform(libs.androidx.compose.bom))
    compileOnly(libs.androidx.ui)
    compileOnly(libs.androidx.ui.graphics)
    compileOnly(libs.androidx.ui.tooling.preview)
    compileOnly(libs.androidx.material3)
}