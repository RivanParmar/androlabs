package com.rivan.androlabs.core.network.model

import kotlinx.serialization.Serializable

/**
 * Firestore representation of a change list for a model.
 *
 * Change lists are a representation of a server-side map like data structure of model ids to
 * metadata about the model. In a single change list, a given model id can only show up once.
 */
@Serializable
data class FirestoreChangeList(
    /**
     * The id of the model that was changed
     */
    val id: String,
    /**
     * Unique consecutive, monotonically increasing version number in the collection describing
     * the relative point of change between models in the collection
     */
    val changeListVersion: Int,
    /**
     * Summarizes the update to the model; whether it was deleted or updated.
     * Updates include creations.
     */
    val isDelete: Boolean
)