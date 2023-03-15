package com.rivan.androlabs.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rivan.androlabs.core.model.data.ProjectResource
import com.rivan.androlabs.core.model.data.ProjectResourceType
import kotlinx.datetime.Instant

/**
 * Defines an Andro Labs project resource for the [RecentScreen].
 */
@Entity(
    tableName = "project_resources"
)
data class ProjectResourceEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    @ColumnInfo(name = "extra_title")
    val extraTitle: String,
    val description: String,
    val url: String?,
    @ColumnInfo(name = "header_image_url")
    val headerImageUrl: String?,
    @ColumnInfo(name = "last_edited")
    val lastEdited: Instant,
    val path: String?,
    val type: ProjectResourceType
)

fun ProjectResourceEntity.asExternalModel() = ProjectResource(
    id = id,
    title = title,
    extraTitle = extraTitle,
    description = description,
    url = url,
    headerImageUrl = headerImageUrl,
    lastEdited = lastEdited,
    path = path,
    type = type
)