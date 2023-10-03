package com.samkt.asternews.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import daniel.brian.asternews.data.asterNewsDtos.Result

@Composable
fun ArticlesCard(
    results: Result,
    onCardClick: () -> Unit,
    onClickShareButton: (title: String, body: String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .clickable {
                onCardClick()
            },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Column() {
                Text(
                    text = results.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    ),
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (results.image != null) {
                    Surface(shape = MaterialTheme.shapes.medium) {
                        AsyncImage(
                            modifier = Modifier
                                .size(100.dp)
                                .background(
                                    shape = MaterialTheme.shapes.medium,
                                    color = Color.Transparent,
                                ),
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(results.image)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Articles Image",
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Text(
                    text = results.body,
                    maxLines = 4,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "${results.dateTimePub}",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Light,
                    ),
                )
                IconButton(
                    onClick = {
                        onClickShareButton(
                            results.title,
                            results.body,
                        )
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share Button",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

// @Preview
// @Composable
// fun ArticlesCardPrev() {
//    AsterNewsTheme {
//        ArticlesCard(
//            uiState = HomeUiState(
//                articles = emptyList(),
//                title = "This is an example title",
//                body = "This is an example body to test the working of the app This is an example body to test the working of the " +
//                        "appThis is an example body to test the working of the app",
//                timeUpdated = "25 min",
//            ),
//            onCardClick = {},
//        ) {
//        }
//    }
// }
