package com.rivan.androlabs.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * A [Serializer] for the [UserLabPrefs] proto.
 */
class UserLabPrefsSerializer @Inject constructor() :
    Serializer<UserLabPrefs> {

    override val defaultValue: UserLabPrefs =
        UserLabPrefs.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserLabPrefs =
        try {
            // readFrom is already called on the data store background thread
            @Suppress("BlockingMethodInNonBlockingContext")
            UserLabPrefs.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: UserLabPrefs, output: OutputStream) =
        // writeTo is already called on the data store background thread
        @Suppress("BlockingMethodInNonBlockingContext")
        t.writeTo(output)
}