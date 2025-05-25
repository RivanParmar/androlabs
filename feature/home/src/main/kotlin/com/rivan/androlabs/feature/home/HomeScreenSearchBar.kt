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

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarScrollBehavior
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.component.ALTopSearchBar
import com.rivan.androlabs.core.model.data.ContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenSearchBar(
    scrollBehavior: SearchBarScrollBehavior,
    contentType: ContentType,
    recentSearchQueriesUiState: RecentSearchQueriesUiState,
    modifier: Modifier = Modifier,
    searchBarState: SearchBarState = rememberSearchBarState(),
    textFieldState: TextFieldState = rememberTextFieldState(),
    onSearch: (String) -> Unit,
    onLeadingIconClick: () -> Unit,
    onTrailingIconClick: () -> Unit,
    onSearchItemClick: (String) -> Unit,
    onRecentSearchDelete: (String) -> Unit,
    onClearRecentSearches: () -> Unit,
) {
    ALTopSearchBar(
        scrollBehavior = scrollBehavior,
        placeholderRes = R.string.search_bar_placeholder_text,
        isDocked = contentType == ContentType.DUAL_PANE,
        modifier = modifier,
        searchBarState = searchBarState,
        textFieldState = textFieldState,
        onSearch = onSearch,
        onLeadingIconClick = onLeadingIconClick,
        onTrailingIconClick = onTrailingIconClick,
    ) {
        if (recentSearchQueriesUiState is RecentSearchQueriesUiState.Success) {
            RecentSearchesPanel(
                recentSearchQueries = recentSearchQueriesUiState.recentQueries.map { it.query },
                onRecentSearchClicked = onSearchItemClick,
                onRecentSearchLongClicked = onRecentSearchDelete,
                onClearRecentSearches = onClearRecentSearches,
            )
        }
    }
}

@Composable
private fun RecentSearchesPanel(
    recentSearchQueries: List<String>,
    onRecentSearchClicked: (String) -> Unit,
    onRecentSearchLongClicked: (String) -> Unit,
    onClearRecentSearches: () -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.search_bar_recent_searches),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            if (recentSearchQueries.isNotEmpty()) {
                TextButton(
                    onClick = onClearRecentSearches,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = "Clear")
                }
            }
        }

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(recentSearchQueries) { recentSearchQuery ->
                RecentSearchItem(
                    recentSearchQuery = recentSearchQuery,
                    onRecentSearchClicked = { onRecentSearchClicked(recentSearchQuery) },
                    onRecentSearchLongClicked = { onRecentSearchLongClicked(recentSearchQuery) }
                )
            }
        }
    }
}

@Composable
private fun RecentSearchItem(
    recentSearchQuery: String,
    onRecentSearchClicked: (String) -> Unit,
    onRecentSearchLongClicked: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 14.dp)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClickLabel = null,
                onClick = { onRecentSearchClicked(recentSearchQuery) },
                onLongClickLabel = null,
                onLongClick = { onRecentSearchLongClicked(recentSearchQuery) }
            )
    ) {
        Icon(
            imageVector = Icons.Outlined.History,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
        )

        Text(
            text = recentSearchQuery,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 18.dp)
                .fillMaxWidth(),
        )
    }
}