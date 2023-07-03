/*
 * Copyright 2023 Rivan Parmar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.ListType
import com.rivan.androlabs.core.ui.LabFeedUIState

@Composable
internal fun HomeRoute(
    contentType: ContentType,
    listType: ListType,
    displayFeatures: List<DisplayFeature>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()

    HomeScreen(
        contentType = contentType,
        listType = listType,
        closeDetailScreen = viewModel::closeDetailScreen,
        displayFeatures = displayFeatures,
        labFeedUIState = uiState,
        isSyncing = isSyncing,
        navigateToDetail = viewModel::setSelectedLab,
        modifier = modifier
    )
}

@Composable
internal fun HomeScreen(
    contentType: ContentType,
    listType: ListType,
    closeDetailScreen: () -> Unit,
    displayFeatures: List<DisplayFeature>,
    labFeedUIState: LabFeedUIState,
    isSyncing: Boolean,
    navigateToDetail: (String, ContentType) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = contentType) {
        if (contentType == ContentType.SINGLE_PANE) {
            closeDetailScreen()
        }
    }

    if (contentType == ContentType.DUAL_PANE) {
        TwoPane(
            first = {
                HomeScreenLabsGrid(
                    contentType = contentType,
                    listType = listType,
                    labFeedUIState = labFeedUIState,
                    modifier = modifier,
                    isSyncing = isSyncing,
                    navigateToDetail = navigateToDetail
                )
            },
            second = {
                HomeScreenLabsDetail(
                    lab = labFeedUIState.selectedLab,
                    closeDetailScreen = closeDetailScreen,
                    modifier = modifier
                )
            },
            // TODO: Maybe reduce the gap width and set split fraction to .5f
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.7f, gapWidth = 16.dp),
            displayFeatures = displayFeatures
        )
    } else {
        HomeScreenSinglePaneContent(
            contentType = contentType,
            listType = listType,
            labFeedUIState = labFeedUIState,
            isSyncing = isSyncing,
            closeDetailScreen = closeDetailScreen,
            navigateToDetail = navigateToDetail,
            modifier = modifier
        )
    }
}

@Composable
fun HomeScreenSinglePaneContent(
    contentType: ContentType,
    listType: ListType,
    labFeedUIState: LabFeedUIState,
    isSyncing: Boolean,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (String, ContentType) -> Unit,
    modifier: Modifier = Modifier
) {
    if (labFeedUIState.selectedLab != null && labFeedUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        HomeScreenLabsDetail(
            lab = labFeedUIState.selectedLab,
            closeDetailScreen = closeDetailScreen,
            modifier = modifier
        )
    } else {
        HomeScreenLabsGrid(
            contentType = contentType,
            listType = listType,
            labFeedUIState = labFeedUIState,
            modifier = modifier,
            isSyncing = isSyncing,
            navigateToDetail = navigateToDetail
        )
    }
}