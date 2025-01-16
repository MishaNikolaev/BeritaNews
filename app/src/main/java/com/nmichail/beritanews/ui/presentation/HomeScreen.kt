package com.nmichail.beritanews.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nmichail.beritanews.domain.model.Article

@Composable
fun HomeScreen(articles: List<Article>) {
    if (articles.isEmpty()) {
        Text(text = "Loading articles...")
    } else {
        LazyColumn {
            items(articles.size) { index ->
                val article = articles[index]
                Row(modifier = Modifier.padding(8.dp)) {
                    article.urlToImage?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(text = article.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        article.description?.let { Text(text = it, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                    }
                }
            }
        }
    }
}