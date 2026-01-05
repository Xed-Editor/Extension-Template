package com.rk.demo

import android.app.Activity
import android.os.Bundle
import androidx.annotation.Keep
import com.rk.extension.Extension
import com.rk.extension.ExtensionAPI

@Keep
class Main : ExtensionAPI() {
    override fun onPluginLoaded(extension: Extension) {
        // Called only once when extension is loaded
        // NOTE: Might get called even before the main activity or any UI context is available

        // The extension will stay in memory until the app is killed by system or force stopped
        // For more information, view the official documentation: https://xed-editor.github.io/Xed-Docs/docs/extensions/
    }

    override fun onUninstalled(extension: Extension) {
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