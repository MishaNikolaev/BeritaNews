package com.nmichail.beritanews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.nmichail.beritanews.domain.model.Article
import com.nmichail.beritanews.ui.presentation.home.NewsViewModel
import com.nmichail.beritanews.ui.presentation.home.HomeDetailsScreen
import com.nmichail.beritanews.ui.presentation.home.HomeScreen
import com.nmichail.beritanews.ui.presentation.welcome.CountrySelectionScreen
import com.nmichail.beritanews.ui.presentation.welcome.StartUpViewModel
import com.nmichail.beritanews.ui.presentation.welcome.WelcomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object HomeDetails : Screen("homedetails") {
        val arguments = listOf(
            navArgument("article") { type = NavType.StringType }
        )
    }
}

enum class StartUpDestination {
    WelcomeScreen, CountrySelectionScreen, HomeScreen
}

@Composable
fun NavGraph(
    startUpViewModel: StartUpViewModel,
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val articles by newsViewModel.articles.collectAsState()
    var startDestination by remember { mutableStateOf<StartUpDestination?>(null) }

    LaunchedEffect(Unit) {
        startUpViewModel.navigationEvent.collect { destination ->
            startDestination = destination
        }
    }

    if (startDestination != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination!!.name
        ) {
            composable(StartUpDestination.WelcomeScreen.name) {
                WelcomeScreen {
                    startUpViewModel.completeFirstLaunch()
                    navController.navigate(StartUpDestination.CountrySelectionScreen.name)
                }
            }

            composable(StartUpDestination.CountrySelectionScreen.name) {
                CountrySelectionScreen { country ->
                    startUpViewModel.saveSelectedCountry(country)
                    navController.navigate(StartUpDestination.HomeScreen.name)
                }
            }

            composable(StartUpDestination.HomeScreen.name) {
                HomeScreen(
                    newsViewModel = newsViewModel,
                    onArticleClick = { article ->
                        val articleJson = java.net.URLEncoder.encode(Gson().toJson(article), "UTF-8")
                        navController.navigate("${Screen.HomeDetails.route}/$articleJson")
                    }
                )
            }

            composable(
                route = "${Screen.HomeDetails.route}/{article}",
                arguments = Screen.HomeDetails.arguments
            ) { navBackStackEntry ->
                val articleJson = navBackStackEntry.arguments?.getString("article")
                val article = articleJson?.let {
                    val decodedJson = java.net.URLDecoder.decode(it, "UTF-8")
                    Gson().fromJson(decodedJson, Article::class.java)
                }
                if (article != null) {
                    HomeDetailsScreen(article = article)
                }
            }
        }
    }
}