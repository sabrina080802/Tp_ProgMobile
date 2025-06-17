package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp_flashcard.model.FlashCardCategory
import com.example.tp_flashcard.model.FlashcardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(repo: FlashcardRepository) : ViewModel() {
    val categories: StateFlow<List<FlashCardCategory>> =
        repo.categories.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )
}
