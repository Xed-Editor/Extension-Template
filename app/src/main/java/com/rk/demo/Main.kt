package com.rk.demo

import android.util.Log
import com.rk.extension.Extension
import com.rk.extension.ExtensionAPI

class Main : ExtensionAPI() {
    override fun onPluginLoaded(extension: Extension) {
        Log.d("Main", "onPluginLoaded")
    }

    override fun onMainActivityCreated() {
        Log.d("Main", "onMainActivityCreated")
    }

    override fun onMainActivityPaused() {
        Log.d("Main", "onMainActivityPaused")
    }

    override fun onMainActivityResumed() {
        Log.d("Main", "onMainActivityResumed")
    }

    override fun onMainActivityDestroyed() {
        Log.d("Main", "onMainActivityDestroyed")
    }

    override fun onLowMemory() {
        Log.d("Main", "onLowMemory")
    }

}