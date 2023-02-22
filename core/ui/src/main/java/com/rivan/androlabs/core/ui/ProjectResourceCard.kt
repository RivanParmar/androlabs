package com.rivan.androlabs.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.theme.AndroLabsTheme
import com.rivan.androlabs.core.domain.model.UserProjectResource

// TODO: Not yet completed
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectResourceCard(
    userProjectResource: UserProjectResource,
    isSaved: Boolean,
    isCompleted: Boolean,
    onClick: () -> Unit,
    onResourceTypeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        ProjectResourceTitle(
                            userProjectResource.title,
                            modifier = Modifier.fillMaxWidth((.8f))
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    ProjectResourceShortDescription(userProjectResource.shortDescription)
                }
            }
        }
    }
}

@Composable
fun ProjectResourceTitle(
    projectResourceTitle: String,
    modifier: Modifier = Modifier
) {
    Text(projectResourceTitle, style = MaterialTheme.typography.headlineSmall, modifier = modifier)
}

@Composable
fun ProjectResourceShortDescription(
    projectResourceShortDescription: String
) {
    Text(projectResourceShortDescription, style = MaterialTheme.typography.bodyLarge)
}

@Preview("ProjectResourceCard")
@Composable
private fun ProjectResourceCardPreview(
    @PreviewParameter(UserProjectResourcePreviewParameterProvider::class)
    userProjectResources: List<UserProjectResource>
) {
    AndroLabsTheme {
        Surface {
            ProjectResourceCard(
                userProjectResource = userProjectResources[0],
                isSaved = true,
                isCompleted = false,
                onClick = { },
                onResourceTypeClick = { }
            )
        }
    }
}