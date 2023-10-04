package daniel.brian.asternews.presentation.home

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.brian.asternews.data.asterNewsDtos.Result
import daniel.brian.asternews.data.asterNewsRepository.AsterNewsRepository
import daniel.brian.asternews.presentation.home.components.ArticlesCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val asterNewsRepository: AsterNewsRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        getAllArticles()
    }

    private fun getAllArticles() {
        viewModelScope.launch {
            try {
                val allArticles = asterNewsRepository.getAllArticles()
                allArticles
                    .distinctUntilChanged()
                    .collectLatest { allArticles ->
                        _state.update {
                            it.copy(
                                articles = allArticles.articles.results,
                                hasErrorOccured = false,
                            )
                        }
                    }
            } catch (e: HttpException) {
                updateErrorOccured(true)
            } catch (e: HttpException) {
                updateErrorOccured(true)
            }
        }
    }

    private fun getArticlesByCategory(keyword: String) {
        viewModelScope.launch {
            try {
                val allArticles = asterNewsRepository.getArticlesOnCategory(keyword)
                allArticles
                    .distinctUntilChanged()
                    .collectLatest { allArticles ->
                        _state.update {
                            it.copy(
                                articles = allArticles.articles.results,
                                hasErrorOccured = false,
                            )
                        }
                    }
            } catch (e: HttpException) {
                updateErrorOccured(true)
            } catch (e: HttpException) {
                updateErrorOccured(true)
            }
        }
    }

    fun updateErrorOccured(boolean: Boolean) {
        _state.update {
            it.copy(
                hasErrorOccured = boolean,
            )
        }
    }

    fun shareNews(title: String, body: String, activity: Activity) {
        val intent = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_SUBJECT, title)
            .putExtra(Intent.EXTRA_TEXT, body)
        intent.type = ("text/plain")

        val chooserTitle = "Share Via"
        val chooserIntent = Intent.createChooser(intent, chooserTitle)
        activity.startActivity(chooserIntent)
    }

    fun setCategorySelected(categoryName: String) {
        if (categoryName != ArticlesCategories.ALL.value) {
            getArticlesByCategory(categoryName)
        } else {
            getAllArticles()
        }
        _state.update {
            it.copy(
                categoryNameSelected = categoryName,
            )
        }
    }
}

data class HomeUiState(
    val articles: List<Result> = emptyList(),
    val categories: List<ArticlesCategories> = emptyList(),
    val categoryNameSelected: String = ArticlesCategories.ALL.value,
    val hasErrorOccured: Boolean = false,
)
