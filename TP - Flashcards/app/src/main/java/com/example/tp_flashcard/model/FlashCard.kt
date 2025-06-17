package com.example.tp_flashcard.model

data class FlashCard(
    val id: Int,
    val question: String,
    val answer: String,
    val categoryId: Int
)
