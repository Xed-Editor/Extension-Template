package com.rk.demo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import com.rk.activities.main.MainActivity
import com.rk.extension.Extension
import com.rk.extension.ExtensionAPI
import com.rk.utils.toast

@Keep
class Main : ExtensionAPI() {

    override fun onPluginLoaded(extension: Extension) {
        //called when plugin is loaded
        //note: might get called even before the main activity or any ui context is available
    }

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) {
        if (activity is MainActivity){
            toast("hello from demo plugin")
        }

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