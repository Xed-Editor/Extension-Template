package com.rk.demo

import android.app.Activity
import android.os.Bundle
import androidx.annotation.Keep
import com.rk.extension.ExtensionAPI
import com.rk.extension.ExtensionContext
import com.rk.utils.toast

@Keep
@Suppress("unused")
class Main(context: ExtensionContext) : ExtensionAPI(context) {
    override fun onExtensionLoaded() {
        // Called only once when extension is loaded
        // NOTE: Might get called even before the main activity or any UI context is available

        val res = context.resources
        val text = res.getString(R.string.hello_world)
        toast(text)

        // The extension will stay in memory until the app is killed by system or force stopped
        // For more information, view the official documentation: https://xed-editor.github.io/Xed-Docs/docs/extensions/
    }

    override fun onInstalled() {
        // Called when this extension gets initially installed
    }

    override fun onUpdated() {
        // Called when this extension gets updated by user
    }

    override fun onUninstalled() {
        // Called when this extension gets updated or uninstalled by user, any running tasks by your extension should be stopped after this function call
    }

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) {
        // Will most likely never be called, as activities are usually created before the extensions are loaded
    }

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}
}