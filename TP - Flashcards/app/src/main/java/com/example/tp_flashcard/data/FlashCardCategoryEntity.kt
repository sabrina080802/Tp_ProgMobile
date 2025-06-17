package com.example.tp_flashcard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class FlashCardCategoryEntity(
    @PrimaryKey val id: Int,
    val name: String
)