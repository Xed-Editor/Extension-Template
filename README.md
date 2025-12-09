# Xed-Editor Plugin Template

This repository is a starting point for building plugins for **Xed-Editor (Karbon)**.
It includes a ready-to-use template, build scripts, and a simple folder structure so you can focus on writing your plugin instead of setting up the environment.

---

## 🚀 Getting Started

### 1. Clone the Template

```bash
git clone https://github.com/Xed-Editor/pluginTemplate
cd pluginTemplate
```

---

### 2. Configure Your Plugin

Before building, update the following in `manifest.json`:

* `name` – your plugin’s name
* `version` – version of your plugin

>[!WARNING]
If you rename the main class or move it to another package/folder, **you must update the `mainClass` field in `manifest.json`**, or the plugin will not load.

---

### 3. Build the Plugin

To build the plugin in **debug mode**, run:

```bash
sh compileDebug.sh
```

(You can create your own release script later if needed.)

---

### 4. Find the Output

After a successful build, your plugin package will be created here:

```
output/YourPluginName.zip
```

This ZIP file is what you load into **Xed-Editor** as a plugin.
