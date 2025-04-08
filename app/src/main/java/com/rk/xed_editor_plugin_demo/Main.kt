package com.rk.xed_editor_plugin_demo

import android.graphics.drawable.PaintDrawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import com.rk.components.compose.preferences.base.PreferenceGroup
import com.rk.components.compose.preferences.base.PreferenceLayout
import com.rk.controlpanel.ControlItem
import com.rk.extension.ExtensionAPI
import com.rk.extension.Hooks
import com.rk.extension.SettingsScreen
import com.rk.xededitor.ui.components.SettingsToggle
import com.rk.xededitor.ui.screens.settings.feature_toggles.Feature

class Main : ExtensionAPI() {
    override fun onPluginLoaded() {
        //add git setting screen
        Hooks.Settings.screens["git"] = SettingsScreen(
            label = "Git",
            description = "Git client settings",
            route = "git",
            icon = {
                Icon(imageVector = Icons.Default.Build,contentDescription = null)
            },
            content = {
                PreferenceLayout(label = "Git") {
                    PreferenceGroup {
                        SettingsToggle(label = "Example", default = false, showSwitch = true)
                        SettingsToggle(label = "Example", default = false, showSwitch = true)
                        SettingsToggle(label = "Example", default = false, showSwitch = true)
                        SettingsToggle(label = "Example", default = false, showSwitch = true)
                    }
                }
            }
        )

        //add items in control panel
        Hooks.ControlPanel.controlItems["pull"] = ControlItem(label = "Pull", description = "GitClient", hideControlPanelOnClick = true, sideEffect = {

        })

        Hooks.ControlPanel.controlItems["push"] = ControlItem(label = "Push", description = "GitClient", hideControlPanelOnClick = true, sideEffect = {

        })

        Hooks.ControlPanel.controlItems["commit"] = ControlItem(label = "Commit", description = "GitClient",  hideControlPanelOnClick = true, sideEffect = {

        })

        Hooks.ControlPanel.controlItems["commit + push"] = ControlItem(label = "Commit + Push", description = "GitClient",  hideControlPanelOnClick = true, sideEffect = {

        })

        Hooks.ControlPanel.controlItems["log"] = ControlItem(label = "Logs", description = "GitClient",  hideControlPanelOnClick = true, sideEffect = {

        })


    }

    override fun onAppCreated() {
       
    }

    override fun onAppLaunched() {
       
    }

    override fun onAppPaused() {
       
    }

    override fun onAppResumed() {
       
    }

    override fun onAppDestroyed() {
       
    }

    override fun onLowMemory() {
       
    }
}