package com.rivan.androlabs.core.data.repository

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FileSystemRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : FileSystemRepository {

    override suspend fun getFolderChildren(
        rootTreeUri: Uri,
        parentDocumentId: String,
        depth: Int,
    ): List<ProjectViewNode> = withContext(Dispatchers.IO) {
        val nodes = mutableListOf<ProjectViewNode>()

        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
            rootTreeUri,
            parentDocumentId,
        )

        val projection = arrayOf(
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
        )

        context.contentResolver.query(
            childrenUri,
            projection,
            null,
            null,
            null,
        )?.use { cursor ->
            val idIdx = cursor.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_DOCUMENT_ID)
            val nameIdx =
                cursor.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
            val mimeIdx = cursor.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_MIME_TYPE)

            while (cursor.moveToNext()) {
                val docId = cursor.getString(idIdx)
                val name = cursor.getString(nameIdx)
                val mimeType = cursor.getString(mimeIdx)

                val isDir = mimeType == DocumentsContract.Document.MIME_TYPE_DIR

                val docUri = DocumentsContract.buildDocumentUriUsingTree(rootTreeUri, docId)

                nodes.add(
                    ProjectViewNode(
                        rootTreeUri = rootTreeUri,
                        documentUri = docUri,
                        documentId = docId,
                        name = name,
                        isDirectory = isDir,
                        depth = depth,
                    ),
                )
            }
        }

        nodes.sortedWith(compareBy({ !it.isDirectory }, { it.name.lowercase() }))
    }
}