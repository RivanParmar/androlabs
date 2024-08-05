package com.rivan.androlabs.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rivan.androlabs.core.designsystem.R.drawable.ic_launcher_background_2
import com.rivan.androlabs.core.designsystem.component.ALIconToggleButton
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.model.data.UserLabs

// TODO: Not yet completed
@Composable
fun LabCard(
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
            LabHeaderImage(userLabs.headerImageUrl)
        }

        HorizontalDivider()

        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                LabTitle(userLabs.title)
                Spacer(modifier = Modifier.height(4.dp))
                LabExtraTitle(userLabs.extraTitle)
            }
        }
    }
}

@Composable
private fun LabHeaderImage(
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
private fun LabTitle(
    projectResourceTitle: String,
    modifier: Modifier = Modifier
) {
    Text(projectResourceTitle, style = MaterialTheme.typography.headlineSmall, modifier = modifier)
}

@Composable
private fun FavouriteButton(
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
private fun LabExtraTitle(
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
            LabCard(
                userLabs = userLabs[1],
                isFavourite = false,
                isCompleted = false,
                onToggleFavourite = { },
                onClick = { }
            )
        }
    }
}