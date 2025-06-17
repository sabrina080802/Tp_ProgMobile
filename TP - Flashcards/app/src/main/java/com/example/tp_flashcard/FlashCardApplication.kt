package com.example.tp_flashcard

import android.app.Application
import com.example.tp_flashcard.data.AppDatabase
import com.example.tp_flashcard.model.FlashcardRepository

class FlashcardApplication : Application() {
    val repository: FlashcardRepository by lazy {
        FlashcardRepository.getInstance(
            AppDatabase.get(this).flashcardDao()
        )
    }
}
