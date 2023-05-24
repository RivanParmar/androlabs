package com.rivan.androlabs.openapi.module

import com.rivan.androlabs.openapi.project.Project

interface Module {

    fun getProject(): Project

    fun getName(): String

    fun isLoaded(): Boolean
}