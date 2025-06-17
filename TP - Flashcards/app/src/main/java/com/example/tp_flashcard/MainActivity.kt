package com.example.tp_flashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tp_flashcard.ui.theme.TP_FlashcardTheme
import com.example.tp_flashcard.viewmodel.*

class MainActivity : ComponentActivity() {

    private val app by lazy { application as FlashcardApplication }
    private val factory by lazy { FlashcardVMFactory(app.repository) }

    private val homeVM: HomeViewModel by viewModels { factory }
    private val flashVM: FlashcardViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TP_FlashcardTheme {
                Scaffold(Modifier.fillMaxSize()) { padding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp)
                    ) {
                        AppNavHost(
                            homeViewModel      = homeVM,
                            flashcardViewModel = flashVM
                        )
                    }
                }
            }
        }
    }
}
