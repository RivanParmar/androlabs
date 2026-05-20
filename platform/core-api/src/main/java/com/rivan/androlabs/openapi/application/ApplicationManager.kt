package com.rivan.androlabs.openapi.application

interface ApplicationManager {

    suspend fun <T> runReadAction(block: suspend () -> T): T

    suspend fun <T> runWriteAction(block: suspend () -> T): T

    val isReadAccessAllowed: Boolean

    val isWriteAccessAllowed: Boolean

    companion object {
        @Volatile
        var instance: ApplicationManager? = null
    }
}