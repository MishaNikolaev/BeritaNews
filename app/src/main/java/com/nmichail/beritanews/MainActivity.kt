package com.nmichail.beritanews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nmichail.beritanews.ui.presentation.HomeScreen
import com.nmichail.beritanews.ui.presentation.NewsViewModel
import com.nmichail.beritanews.ui.theme.BeritaNewsTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeritaNewsTheme {
                val viewModel: NewsViewModel = hiltViewModel()
                val articles by viewModel.articles.collectAsState()
                viewModel.fetchNews("us", "YOUR_API_KEY")

                HomeScreen(articles = articles)
            }
        }
    }
}
