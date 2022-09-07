package com.rivan.androidplaygrounds.android.compiler.java;

import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.tools.r8.CompilationFailedException;
import com.rivan.androidplaygorunds.project.utils.FileUtil;
import com.rivan.androidplaygorunds.project.utils.Indexer;
import com.rivan.androidplaygorunds.project.utils.interfaces.Project;
import com.rivan.androidplaygrounds.android.compiler.interfaces.Compiler;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardLocation;

public class JavacCompiler implements Compiler {

    private final SharedPreferences preferences;

    public JavacCompiler(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @NotNull
    @Override
    public String getCompilerName() {
        return "Javac Compiler";
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void compile(@NotNull Project project) throws Exception {
        final var output = new File(project.getBinDirPath(), "classes");

        final var version = preferences.getString("java_version", "7");

        final var diagnostics = new DiagnosticCollector<JavaFileObject>();

        var lastBuildTime = new Indexer(project.getProjectName(), project.getCacheDirPath()).getLong("lastBuildTime");

        if (!output.exists()) {
            lastBuildTime = 0;
            output.mkdirs();
        }

        final var javaFileObjects = new ArrayList<JavaFileObject>();
        final var javaFiles = getSourceFiles(new File(project.getSrcDirPath()));
        for (var file : javaFiles) {
            if (file.lastModified() > lastBuildTime) {
                var path = file.getAbsolutePath();
                new File(output, path.replaceFirst(project.getSrcDirPath(), "")).delete();
                javaFileObjects.add(
                        new SimpleJavaFileObject(file.toURI(), JavaFileObject.Kind.SOURCE) {
                            @Override
                            public CharSequence getCharContent(boolean ignoreEncodingErrors)
                                    throws IOException {
                                return FileUtil.readFile(file);
                            }
                        });
            }
        }

        if (javaFileObjects.isEmpty()) {
            return;
        }

        final var tool = JavacTool.create();

        final var standardJavaFileManager =
                tool.getStandardFileManager(
                        diagnostics, Locale.getDefault(), Charset.defaultCharset());
        standardJavaFileManager.setLocation(
                StandardLocation.CLASS_OUTPUT, Collections.singletonList(output));
        standardJavaFileManager.setLocation(
                StandardLocation.PLATFORM_CLASS_PATH, getPlatformClasspath());
        standardJavaFileManager.setLocation(StandardLocation.CLASS_PATH, getClasspath(project));
        standardJavaFileManager.setLocation(StandardLocation.SOURCE_PATH, javaFiles);

        final var args = new ArrayList<String>();

        args.add("-proc:none");
        args.add("-Xlint:-options");
        args.add("-source");
        args.add(version);
        args.add("-target");
        args.add(version);
        if (Integer.parseInt(version) >= 9) {
            args.add("--system");
            args.add(FileUtil.getDataDir() + "compiler-modules");
        }

        final var task = (JavacTask) tool.getTask(null, standardJavaFileManager, diagnostics, args, null,
                javaFileObjects);

        if (!task.call()) {
            var errs = new StringBuilder();
            var warns = new StringBuilder();
            for (var diagnostic : diagnostics.getDiagnostics()) {
                var message = new StringBuilder();
                if (diagnostic.getSource() != null) {
                    message.append(diagnostic.getSource().getName());
                    message.append(":");
                    message.append(diagnostic.getLineNumber());
                    message.append(": ");
                }
                message.append(diagnostic.getKind().name());
                message.append(": ");
                message.append(diagnostic.getMessage(Locale.getDefault()));

                switch (diagnostic.getKind()) {
                    case ERROR:
                    case OTHER:
                        errs.append(message.toString());
                        errs.append("\n");
                        break;
                    case NOTE:
                    case WARNING:
                    case MANDATORY_WARNING:
                        warns.append(message.toString());
                        warns.append("\n");
                        break;
                    default:
                        warns.append(message.toString());
                        break;
                }
            }
            var errors = errs.toString();
            var warnings = warns.toString();

            throw new CompilationFailedException(warnings + "\n" + errors);
        }
        new Indexer(project.getProjectName(), project.getCacheDirPath())
                .put("lastBuildTime", System.currentTimeMillis());
    }

    public ArrayList<File> getSourceFiles(File path) {
        var sourceFiles = new ArrayList<File>();
        var files = path.listFiles();
        if (files == null) {
            return new ArrayList<File>();
        }
        for (var file : files) {
            if (file.isFile()) {
                if (file.getName().endsWith(".java")) {
                    sourceFiles.add(file);
                }
            } else {
                sourceFiles.addAll(getSourceFiles(file));
            }
        }
        return sourceFiles;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public ArrayList<File> getClasspath(Project project) {
        var classpath = new ArrayList<File>();
        var classpath1 = preferences.getString("key_classpath", "");

        if (!classpath1.isEmpty()) {
            for (var clas : classpath1.split(":")) {
                classpath.add(new File(clas));
            }
        }
        classpath.add(new File(project.getBinDirPath(), "classes"));
        var libs = new File(project.getLibDirPath()).listFiles();
        if (libs != null) {
            Collections.addAll(classpath, libs);
        }
        return classpath;
    }

    public ArrayList<File> getPlatformClasspath() {
        var classpath = new ArrayList<File>();
        classpath.add(new File(FileUtil.getClasspathDir(), "android.jar"));
        classpath.add(new File(FileUtil.getClasspathDir(), "core-lambda-stubs.jar"));
        classpath.add(new File(FileUtil.getClasspathDir(), "kotlin-stdlib-1.7.20-Beta.jar"));
        return classpath;
    }
}
