package com.rivan.androlabs.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rivan.androlabs.core.domain.model.UserProjectResource
import com.rivan.androlabs.core.model.data.ProjectResource
import com.rivan.androlabs.core.model.data.ProjectResourceType
import com.rivan.androlabs.core.model.data.UserProjectResourceData
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class UserProjectResourcePreviewParameterProvider
    : PreviewParameterProvider<List<UserProjectResource>> {
    override val values: Sequence<List<UserProjectResource>>
        get() {
            val userProjectResourceData: UserProjectResourceData = UserProjectResourceData(
                recentProjectResources = emptySet(),
                favouriteProjectResources = setOf("1", "3"),
                completedProjectResources = emptySet(),
            )

            return sequenceOf(
                listOf(
                    UserProjectResource(
                        projectResource = ProjectResource(
                            id = "1",
                            title = "Android App 1",
                            extraTitle = "Beginner Project",
                            description = "No description provided!",
                            url = null,
                            headerImageUrl = "null",
                            lastEdited = LocalDateTime(
                                year = 2023,
                                monthNumber = 2,
                                dayOfMonth = 28,
                                hour = 11,
                                minute = 0,
                                second = 0,
                                nanosecond = 0
                            ).toInstant(TimeZone.UTC),
                            path = "/downloads/",
                            type = ProjectResourceType.Project
                        ),
                        userProjectResourceData = userProjectResourceData
                    ),
                    UserProjectResource(
                        projectResource = ProjectResource(
                            id = "2",
                            title = "Android Lab 1",
                            extraTitle = "Beginner Lab",
                            description = "No description provided!",
                            url = null,
                            headerImageUrl = "null",
                            lastEdited = Instant.parse("2022-12-15T00:00:00.00Z"),
                            path = null,
                            type = ProjectResourceType.Lab
                        ),
                        userProjectResourceData = userProjectResourceData
                    ),
                    UserProjectResource(
                        projectResource = ProjectResource(
                            id = "3",
                            title = "Android Lab 2",
                            extraTitle = "Foundational Lab",
                            description = "No description provided!",
                            url = null,
                            headerImageUrl = "null",
                            lastEdited = Instant.parse("2022-12-09T00:00:00.00Z"),
                            path = null,
                            type = ProjectResourceType.JetpackCompose
                        ),
                        userProjectResourceData = userProjectResourceData
                    )
                )
            )
        }
}