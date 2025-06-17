package com.example.tp_flashcard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
data class FlashCardEntity(
    @PrimaryKey val id: Int,
    val question: String,
    val answer: String,
    val categoryId: Int
)

