package com.rivan.androidplaygorunds.project.utils

import dalvik.system.BaseDexClassLoader

@Suppress("NewApi")
class MultipleDexClassLoader(private val librarySearchPath: String? = null) {

    val loader by lazy {
        BaseDexClassLoader("", null, librarySearchPath, javaClass.classLoader)
    }

    // We're calling an internal API for adding the dex path, might not be good
    private val addDexPath = BaseDexClassLoader::class.java
        .getMethod("addDexPath", String::class.java)

    fun loadDex(dexPath: String): BaseDexClassLoader {
        addDexPath.invoke(loader, dexPath)

        return loader
    }
}