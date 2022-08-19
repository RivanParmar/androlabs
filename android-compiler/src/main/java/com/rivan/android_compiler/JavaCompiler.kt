package com.rivan.android_compiler

import android.content.SharedPreferences
import org.eclipse.jdt.internal.compiler.batch.Main
import java.io.File
import java.io.OutputStream
import java.io.PrintWriter

class JavaCompiler() {

    private val errs = StringBuilder()

    fun getTaskName(): String = "Java Compiler"

    @Throws(Exception::class)
    fun compile() {

        val writer =
            PrintWriter(
                object : OutputStream() {
                    override fun write(p1: Int) {
                        errs.append(p1.toChar())
                    }
                })

        val main = Main(writer, writer, false, null, null)

        val args = arrayListOf<String>(
            "-g",
            "get java version",
            "-d",
            "get output dir",
            "-cp",
            "get classpath",
            "-proc:none",
            "-sourcepath",
            " ",
            "get java dir"
        )

        main.compile(args.toTypedArray())

        if (main.globalErrorsCount > 0) {
            throw CompilationFailedException(errs.toString())
        }
    }
}