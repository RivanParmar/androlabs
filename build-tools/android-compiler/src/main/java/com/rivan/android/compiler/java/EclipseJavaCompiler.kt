package com.rivan.androidplaygrounds.android.compiler.java;

import android.content.SharedPreferences
import com.android.tools.r8.CompilationFailedException
import com.rivan.androidplaygorunds.project.utils.FileUtil
import com.rivan.androidplaygorunds.project.utils.interfaces.Project
import com.rivan.androidplaygrounds.android.compiler.interfaces.Compiler
import org.eclipse.jdt.internal.compiler.batch.Main
import java.io.File
import java.io.OutputStream
import java.io.PrintWriter

class EclipseJavaCompiler(preferences: SharedPreferences) : Compiler {

    private val preferences: SharedPreferences
    private val errors = StringBuilder()

    override fun getCompilerName(): String = "Eclipse Java Compiler"

    init {
        this.preferences = preferences
    }

    override fun compile(project: Project) {
        val writer = PrintWriter(object : OutputStream() {
            override fun write(p0: Int) {
                errors.append(p0.toChar())
            }
        })

        val main = Main(writer, writer, false, null, null)

        val output = File(project.getBinDirPath(), "classes")

        val classPath = StringBuilder()
        classPath.append(FileUtil.getClasspathDir() + "android.jar")
        classPath.append(File.pathSeparator)
        classPath.append(FileUtil.getClasspathDir() + "core-lambda-stubs.jar")
        classPath.append(File.pathSeparator)
        classPath.append(FileUtil.getClasspathDir() + "kotlin-stdlib-1.7.20-Beta.jar")
        classPath.append(File.pathSeparator)
        classPath.append(output)

        val prefsClassPath = preferences.getString("classPath", "")
        if (prefsClassPath!!.isNotEmpty() && classPath.isNotEmpty()) {
            classPath.append(File.pathSeparator)
            classPath.append(prefsClassPath)
        }

        val libs = File(project.getLibDirPath()).listFiles()
        if (libs != null) {
            for (lib in libs) {
                classPath.append(File.pathSeparator)
                classPath.append(lib.absolutePath)
            }
        }

        val args = arrayListOf(
            "-g", "-" + preferences.getString("java_version", "7"), "-d", output.absolutePath, "-cp",
            classPath.toString(), "-proc:none", "-sourcePath", " ", project.getSrcDirPath()
        )

        main.compile(args.toTypedArray())

        if (main.globalErrorsCount > 0 || !output.exists()) {
            throw CompilationFailedException(errors.toString())
        }
    }
}
