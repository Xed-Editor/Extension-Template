package com.rk.demo

import android.app.Activity
import android.os.Bundle
import androidx.annotation.Keep
import com.rk.extension.Extension
import com.rk.extension.ExtensionAPI

@Keep
class Main : ExtensionAPI() {

    override fun onPluginLoaded(extension: Extension) {
        //called only once when plugin is loaded
        //note: might get called even before the main activity or any ui context is available

        //the plugin will stay in memory until the app is killed by system or force stopped
    }

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) {
       //most likely never get called because usually activity get created even before the plugins get loaded
    }

    override fun onActivityDestroyed(activity: Activity) {
        
    }

    override fun onActivityPaused(activity: Activity) {
        
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle
    ) {
        
    }

    override fun onActivityStarted(activity: Activity) {
        
    }

    override fun onActivityStopped(activity: Activity) {
        
    }


}