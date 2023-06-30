package com.rivan.androlabs.core.network.fake

import JvmUnitTestFakeAssetManager
import com.rivan.androlabs.core.network.AndrolabsDispatcher.IO
import com.rivan.androlabs.core.network.AndrolabsNetworkDataSource
import com.rivan.androlabs.core.network.Dispatcher
import com.rivan.androlabs.core.network.model.FirestoreChangeList
import com.rivan.androlabs.core.network.model.FirestoreLab
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

/**
 * [AndrolabsNetworkDataSource] implementation that provides static labs to aid development.
 */
class FakeAndrolabsNetworkDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager
) : AndrolabsNetworkDataSource {

    companion object {
        private const val LABS_ASSET = "labs.json"
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getLabs(ids: List<String>?): List<FirestoreLab> =
        withContext(ioDispatcher) {
            assets.open(LABS_ASSET).use(networkJson::decodeFromStream)
        }

    override suspend fun getLabChangeList(after: Int?): List<FirestoreChangeList> =
        getLabs().mapToChangeList(FirestoreLab::id)

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