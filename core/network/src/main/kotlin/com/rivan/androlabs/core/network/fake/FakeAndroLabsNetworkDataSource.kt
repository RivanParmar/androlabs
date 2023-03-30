package com.rivan.androlabs.core.network.fake

import JvmUnitTestFakeAssetManager
import com.rivan.androlabs.core.network.AndroLabsDispatcher.IO
import com.rivan.androlabs.core.network.AndroLabsNetworkDataSource
import com.rivan.androlabs.core.network.Dispatcher
import com.rivan.androlabs.core.network.model.FirestoreChangeList
import com.rivan.androlabs.core.network.model.FirestoreProjectResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

/**
 * [AndroLabsNetworkDataSource] implementation that provides static project resources to aid
 * development.
 */
class FakeAndroLabsNetworkDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager
) : AndroLabsNetworkDataSource {

    companion object {
        private const val PROJECTS_ASSET = "projects.json"
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getProjectResources(ids: List<String>?): List<FirestoreProjectResource> =
        withContext(ioDispatcher) {
            assets.open(PROJECTS_ASSET).use(networkJson::decodeFromStream)
        }

    override suspend fun getProjectResourceChangeList(after: Int?): List<FirestoreChangeList> =
        getProjectResources().mapToChangeList(FirestoreProjectResource::id)

}

/**
 * Converts a list of [T] to change list of all the items in it where [idGetter] defines the
 * [FirestoreChangeList.id]
 */
private fun <T> List<T>.mapToChangeList(
    idGetter: (T) -> String,
) = mapIndexed { index, item ->
    FirestoreChangeList(
        id = idGetter(item),
        changeListVersion = index,
        isDelete = false
    )
}