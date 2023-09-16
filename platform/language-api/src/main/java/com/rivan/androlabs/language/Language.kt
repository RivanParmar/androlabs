package com.rivan.androlabs.language

import java.util.Collections
import java.util.concurrent.ConcurrentHashMap

abstract class Language protected constructor(val id: String) {

    init {
        val langClass = javaClass
        var prev = ourRegisteredLanguages.putIfAbsent(langClass, this)
        if (prev != null) {
            throw IllegalStateException("Language of '$langClass' is already registered.")
        }

        prev = ourRegisteredIds.putIfAbsent(id, this)
        if (prev != null) {
            throw IllegalStateException("Language with ID '$id' is already registered.")
        }
    }

    fun getDisplayName(): String = id

    override fun toString(): String = "Language: $id"

    companion object {
        private val ourRegisteredLanguages: MutableMap<Class<out Language>, Language> =
            ConcurrentHashMap()
        private val ourRegisteredIds: MutableMap<String, Language> = ConcurrentHashMap()

        fun getRegisteredLanguages(): Collection<Language> {
            val languages = ourRegisteredLanguages.values
            return Collections.unmodifiableCollection(ArrayList(languages))
        }
    }
}