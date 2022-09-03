package com.rivan.androidplaygorunds.project.utils.interfaces;

import java.io.File
import java.io.IOException
import kotlin.jvm.Throws

interface Project {

    @Throws(IOException::class)
    fun newProject(projectName: String): Project

    fun init()

    fun delete()

    fun getRootFile(): File

    fun getRootDirPath(): String

    fun getProjectName(): String

    fun getProjectDirPath(): String

    fun getSrcDirPath(): String

    fun getBinDirPath(): String

    fun getLibDirPath(): String

    fun getBuildDirPath(): String

    fun getCacheDirPath(): String
}
