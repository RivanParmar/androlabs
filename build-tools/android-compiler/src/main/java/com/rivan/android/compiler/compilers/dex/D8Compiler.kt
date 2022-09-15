package com.rivan.androidplaygrounds.android.compiler.compilers.dex

import android.os.Build
import androidx.annotation.RequiresApi
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import com.rivan.androidplaygorunds.project.utils.FileUtil
import com.rivan.androidplaygorunds.project.utils.interfaces.Project
import com.rivan.androidplaygrounds.android.compiler.interfaces.Compiler
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

@RequiresApi(Build.VERSION_CODES.O)
class D8Compiler : Compiler {

    companion object {
        @JvmStatic
        fun compileJar(jarFile: String) {
            val outputDex = jarFile.replaceAfterLast('.', "dex")
            D8.run(
                D8Command.builder()
                    .setMinApiLevel(24)
                    .addLibraryFiles(Paths.get(FileUtil.getClasspathDir(), "android.jar"))
                    .addProgramFiles(Paths.get(jarFile))
                    .setOutput(Paths.get(outputDex), OutputMode.DexIndexed)
                    .build()
            )
        }
    }

    override fun getCompilerName(): String = "D8 Compiler"

    override fun compile(project: Project) {
        D8.run(
            D8Command.builder()
                .setMinApiLevel(26)
                .addLibraryFiles(Paths.get(FileUtil.getClasspathDir(), "android.jar"))
                .addProgramFiles(
                    getClassFiles(File(project.getBinDirPath(), "classes"))
                )
                .setOutput(Paths.get(project.getBinDirPath()), OutputMode.DexIndexed)
                .build()
        )
    }

    private fun getClassFiles(root: File): ArrayList<Path> {
        val paths = arrayListOf<Path>()

        root.walk().forEach {
            if (it.isFile() && it.extension == "class") {
                paths.add(it.toPath())
            }
        }
        return paths
    }
}