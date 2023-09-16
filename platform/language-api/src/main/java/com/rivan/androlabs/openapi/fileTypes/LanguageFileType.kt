package com.rivan.androlabs.openapi.fileTypes

import com.rivan.androlabs.language.Language

abstract class LanguageFileType protected constructor(
    val language: Language,
    val secondary: Boolean
) : FileType {

    constructor(language: Language) : this(language, false)

    fun getDisplayName(): String = language.getDisplayName()
}