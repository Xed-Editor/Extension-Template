package com.rk.demo

import androidx.annotation.Keep
import com.rk.extension.ExtensionAPI
import com.rk.extension.ExtensionContext
import com.rk.utils.toast

@Keep
@Suppress("unused")
class Main(context: ExtensionContext) : ExtensionAPI(context) {
    override fun onLoad() {
        // Called only once when extension is loaded
        // NOTE: Might get called even before the main activity or any UI context is available

        val res = context.resources
        val text = res.getString(R.string.hello_world)
        toast(text)

        // The extension will stay in memory until the app is killed by system or force stopped
        // For more information, view the official documentation: https://xed-editor.github.io/Xed-Docs/docs/extensions/
    }

    override fun onDispose() {
        // Any running tasks by your extension should be stopped after this function call
        // NOTE: Make sure to also unregister all commands, language servers, etc.
    }
}