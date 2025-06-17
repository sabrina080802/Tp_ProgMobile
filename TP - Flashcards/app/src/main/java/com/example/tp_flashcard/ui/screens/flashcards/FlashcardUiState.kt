package com.example.tp_flashcard.ui.screens.flashcards

import com.example.tp_flashcard.model.FlashCard

data class FlashcardUiState(
    val currentIndex: Int = 0,
    val flashcards: List<FlashCard> = emptyList(),
    val isSessionFinished: Boolean = false
) {
    val currentCard: FlashCard? get() = flashcards.getOrNull(currentIndex)

    fun withNextIndex(): FlashcardUiState {
        val next = currentIndex + 1
        return copy(
            currentIndex = next.coerceAtMost(flashcards.lastIndex),
            isSessionFinished = next >= flashcards.size
        )
    }
}
