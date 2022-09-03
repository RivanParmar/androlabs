package com.rivan.androidplaygorunds.project.utils.project_templates;

import com.rivan.androidplaygorunds.project.utils.FileUtil;
import com.rivan.androidplaygorunds.project.utils.class_templates.JavaClassTemplate;
import com.rivan.androidplaygorunds.project.utils.interfaces.Project;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class JavaProject implements Project {

    private static final String rootDirPath = FileUtil.getProjectsDir();

    private final File root;

    public JavaProject(File root) {
        this.root = root;
    }

    public @NotNull JavaProject newProject(@NotNull String projectName) throws IOException {
        var projectRoot = new File(getRootDirPath() + projectName);
        if (!projectRoot.exists() && !projectRoot.mkdirs()) {
            throw new IOException("Unable to create directory");
        }
        var project = new JavaProject(projectRoot);
        project.init();
        return project;
    }

    @Override
    public void init() {
        FileUtil.createOrExistsDir(getProjectDirPath());
        FileUtil.createOrExistsDir(getSrcDirPath());
        FileUtil.createOrExistsDir(getBinDirPath());
        FileUtil.createOrExistsDir(getLibDirPath());
        FileUtil.createOrExistsDir(getBuildDirPath());
        FileUtil.createOrExistsDir(getCacheDirPath());
        JavaClassTemplate javaClassTemplate = new JavaClassTemplate();
        var classTemplate = javaClassTemplate.getTemplate(null, "Main", true);
        FileUtil.writeFileFromString(getSrcDirPath() + "Main.kt", classTemplate);
    }

    @Override
    public void delete() {
        FileUtil.deleteAllInDir(getProjectDirPath());
        FileUtil.delete(getProjectDirPath());
    }

    @Override
    public @NotNull File getRootFile() {
        return root;
    }

    @Override
    public @NotNull String getRootDirPath() {
        return rootDirPath;
    }

    @NotNull
    @Override
    public String getProjectName() {
        return getRootFile().getName();
    }

    @NotNull
    @Override
    public String getProjectDirPath() {
        return getRootFile().getAbsolutePath() + File.separator;
    }

    @NotNull
    @Override
    public String getSrcDirPath() {
        return getRootDirPath() + "src" + File.separator;
    }

    @NotNull
    @Override
    public String getBinDirPath() {
        return getRootDirPath() + "bin" + File.separator;
    }

    @NotNull
    @Override
    public String getLibDirPath() {
        return getRootDirPath() + "libs" + File.separator;
    }

    @NotNull
    @Override
    public String getBuildDirPath() {
        return getRootDirPath() + "build" + File.separator;
    }

    @NotNull
    @Override
    public String getCacheDirPath() {
        return getRootDirPath() + "cache" + File.separator;
    }
}
