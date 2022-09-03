package com.rivan.androidplaygorunds.project.utils.class_templates;

import com.rivan.androidplaygorunds.project.utils.interfaces.CodeTemplate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaClassTemplate implements CodeTemplate {

    @Override
    public @NotNull String getTemplate(@Nullable String packageName, @NotNull String className, boolean createMainMethod) {
        var header = "";
        assert packageName != null;
        if(!packageName.isEmpty()) {
            header = "package " + packageName + ";\n" + "\n";
        }
        return header + "public class " + className + " {\n" +
                (createMainMethod ? "    public static void main(String[] args) {\n"
                        + "        System.out.println(\"Hello, World!\");\n"
                        + "    }"
                        : "    ")
                + "\n" + "}\n";
    }
}
