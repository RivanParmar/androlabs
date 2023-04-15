package com.rivan.androlabs.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * A [Serializer] for the [UserProjectResourcePrefs] proto.
 */
class UserProjectResourcePrefsSerializer @Inject constructor() :
    Serializer<UserProjectResourcePrefs> {

    override val defaultValue: UserProjectResourcePrefs =
        UserProjectResourcePrefs.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserProjectResourcePrefs =
        try {
            // readFrom is already called on the data store background thread
            @Suppress("BlockingMethodInNonBlockingContext")
            UserProjectResourcePrefs.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: UserProjectResourcePrefs, output: OutputStream) =
        // writeTo is already called on the data store background thread
        @Suppress("BlockingMethodInNonBlockingContext")
        t.writeTo(output)
}