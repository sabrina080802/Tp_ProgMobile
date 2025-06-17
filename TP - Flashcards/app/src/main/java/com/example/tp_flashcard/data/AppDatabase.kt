package com.example.tp_flashcard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [FlashCardEntity::class, FlashCardCategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun flashcardDao(): FlashcardDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "flash_db"
                )
                    .addCallback(SeedCallback(context))
                    .build()
                    .also { INSTANCE = it }
            }
    }


    private class SeedCallback(private val ctx: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                get(ctx).flashcardDao().apply {
                    insertCategories(seedCategories)
                    insertFlashcards(seedCards)
                }
            }
        }
    }
}


private val seedCategories = listOf(
    FlashCardCategoryEntity(1, "Mathématiques"),
    FlashCardCategoryEntity(2, "Histoire"),
    FlashCardCategoryEntity(3, "Informatique")
)

private val seedCards = listOf(
    // -------- Mathématiques (categoryId = 1) --------
    FlashCardEntity(1,  "Quelle est la dérivée de x² ?",                "2x",                       1),
    FlashCardEntity(2,  "Combien vaut π (3 décimales) ?",              "3,142",                    1),
    FlashCardEntity(3,  "Quel est le résultat de 7 × 6 ?",             "42",                       1),
    FlashCardEntity(4,  "Quelle est l’intégrale de 1/x ?",             "ln|x| + C",                1),
    FlashCardEntity(5,  "Formule de l’aire d’un cercle ?",             "π r²",                     1),

    // -------- Histoire (categoryId = 2) --------
    FlashCardEntity(6,  "En quelle année débute la Révolution française ?", "1789",               2),
    FlashCardEntity(7,  "Qui était le premier empereur romain ?",      "Auguste",                  2),
    FlashCardEntity(8,  "Quelle ville a été bombardée le 6 août 1945 ?", "Hiroshima",            2),
    FlashCardEntity(9,  "Quel mur est tombé en 1989 ?",                "Le mur de Berlin",         2),
    FlashCardEntity(10, "Qui a découvert l’Amérique en 1492 ?",        "Christophe Colomb",        2),

    // -------- Informatique (categoryId = 3) --------
    FlashCardEntity(11, "Que signifie HTML ?",                         "HyperText Markup Language",3),
    FlashCardEntity(12, "Langage créé par Guido van Rossum ?",         "Python",                   3),
    FlashCardEntity(13, "Combien de bits dans un octet ?",             "8",                        3),
    FlashCardEntity(14, "Algorithme de tri en O(n log n) le plus connu ?", "Quicksort",           3),
    FlashCardEntity(15, "Port TCP utilisé par HTTP ?",                 "80",                       3)
)
