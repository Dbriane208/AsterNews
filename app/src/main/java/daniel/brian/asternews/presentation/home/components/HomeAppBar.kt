package daniel.brian.asternews.presentation.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import daniel.brian.asternews.ui.theme.AsterNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    onMenuClicked: () -> Unit,
    onSearchClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = "Aster News")
        },
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Button")
            }
        },
    )
}

@Preview
@Composable
fun HomeAppBarPrev() {
    AsterNewsTheme {
        HomeAppBar(onMenuClicked = { }) {
        }
    }
}
