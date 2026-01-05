# Xed-Editor Extension Template

This repository is a starting point for building extensions for **Xed-Editor (Karbon)**.
It includes a ready-to-use template, build scripts, and a simple folder structure so you can focus on writing your extension instead of setting up the environment.

---

## 🚀 Getting Started

### 1. Clone the Template

```bash
git clone https://github.com/Xed-Editor/Extension-Template
cd Extension-Template
```

---

### 2. Configure Your Extension

Before building, update the following in `manifest.json`:

* `name` – your extension’s name
* `version` – version of your extension
* `authors` – Developers of the extension

>[!WARNING]
If you rename the main class or move it to another package/folder, **you must update the `mainClass` field in `manifest.json`**, or the extension will not load.

---

### 3. Build the Extension

To build the extension in **debug mode**, run:

```bash
./compileDebug
```

(You can create your own release script later if needed.)

---

### 4. Find the Output

After a successful build, your extension package will be created here:

```
output/YourExtensionName.zip
```

This ZIP file is what you load into **Xed-Editor** as a extension.

---

## 📖 Documentation

For more detailed information about creating and managing extensions for Xed-Editor, see the official documentation:

[Xed-Editor Extension Docs](https://xed-editor.github.io/Xed-Docs/docs/extensions/)

This includes guides, API references, examples, and best practices for developing your extensions.
