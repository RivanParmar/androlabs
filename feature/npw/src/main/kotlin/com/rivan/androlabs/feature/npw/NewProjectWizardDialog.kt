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

package com.rivan.androlabs.feature.npw

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.rivan.androlabs.wizard.template.api.Template

@Composable
internal fun NpwRoute(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    isSmallScreen: Boolean,
    npwViewModel: NewProjectWizardViewModel = viewModel(),
) {
    val dialogState by npwViewModel.dialogState.collectAsStateWithLifecycle()
    val tabState by npwViewModel.tabState.collectAsStateWithLifecycle()

    NewProjectWizardDialog(
        dialogState = dialogState,
        tabState = tabState,
        shouldUseScrollableTabRow = isSmallScreen,
        templates = npwViewModel.getTemplates(),
        switchTab = npwViewModel::switchTab,
        onDismiss = onDismiss,
        canGoForward = npwViewModel::setCanGoForward,
        canGoBack = npwViewModel::setCanGoBack,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewProjectWizardDialog(
    dialogState: NpwDialogState,
    tabState: NpwTabState,
    shouldUseScrollableTabRow: Boolean,
    templates: List<Template>,
    switchTab: (Int) -> Unit,
    onDismiss: () -> Unit,
    canGoForward: (Boolean) -> Unit,
    canGoBack: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.new_project_dialog_title)) },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = if (dialogState.canGoBack) {
                                Icons.AutoMirrored.Default.ArrowBack
                            } else {
                                Icons.Outlined.Clear
                            },
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            navController.navigate("configureAndroidProjectStep")
                            canGoForward(false)
                            canGoBack(true)
                        },
                        // Enable this only when the user has selected a template
                        enabled = dialogState.canGoForward,
                    ) {
                        Text(text = "Next")
                    }
                },
            )
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "npw_graph",
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            navigation(startDestination = "chooseAndroidProjectStep", route = "npw_graph") {
                composable("chooseAndroidProjectStep") {
                    ChooseAndroidProjectStep(
                        tabState = tabState,
                        templates = templates,
                        switchTab = switchTab,
                        canGoForward = canGoForward,
                        shouldUseScrollableTabRow = shouldUseScrollableTabRow,
                    )
                }
            }
        }
    }
}