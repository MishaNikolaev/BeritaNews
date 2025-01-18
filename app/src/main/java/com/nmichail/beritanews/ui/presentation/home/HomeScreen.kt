package com.nmichail.beritanews.ui.presentation.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.ui.presentation.components.ArticleCard
import com.nmichail.beritanews.ui.presentation.components.SearchSection
import com.nmichail.beritanews.ui.presentation.components.TopBar

@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit
) {
    val articles by newsViewModel.articles.collectAsState()
    val offlineArticles by newsViewModel.offlineArticles.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    var isTopBarVisible by remember { mutableStateOf(true) }
    val topBarHeight by animateDpAsState(targetValue = if (isTopBarVisible) 130.dp else 0.dp)

    LaunchedEffect(lazyListState.firstVisibleItemIndex, lazyListState.firstVisibleItemScrollOffset) {
        val currentOffset = lazyListState.firstVisibleItemScrollOffset
        isTopBarVisible = !(currentOffset > 10 && lazyListState.firstVisibleItemIndex > 0)
    }

    LaunchedEffect(Unit) {
        newsViewModel.loadLocalArticles()
    }

    val displayedArticles = if (articles.isNotEmpty()) articles else offlineArticles

    val filteredArticles = displayedArticles.filter { article ->
        article.title.contains(searchQuery, ignoreCase = true) &&
                (selectedCategory.isEmpty() || article.category == selectedCategory)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topBarHeight)
                .background(Color.White)
        ) {
            if (isTopBarVisible) {
                Column {
                    TopBar()
                    Spacer(modifier = Modifier.height(8.dp))

                    SearchSection(
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredArticles.size) { index ->
                val article = filteredArticles[index]
                ArticleCard(article = article, onArticleClick = onArticleClick)
            }
        }
    }
}