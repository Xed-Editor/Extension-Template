plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.rk.demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rk.demo"
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
    compileOnly(libs.appcompat)
    compileOnly(libs.material)
    compileOnly(libs.constraintlayout)
    compileOnly(libs.navigation.fragment)
    compileOnly(libs.navigation.ui)
    compileOnly(libs.asynclayoutinflater)
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
    compileOnly(libs.media3.exoplayer)
    compileOnly(libs.media3.exoplayer.dash)
    compileOnly(libs.media3.ui)
    compileOnly(libs.browser)
    compileOnly(libs.quickjs.android)
    compileOnly(libs.anrwatchdog)
    compileOnly(libs.word.wrap)
}