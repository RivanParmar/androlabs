package com.rivan.androlabs.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rivan.androlabs.core.domain.model.UserProjectListResource
import com.rivan.androlabs.core.model.data.ProjectListResource
import com.rivan.androlabs.core.model.data.ProjectListResourceType
import com.rivan.androlabs.core.model.data.UserProjectListResourceData
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class UserProjectListResourcePreviewParameterProvider : PreviewParameterProvider<List<UserProjectListResource>> {
    override val values: Sequence<List<UserProjectListResource>>
        get() {
            val userProjectListResourceData: UserProjectListResourceData = UserProjectListResourceData(
                favouriteProjectResources = setOf("1", "3"),
                completedLabs = emptySet(),
            )

            return sequenceOf(
                listOf(
                    UserProjectListResource(
                        projectListResource = ProjectListResource(
                            id = "1",
                            title = "Android App 1",
                            extraTitle = "Beginner Project",
                            description = "No description provided!",
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
                            type = listOf(ProjectListResourceType.Project)
                        ),
                        userProjectListResourceData = userProjectListResourceData
                    ),
                    UserProjectListResource(
                        projectListResource = ProjectListResource(
                            id = "2",
                            title = "Android Lab 1",
                            extraTitle = "Beginner Lab",
                            description = "No description provided!",
                            headerImageUrl = "null",
                            lastEdited = Instant.parse("2022-12-15T00:00:00.00Z"),
                            path = null,
                            type = listOf(ProjectListResourceType.Lab)
                        ),
                        userProjectListResourceData = userProjectListResourceData
                    ),
                    UserProjectListResource(
                        projectListResource = ProjectListResource(
                            id = "3",
                            title = "Android Lab 2",
                            extraTitle = "Foundational Lab",
                            description = "No description provided!",
                            headerImageUrl = "null",
                            lastEdited = Instant.parse("2022-12-09T00:00:00.00Z"),
                            path = null,
                            type = listOf(ProjectListResourceType.Project, ProjectListResourceType.JetpackCompose)
                        ),
                        userProjectListResourceData = userProjectListResourceData
                    )
                )
            )
        }
}