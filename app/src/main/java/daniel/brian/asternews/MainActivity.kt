package daniel.brian.asternews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import daniel.brian.asternews.presentation.home.AsterHomeScreen
import daniel.brian.asternews.presentation.home.HomeViewModel
import daniel.brian.asternews.ui.theme.AsterNewsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsterNewsTheme {
                val viewModel: HomeViewModel = hiltViewModel()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AsterHomeScreen(
                        uiState = viewModel.state.collectAsState().value,
                        onCardClick = { /*TODO*/ },
                        onShareButtonClick = { title, body ->
                            viewModel.shareNews(title, body, this)
                        },
                        onMenuClicked = { /*TODO*/ },
                        onSearchClicked = { /*TODO*/ },
                        onCategoryClick = {
                            viewModel.setCategorySelected(it)
                        },
                        retryOnError = {
                            viewModel.retry()
                        },
                    )
                }
            }
        }
    }
}
