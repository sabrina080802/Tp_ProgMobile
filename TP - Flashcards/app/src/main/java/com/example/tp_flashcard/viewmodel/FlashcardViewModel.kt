package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp_flashcard.model.FlashcardRepository
import com.example.tp_flashcard.ui.screens.flashcards.FlashcardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashcardViewModel(private val repo: FlashcardRepository) : ViewModel() {

    private val _ui = MutableStateFlow(FlashcardUiState())
    val uiState: StateFlow<FlashcardUiState> = _ui

    fun startSession(catId: Int) {
        viewModelScope.launch {
            repo.cardsFor(catId).collect { list ->
                _ui.value = FlashcardUiState(
                    flashcards = list,
                    currentIndex = 0,
                    isSessionFinished = list.isEmpty()
                )
            }
        }
    }

    fun goToNextCard() = _ui.update { it.withNextIndex() }
}
