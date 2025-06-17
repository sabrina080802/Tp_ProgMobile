package com.example.tp_flashcard.model

import com.example.tp_flashcard.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FlashcardRepository private constructor(
    private val dao: FlashcardDao
) {
    val categories: Flow<List<FlashCardCategory>> =
        dao.getCategories().map { list -> list.map(EntityMapper::cat) }

    fun cardsFor(catId: Int): Flow<List<FlashCard>> =
        dao.getCards(catId).map { list -> list.map(EntityMapper::card) }

    companion object {
        @Volatile private var INSTANCE: FlashcardRepository? = null
        fun getInstance(dao: FlashcardDao): FlashcardRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FlashcardRepository(dao).also { INSTANCE = it }
            }

        private val seedCategories = listOf(
            FlashCardCategory(1, "Mathématiques"),
            FlashCardCategory(2, "Histoire"),
            FlashCardCategory(3, "Informatique")
        )

        private val seedCards = listOf(
            FlashCard(1, "Quelle est la dérivée de x² ?", "2x", 1),
            FlashCard(2, "Combien vaut π (3 décimales) ?", "3,142", 1),
            FlashCard(3, "7 × 6 ?", "42", 1),
            FlashCard(4, "∫ 1/x ?", "ln|x| + C", 1),
            FlashCard(5, "Aire d’un cercle ?", "π r²", 1),
            FlashCard(6, "Début Révolution française ?", "1789", 2),
            FlashCard(7, "Premier empereur romain ?", "Auguste", 2),
            FlashCard(8, "Ville bombardée le 6 août 1945 ?", "Hiroshima", 2),
            FlashCard(9, "Mur tombé en 1989 ?", "Mur de Berlin", 2),
            FlashCard(10, "Découvreur de l’Amérique ?", "Christophe Colomb", 2),
            FlashCard(11, "Signification de HTML ?", "HyperText Markup Language", 3),
            FlashCard(12, "Langage créé par Guido van Rossum ?", "Python", 3),
            FlashCard(13, "Bits dans un octet ?", "8", 3),
            FlashCard(14, "Tri O(n log n) célèbre ?", "Quicksort", 3),
            FlashCard(15, "Port TCP d’HTTP ?", "80", 3)
        )
    }
}

private object EntityMapper {
    fun card(e: FlashCardEntity) =
        FlashCard(e.id, e.question, e.answer, e.categoryId)

    fun cat(e: FlashCardCategoryEntity) =
        FlashCardCategory(e.id, e.name)
}
