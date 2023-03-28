package com.rivan.androlabs.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val androLabsDispatcher: AndroLabsDispatcher)

enum class AndroLabsDispatcher {
    IO
}