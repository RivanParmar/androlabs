package com.rivan.androidplaygrounds.android.compiler.jar;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.rivan.androidplaygorunds.project.utils.interfaces.Project;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
public class JarTask {

    public String getTaskName() {
        return "Jar Task";
    }

    public void doTask(Project project) throws Exception {
        // input file
        var classesFolder = new File(project.getBinDirPath() + "classes");

        // Open archive file
        var stream = new FileOutputStream(project.getBinDirPath() + "classes.jar");

        var manifest = buildManifest();

        // Create the jar file
        var output = new JarOutputStream(stream, manifest);

        // Add the files..
        if (classesFolder.listFiles() != null) {
            for (var clazz : classesFolder.listFiles()) {
                add(classesFolder.getPath(), clazz, output);
            }
        }

        output.close();
        stream.close();
    }

    private Manifest buildManifest() {
        var manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        return manifest;
    }

    private void add(String parentPath, File source, JarOutputStream target) throws IOException {
        var name = source.getPath().substring(parentPath.length() + 1);

        BufferedInputStream in = null;
        try {
            if (source.isDirectory()) {
                if (!name.isEmpty()) {
                    if (!name.endsWith("/")) name += "/";

                    // Add the Entry
                    var entry = new JarEntry(name);
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }

                for (var nestedFile : source.listFiles()) {
                    add(parentPath, nestedFile, target);
                }
                return;
            }

            var entry = new JarEntry(name);
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));
            var buffer = new byte[1024];
            while (true) {
                var count = in.read(buffer);
                if (count == -1) break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();

        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}