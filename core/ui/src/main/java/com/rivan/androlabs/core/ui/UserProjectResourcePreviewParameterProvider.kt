package com.rivan.androlabs.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rivan.androlabs.core.domain.model.UserProjectResource
import com.rivan.androlabs.core.model.data.*

class UserProjectResourcePreviewParameterProvider : PreviewParameterProvider<List<UserProjectResource>> {
    override val values: Sequence<List<UserProjectResource>>
        get() {
            val userData: UserData = UserData(
                savedProjectResources = setOf("1", "3"),
                completedProjectResources = emptySet(),
                themeType = ThemeType.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false
            )

            return sequenceOf(
                listOf(
                    UserProjectResource(
                        projectResource = ProjectResource(
                            id = "1",
                            title = "Android App 1",
                            shortDescription = "First Android app made by me.",
                            longDescription = "No description provided!",
                            path = "/downloads/",
                            type = listOf(ProjectResourceType.Project)
                        ),
                        userData = userData
                    ),
                    UserProjectResource(
                        projectResource = ProjectResource(
                            id = "2",
                            title = "Android Lab 1",
                            shortDescription = "First Android Lab.",
                            longDescription = "No description provided!",
                            path = null,
                            type = listOf(ProjectResourceType.Lab)
                        ),
                        userData = userData
                    ),
                    UserProjectResource(
                        projectResource = ProjectResource(
                            id = "3",
                            title = "Android Lab 2",
                            shortDescription = "Second Android Lab.",
                            longDescription = "No description provided!",
                            path = null,
                            type = listOf(ProjectResourceType.Project, ProjectResourceType.JetpackCompose)
                        ),
                        userData = userData
                    )
                )
            )
        }
}