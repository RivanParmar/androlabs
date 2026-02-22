package com.rivan.androlabs.core.data.repository

import android.net.Uri

data class ProjectViewNode(
    val rootTreeUri: Uri,
    val documentUri: Uri,
    val documentId: String,
    val name: String,
    val isDirectory: Boolean,
    val depth: Int,
    var isExpanded: Boolean = false,
)

interface FileSystemRepository {

    suspend fun getFolderChildren(
        rootTreeUri: Uri,
        parentDocumentId: String,
        depth: Int,
    ): List<ProjectViewNode>
}