package com.nmichail.beritanews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nmichail.beritanews.ui.presentation.home.NewsViewModel
import com.nmichail.beritanews.ui.theme.BeritaNewsTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.beritanews.ui.navigation.NavGraph
import com.nmichail.beritanews.ui.presentation.welcome.StartUpViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeritaNewsTheme {
                val startUpViewModel: StartUpViewModel = hiltViewModel()
                val newsViewModel: NewsViewModel = hiltViewModel()
                NavGraph(startUpViewModel, newsViewModel)
            }
        }
    }
}