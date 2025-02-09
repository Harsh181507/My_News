package com.example.mynews.presentation.navigation.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mynews.data.model.Article
import com.example.mynews.data.model.Source
import com.example.mynews.presentation.NewsAppViewModel
import com.example.mynews.presentation.screens.CategoryDetailScreenUI
import com.example.mynews.presentation.screens.HomeScreenUI

@Composable
fun AppNavigation(modifier: Modifier = Modifier, viewModel: NewsAppViewModel) {
    val navController = rememberNavController()
    NavHost(navController= navController, startDestination =
    HomeScreen) {
        composable<HomeScreen> {
            HomeScreenUI(modifier = modifier, navController = navController, viewModel = viewModel)
        }
        composable<CategoryScreen> {
            val CategoryScreen=it.toRoute<CategoryScreen>()
            val article = Article(
                author = CategoryScreen.author,
                content = CategoryScreen.content,
                description = CategoryScreen.description,
                publishedAt = CategoryScreen.publishedAt,
                source=Source(
                    id=CategoryScreen.id,
                    name=CategoryScreen.name
                ),
                title = CategoryScreen.title,
                url = CategoryScreen.url,
                urlToImage = CategoryScreen.urlToImage

            )
            CategoryDetailScreenUI(article=article)
        }
    }
}

