package daniel.brian.asternews.presentation.home

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import daniel.brian.asternews.presentation.home.components.ArticlesCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.brian.asternews.data.asterNewsDtos.AllArticles
import daniel.brian.asternews.data.asterNewsRepository.AsterNewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val asterNewsRepository: AsterNewsRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private var _articles: MutableStateFlow<PagingData<AllArticles>> =
        MutableStateFlow(value = PagingData.empty())

    val articles = _articles.asStateFlow()

    init {
        getAllArticles()
    }

    private fun getAllArticles() {
        viewModelScope.launch {
            val allArticles = asterNewsRepository.getAllArticles()
            allArticles.collectLatest { allArticles ->
            }
        }
    }

    private fun getArticlesByCategory(keyword: String) {
        viewModelScope.launch {
            val allArticles = asterNewsRepository.getArticlesOnCategory(keyword)
            allArticles
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collectLatest { allArticles ->
                    _articles.value = allArticles
                }
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
            getArticlesByCategory(categoryName)
        }
        _state.update {
            it.copy(
                categoryNameSelected = categoryName,
            )
        }
    }
}

data class HomeUiState(
    val categories: List<ArticlesCategories> = emptyList(),
    val categoryNameSelected: String = ArticlesCategories.ALL.value,
)
