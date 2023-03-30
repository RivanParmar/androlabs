package com.rivan.androlabs.core.network.model.util

import com.rivan.androlabs.core.model.data.ProjectResourceType
import com.rivan.androlabs.core.model.data.asProjectResourceType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ProjectResourceTypeSerializer : KSerializer<ProjectResourceType> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "type",
        kind = STRING
    )

    override fun deserialize(decoder: Decoder): ProjectResourceType =
        decoder.decodeString().asProjectResourceType()

    override fun serialize(encoder: Encoder, value: ProjectResourceType) =
        encoder.encodeString(value.serializedName)
}