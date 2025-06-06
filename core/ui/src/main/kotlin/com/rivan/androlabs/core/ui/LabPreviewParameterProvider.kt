package com.rivan.androlabs.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rivan.androlabs.core.model.data.Lab
import com.rivan.androlabs.core.model.data.LabType
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class LabPreviewParameterProvider
    : PreviewParameterProvider<List<Lab>> {
    override val values: Sequence<List<Lab>>
        get() {
            return sequenceOf(
                listOf(
                    Lab(
                        id = 1,
                        title = "Android App 1",
                        extraTitle = "Beginner Project",
                        description = "No description provided!",
                        url = null,
                        headerImageUrl = null,
                        iconPath = null,
                        lastEdited = LocalDateTime(
                            year = 2023,
                            monthNumber = 2,
                            dayOfMonth = 28,
                            hour = 11,
                            minute = 0,
                            second = 0,
                            nanosecond = 0,
                        ).toInstant(TimeZone.UTC),
                        path = "/downloads/",
                        type = LabType.App,
                        vendor = null,
                    ),
                    Lab(
                        id = 2,
                        title = "Androlab 1",
                        extraTitle = "Beginner Lab",
                        description = "No description provided!",
                        url = null,
                        headerImageUrl = null,
                        iconPath = null,
                        lastEdited = Instant.parse("2022-12-15T00:00:00.00Z"),
                        path = null,
                        type = LabType.Lab,
                        vendor = null,
                    ),
                    Lab(
                        id = 3,
                        title = "Androlab 2",
                        extraTitle = "Foundational Lab",
                        description = "No description provided!",
                        url = null,
                        headerImageUrl = null,
                        iconPath = null,
                        lastEdited = Instant.parse("2022-12-09T00:00:00.00Z"),
                        path = null,
                        type = LabType.JetpackCompose,
                        vendor = null,
                    ),
                ),
            )
        }
}