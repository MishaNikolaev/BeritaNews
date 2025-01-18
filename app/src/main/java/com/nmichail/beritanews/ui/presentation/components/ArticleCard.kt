package com.nmichail.beritanews.ui.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun ArticleCard(article: Article,
                onArticleClick: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF5F5F5))
            .clickable {
                onArticleClick(article)
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