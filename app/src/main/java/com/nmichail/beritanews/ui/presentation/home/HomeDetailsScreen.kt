package com.nmichail.beritanews.ui.presentation.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.nmichail.beritanews.domain.model.Article


@Composable
fun HomeDetailsScreen(article: Article) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = article.title,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(top = 34.dp)
        )

        article.urlToImage?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        } ?: Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = article.content ?: "No content available",
            style = TextStyle(fontSize = 18.sp, color = Color.Black)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${article.author ?: "Unknown"}",
            style = TextStyle(fontSize = 16.sp, color = Color.Gray),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "${article.source}",
            style = TextStyle(fontSize = 16.sp, color = Color(0xFF0D8CF1)),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Published At: ${article.publishedAt ?: "Unknown date"}",
            style = TextStyle(fontSize = 16.sp, color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0D8CF1)),
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        """
                            ${article.title}
                            ${article.description ?: ""}
                            Read more here: ${article.url}
                        """.trimIndent()
                    )
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }
        ) {
            Text(text = "Share",
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding())
        }
        Spacer(modifier = Modifier.height(35.dp))
    }
}
