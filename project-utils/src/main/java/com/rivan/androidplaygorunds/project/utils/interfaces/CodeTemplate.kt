package com.rivan.androidplaygorunds.project.utils.interfaces;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface CodeTemplate {

    //fun getTemplate(@Nullable packageName: String, @NotNull className: String): String

    fun getTemplate(@Nullable packageName: String, @NotNull className: String, @Nullable createMainMethod: Boolean): String
}
