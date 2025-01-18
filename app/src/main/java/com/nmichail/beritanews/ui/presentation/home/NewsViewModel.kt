package com.nmichail.beritanews.ui.presentation.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.domain.repository.LocalRepository
import com.nmichail.beritanews.domain.usecases.CompleteFirstLaunchUseCase
import com.nmichail.beritanews.domain.usecases.GetArticleByIdUseCase
import com.nmichail.beritanews.domain.usecases.GetOfflineArticlesUseCase
import com.nmichail.beritanews.domain.usecases.GetTopHeadlinesUseCase
import com.nmichail.beritanews.domain.usecases.SaveArticlesToLocalDatabaseUseCase
import com.nmichail.beritanews.domain.usecases.SaveSelectedCountryUseCase
import com.nmichail.beritanews.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(
private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
private val getOfflineArticlesUseCase: GetOfflineArticlesUseCase,
private val saveArticlesToLocalDatabaseUseCase: SaveArticlesToLocalDatabaseUseCase,
private val getArticleByIdUseCase: GetArticleByIdUseCase,
private val saveSelectedCountryUseCase: SaveSelectedCountryUseCase,
private val completeFirstLaunchUseCase: CompleteFirstLaunchUseCase,
private val localRepository: LocalRepository,
@ApplicationContext private val context: Context
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    private val _offlineArticles = MutableStateFlow<List<Article>>(emptyList())
    val offlineArticles: StateFlow<List<Article>> = _offlineArticles.asStateFlow()

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(context)) {
                try {
                    loadArticlesFromApi(country = "us", apiKey = "YOUR_API_KEY")
                } catch (apiError: Exception) {
                    Log.e("NewsViewModel", "API error: ${apiError.message}, loading offline data...")
                    loadLocalArticles()
                }
            } else {
                loadLocalArticles()
            }
        }
    }

    fun saveArticles(articles: List<Article>) {
        viewModelScope.launch {
            saveArticlesToLocalDatabaseUseCase(articles)
        }
    }

    fun saveSelectedCountry(countryCode: String) {
        viewModelScope.launch {
            saveSelectedCountryUseCase(countryCode)
        }
    }

    fun completeFirstLaunch() {
        viewModelScope.launch {
            completeFirstLaunchUseCase()
        }
    }

    suspend fun loadArticlesFromApi(country: String = "us", apiKey: String) {
        try {
            val fetchedArticles = getTopHeadlinesUseCase(country, apiKey)
            saveArticlesToLocalDatabase(fetchedArticles)
            _articles.value = fetchedArticles
        } catch (e: Exception) {
            Log.e("NewsViewModel", "API fetch failed: ${e.localizedMessage}. Loading offline data...")
            loadLocalArticles()
        }
    }

    fun loadLocalArticles() {
        viewModelScope.launch {
            _offlineArticles.value = getOfflineArticlesUseCase()
        }
    }

    fun fetchArticleById(id: String) {
        viewModelScope.launch {
            val article = getArticleByIdUseCase(id)
            Log.d("NewsViewModel", "Fetched article: ${article?.title}")
        }
    }

    private fun saveArticlesToLocalDatabase(articles: List<Article>) {
        viewModelScope.launch {
            localRepository.clearArticles()
            localRepository.saveArticles(articles)
        }
    }
}
