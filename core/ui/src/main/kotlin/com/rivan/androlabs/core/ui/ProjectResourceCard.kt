package com.rivan.androlabs.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rivan.androlabs.core.designsystem.R.*
import com.rivan.androlabs.core.designsystem.R.drawable.*
import com.rivan.androlabs.core.designsystem.component.ALIconToggleButton
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.domain.model.UserLabs

// TODO: Not yet completed
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectResourceCard(
    userLabs: UserLabs,
    isFavourite: Boolean,
    isCompleted: Boolean,
    onToggleFavourite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Row {
            ProjectResourceHeaderImage(userLabs.headerImageUrl)
        }

        Divider()

        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                ProjectResourceTitle(userLabs.title)
                Spacer(modifier = Modifier.height(4.dp))
                ProjectResourceExtraTitle(userLabs.extraTitle)
            }
        }
    }
}

@Composable
fun ProjectResourceHeaderImage(
    headerImageUrl: String?
) {
    if (!headerImageUrl.isNullOrEmpty()) {
        AsyncImage(
            placeholder = if (LocalInspectionMode.current) {
                // TODO: Add a placeholder image here
                painterResource(ic_launcher_background_2)
            } else {
                // TODO: Show specific loading image visual
                null
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop,
            model = headerImageUrl,
            contentDescription = null
        )
    } else {
        Image(
            // TODO: Use a default image for project resources that don't specify a headerImageUrl
            painterResource(id = ic_launcher_background_2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
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
fun ProjectResourceExtraTitle(
    projectResourceExtraTitle: String
) {
    Text(projectResourceExtraTitle, style = MaterialTheme.typography.labelSmall)
}

@Preview(name = "ProjectResourceCard")
@Composable
private fun ProjectResourceCardPreview(
    @PreviewParameter(UserLabPreviewParameterProvider::class)
    userLabs: List<UserLabs>
) {
    AndrolabsTheme {
        Surface {
            ProjectResourceCard(
                userLabs = userLabs[1],
                isFavourite = false,
                isCompleted = false,
                onToggleFavourite = { },
                onClick = { }
            )
        }
    }
}