package com.rivan.androlabs.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.component.ALIconToggleButton
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.designsystem.theme.AndroLabsTheme
import com.rivan.androlabs.core.domain.model.UserProjectResource

// TODO: Not yet completed
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectResourceCard(
    userProjectResource: UserProjectResource,
    isFavourite: Boolean,
    isCompleted: Boolean,
    onToggleFavourite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    // Keeping this nullable for now as it is not yet decided whether to use it or not
    // TODO: Remove later if unused
    onResourceTypeClick: ((String) -> Unit)? = null
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProjectResourceTitle(
                            userProjectResource.title,
                            modifier = Modifier.fillMaxWidth((.8f))
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        FavouriteButton(isFavourite, onToggleFavourite)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ProjectResourceLevel(userProjectResource.level)
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
fun FavouriteButton(
    isFavourite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ALIconToggleButton(
        checked = isFavourite,
        onCheckedChange = { onClick() },
        modifier = modifier,
        icon = {
            Icon(
                painter = painterResource(ALIcons.FavouriteBorder),
                contentDescription = null
            )
        },
        checkedIcon = {
            Icon(
                painter = painterResource(ALIcons.Favourite),
                contentDescription = null
            )
        }
    )
}

@Composable
fun ProjectResourceLevel(
    projectResourceShortDescription: String
) {
    Text(projectResourceShortDescription, style = MaterialTheme.typography.bodySmall)
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
                userProjectResource = userProjectResources[1],
                isFavourite = false,
                isCompleted = false,
                onToggleFavourite = { },
                onClick = { },
                onResourceTypeClick = { }
            )
        }
    }
}