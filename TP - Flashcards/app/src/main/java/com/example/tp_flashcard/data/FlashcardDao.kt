package com.example.tp_flashcard.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    /* -------- Lectures réactives -------- */
    @Query("SELECT * FROM categories ORDER BY id")
    fun getCategories(): Flow<List<FlashCardCategoryEntity>>

    @Query("SELECT * FROM flashcards WHERE categoryId = :catId ORDER BY id")
    fun getCards(catId: Int): Flow<List<FlashCardEntity>>

    /* -------- Insertion -------- */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(list: List<FlashCardCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcards(list: List<FlashCardEntity>)
}
