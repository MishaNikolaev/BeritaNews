package com.nmichail.beritanews.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.nmichail.beritanews.R
import com.nmichail.beritanews.domain.model.Article

@Composable
fun HomeScreen(articles: List<Article>,
               //onArticleClick: (Article) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        TopBar()

        SearchSection(searchQuery) {
            searchQuery = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        CategoryFilters()

        Spacer(modifier = Modifier.height(16.dp))

        val filteredArticles = articles.filter { article ->
            article.title.contains(searchQuery, ignoreCase = true) && article.title != "[Removed]"
        }

        ArticlesList(filteredArticles)
    }
}

@Composable
fun TopBar() {
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.logo_shapes_10),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = "Berita News",
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 24.sp, color = Color.Black),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { /* Notifications logic */ }) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun SearchSection(searchQuery: String, onSearchChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = searchQuery,
            onValueChange = { onSearchChange(it) },
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (searchQuery.isEmpty()) {
                    Text(text = "Search", color = Color.Gray, fontSize = 16.sp)
                }
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun CategoryFilters() {
    val categories = listOf("Trending", "Latest", "Politics", "Health", "Sports")
    var selectedCategory by remember { mutableStateOf("Trending") }
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        categories.forEach { category ->
            Text(
                text = category,
                style = TextStyle(
                    color = if (category == selectedCategory) Color.White else Color.Black
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (category == selectedCategory) Color(0xFF1389E7) else Color(
                            0xFFF5F5F5
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { selectedCategory = category }
            )
        }
    }
}

@Composable
fun ArticlesList(articles: List<Article>,
                 //onArticleClick: (Article) -> Unit
) {
    LazyColumn {
        items(articles.size) { index ->
            val article = articles[index]
            ArticleCard(article)
        }
    }
}

@Composable
fun ArticleCard(article: Article,
                //onArticleClick: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF5F5F5))
            .clickable {
                //onArticleClick(article)
            }
    ) {
        val imagePainter = if (article.urlToImage.isNullOrEmpty()) {
            painterResource(R.drawable.emty_photo)
        } else {
            rememberAsyncImagePainter(article.urlToImage)
        }

        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = article.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.description ?: "",
                style = TextStyle(fontSize = 14.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.source ?: "Unknown Source",
                    style = TextStyle(fontSize = 16.sp,
                        color = Color(0xFF03A9F4),
                        fontWeight = FontWeight.Medium)
                )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.author ?: "Unknown author",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.publishedAt ?: "Unknown date",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
        }
    }
}