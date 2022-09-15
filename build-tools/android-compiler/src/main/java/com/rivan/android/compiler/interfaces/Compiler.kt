package com.rivan.androidplaygrounds.android.compiler.interfaces

import com.rivan.androidplaygorunds.project.utils.interfaces.Project
import kotlin.jvm.Throws

interface Compiler {

    fun getCompilerName(): String

    @Throws(Exception::class)
    fun compile(project: Project)
}