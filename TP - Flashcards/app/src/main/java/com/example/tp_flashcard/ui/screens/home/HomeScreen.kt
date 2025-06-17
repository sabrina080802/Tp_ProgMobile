package com.example.tp_flashcard.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.tp_flashcard.viewmodel.HomeViewModel
import com.example.tp_flashcard.model.FlashCardCategory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onCategoryClick: (FlashCardCategory) -> Unit
) {
    val categories by homeViewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Catégories de Flashcards") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category = category, onClick = { onCategoryClick(category) })
            }
        }
    }
}
@Composable
fun CategoryCard(category: FlashCardCategory, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = category.name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

