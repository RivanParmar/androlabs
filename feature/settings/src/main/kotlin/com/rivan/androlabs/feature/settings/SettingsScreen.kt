package com.rivan.androlabs.feature.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.theme.AndroLabsTheme

@Composable
internal fun SettingsRoute(
    onRootSettingsOptionClick: (String) -> Unit
) {
    SettingsScreen(
        onItemClick = onRootSettingsOptionClick
    )
}

@Composable
internal fun SettingsScreen(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RootSettingsOptions.values().forEach { rootSettingsOptions ->
            val rootSettingsOptionId = rootSettingsOptions.id
            item(key = rootSettingsOptionId) {
                RootSettingsListItem(
                    titleId = rootSettingsOptions.textId,
                    supportingTextId = rootSettingsOptions.secondaryTextId,
                    icon = rootSettingsOptions.icon,
                    onClick = { onItemClick(rootSettingsOptionId) }
                )
            }
        }
    }

    // TODO: Add a card containing the "About" section here
}

@Preview
@Composable
fun SettingsPreview() {
    AndroLabsTheme {
        SettingsScreen(
            onItemClick = {}
        )
    }
}