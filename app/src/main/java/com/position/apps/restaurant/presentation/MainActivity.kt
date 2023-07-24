package com.position.apps.restaurant.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.position.apps.restaurant.presentation.detail.RestaurantDetailsScreen
import com.position.apps.restaurant.presentation.list.RestaurantsScreen
import com.position.apps.restaurant.presentation.list.RestaurantsViewModel
import com.position.apps.restaurant.ui.theme.RestaurantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RestaurantApp()
                }
            }
        }
    }
}

@Composable
private fun RestaurantApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "restaurants") {
        composable(route = "restaurants") {
            val viewModel: RestaurantsViewModel = hiltViewModel()
            RestaurantsScreen(state = viewModel.state.value,
                onItemClick = { id ->
                    navController.navigate("restaurants/$id")
                }, onFavoriteClick = { id, oldValue ->
                    viewModel.toggleFavorite(id, oldValue)
                }
            )
        }
        composable(
            route = "restaurants/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            }),
            deepLinks = listOf(navDeepLink { uriPattern = "www.restaurantsapp.details.com/{id}" })
        ) {
            RestaurantDetailsScreen()
        }
    }
}