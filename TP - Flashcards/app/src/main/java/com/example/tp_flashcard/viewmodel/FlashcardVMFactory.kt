package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tp_flashcard.model.FlashcardRepository

class FlashcardVMFactory(
    private val repo: FlashcardRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FlashcardViewModel::class.java -> FlashcardViewModel(repo)
            HomeViewModel::class.java      -> HomeViewModel(repo)
            else -> throw IllegalArgumentException("Unknown VM")
        } as T
    }
}
