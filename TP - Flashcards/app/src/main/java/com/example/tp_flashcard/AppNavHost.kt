package com.example.tp_flashcard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tp_flashcard.ui.screens.flashcards.FlashcardScreen
import com.example.tp_flashcard.ui.screens.home.HomeScreen
import com.example.tp_flashcard.viewmodel.HomeViewModel
import com.example.tp_flashcard.viewmodel.FlashcardViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun AppNavHost(
    homeViewModel: HomeViewModel,
    flashcardViewModel: FlashcardViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(
                homeViewModel = homeViewModel,
                onCategoryClick = { category ->
                    navController.navigate("flashcards/${category.id}")
                }
            )
        }

        composable(
            route = "flashcards/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId")
                if (categoryId != null) {
                FlashcardScreen(
                    categoryId = categoryId,
                    viewModel = flashcardViewModel,
                    navController = navController
                )
            } else {
                Text("Catégorie introuvable")
            }
        }
    }
}
