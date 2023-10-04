package daniel.brian.asternews.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import daniel.brian.asternews.presentation.home.HomeUiState
import daniel.brian.asternews.ui.theme.AsterNewsTheme

enum class ArticlesCategories(val value: String) {
    ALL("All"),
    TECHNOLOGY("Technology"),
    HEALTH("Health"),
    CRIME("Crime"),
    HACKER("Hacker"),
    FITNESS("Fitness"),
    TRAVEL("Travel"),
    FOOD("Food"),
    CLIMATE("Climate"),
    FASHION("Fashion"),
    DATING("Dating"),
    P_LEAGUE("Premier League"),
}

@Composable
fun CategoryList(
    uiState: HomeUiState,
    onCategoryClick: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(20.dp),
    ) {
        items(ArticlesCategories.entries) {
            val selectedCategory = uiState.categoryNameSelected
            val isSelected = selectedCategory == it.value
            val backgroundColor =
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer

            CategoryComposable(
                categoryName = it.value,
                color = backgroundColor,
                onCategoryClick = {
                    onCategoryClick(it.value)
                },
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun CategoryComposable(
    categoryName: String,
    color: Color,
    onCategoryClick: () -> Unit,
) {
    Surface(
        color = color,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.clickable {
            onCategoryClick()
        },
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            textAlign = TextAlign.Center,
            text = categoryName,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
            ),
        )
    }
}

@Preview
@Composable
fun CategoryComposablePrev() {
    AsterNewsTheme {
        CategoryComposable(
            color = Color.Blue,
            categoryName = "ThisText",
        ) {}
    }
}
