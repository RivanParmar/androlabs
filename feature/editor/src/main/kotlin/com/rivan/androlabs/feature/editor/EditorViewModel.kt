package com.rivan.androlabs.feature.editor

import android.net.Uri
import android.provider.DocumentsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivan.androlabs.core.data.repository.FileSystemRepository
import com.rivan.androlabs.core.data.repository.ProjectViewNode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val repository: FileSystemRepository,
) : ViewModel() {

    private val _visibleNodes = MutableStateFlow<List<ProjectViewNode>>(emptyList())
    val visibleNodes = _visibleNodes.asStateFlow()

    fun loadProjectView(treeUri: Uri) {
        viewModelScope.launch {
            val rootDocId = DocumentsContract.getTreeDocumentId(treeUri)

            val rootChildren = repository.getFolderChildren(
                rootTreeUri = treeUri,
                parentDocumentId = rootDocId,
                depth = 0
            )

            _visibleNodes.value = rootChildren
        }
    }

    fun toggleFolder(node: ProjectViewNode) {
        if (!node.isDirectory) return

        viewModelScope.launch {
            _visibleNodes.update { currentList ->
                val mutableList = currentList.toMutableList()
                val index = mutableList.indexOfFirst { it.documentId == node.documentId }
                if (index == -1) return@update currentList

                if (node.isExpanded) {
                    // COLLAPSE: Remove descendants
                    var removeCount = 0
                    for (i in index + 1 until mutableList.size) {
                        if (mutableList[i].depth > node.depth) {
                            removeCount++
                        } else {
                            break
                        }
                    }
                    mutableList.subList(index + 1, index + 1 + removeCount).clear()
                    mutableList[index] = node.copy(isExpanded = false)
                } else {
                    // EXPAND: Fetch and insert children
                    val children = repository.getFolderChildren(
                        rootTreeUri = node.rootTreeUri,
                        parentDocumentId = node.documentId, // Use this folder's ID
                        depth = node.depth + 1
                    )
                    mutableList.addAll(index + 1, children)
                    mutableList[index] = node.copy(isExpanded = true)
                }

                mutableList
            }
        }
    }
}