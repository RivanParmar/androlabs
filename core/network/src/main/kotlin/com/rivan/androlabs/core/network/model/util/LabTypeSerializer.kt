package com.rivan.androlabs.core.network.model.util

import com.rivan.androlabs.core.model.data.LabType
import com.rivan.androlabs.core.model.data.asLabType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LabTypeSerializer : KSerializer<LabType> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "type",
        kind = STRING
    )

    override fun deserialize(decoder: Decoder): LabType =
        decoder.decodeString().asLabType()

    override fun serialize(encoder: Encoder, value: LabType) =
        encoder.encodeString(value.serializedName)
}