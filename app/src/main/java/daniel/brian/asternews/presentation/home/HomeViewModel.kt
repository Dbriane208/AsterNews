package daniel.brian.asternews.presentation.home

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.brian.asternews.data.asterNewsDtos.Result
import daniel.brian.asternews.data.asterNewsRepository.AsterNewsRepository
import daniel.brian.asternews.presentation.home.components.ArticlesCategories
import daniel.brian.asternews.utils.FetchedResponse
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

    private var _state = MutableStateFlow(HomeUiState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
            val response = asterNewsRepository.getAllArticles()
            response
                .distinctUntilChanged()
                .collectLatest { response ->
                    when (response) {
                        is FetchedResponse.Success -> {
                            val articles = response.data?.articles?.results ?: emptyList()
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    articles = articles,
                                )
                            }
                        }

                        is FetchedResponse.Error -> {
                            val errorMessage = response.message!!
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = errorMessage,
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun getArticlesBasedOnCategory(categoryName: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
            val response = asterNewsRepository.getArticlesOnCategory(categoryName = categoryName)
            response
                .distinctUntilChanged()
                .collectLatest { response ->
                    when (response) {
                        is FetchedResponse.Success -> {
                            val articles = response.data?.articles?.results ?: emptyList()
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    articles = articles,
                                )
                            }
                        }

                        is FetchedResponse.Error -> {
                            val errorMessage = response.message!!
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = errorMessage,
                                )
                            }
                        }
                    }
                }
        }
    }

    fun shareNews(title: String, body: String, activity: Activity) {
        val intent = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_SUBJECT, title)
            .putExtra(Intent.EXTRA_TEXT, body)
        intent.type = ("text/plain")

        val chooserTitle = "Share Via!"
        val chooserIntent = Intent.createChooser(intent, chooserTitle)
        activity.startActivity(chooserIntent)
    }

    fun setCategorySelected(categoryName: String) {
        if (categoryName == ArticlesCategories.ALL.value) {
            getArticles()
        } else {
            getArticlesBasedOnCategory(categoryName = categoryName)
        }
        _state.update {
            it.copy(
                categoryNameSelected = categoryName,
            )
        }
    }

    fun retry() {
        getArticlesBasedOnCategory(categoryName = _state.value.categoryNameSelected)
    }
}

data class HomeUiState(
    val articles: List<Result> = emptyList(),
    val categories: List<ArticlesCategories> = emptyList(),
    val categoryNameSelected: String = ArticlesCategories.ALL.value,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
