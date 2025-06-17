package com.example.tp_flashcard.ui.screens.flashcards
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tp_flashcard.model.FlashCard
import com.example.tp_flashcard.viewmodel.FlashcardViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(
    categoryId: Int,
    viewModel: FlashcardViewModel,
    navController: NavController
) {
    /* Charge la session quand on arrive. */
    LaunchedEffect(categoryId) { viewModel.startSession(categoryId) }

    val state by viewModel.uiState.collectAsState()
    var showAnswer by rememberSaveable { mutableStateOf(false) }

    /* On remet la carte côté “question” dès qu’on change d’index. */
    LaunchedEffect(state.currentIndex) { showAnswer = false }

    /* Fin de session = retour après 1 s. */
    if (state.isSessionFinished) {
        LaunchedEffect(Unit) {
            delay(1000)
            navController.popBackStack()
        }
        Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Session terminée !") }
        return
        }

    val card = state.flashcards.getOrNull(state.currentIndex)
    if (card == null) {
        Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
        return
    }
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Révision") })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressIndicator(
                    currentIndex = state.currentIndex,
                    total = state.flashcards.size
                )
                AnimatedContent(
                    targetState = state.currentIndex,
                    label = "flashcard",
                    transitionSpec = {
                        val forward = targetState > initialState
                        val slideIn  = slideInHorizontally { w -> if (forward)  w else -w }
                        val slideOut = slideOutHorizontally { w -> if (forward) -w else  w }
                        (slideIn + fadeIn()) togetherWith (slideOut + fadeOut())
                    }
                ) { idx ->
                    FlashcardCard(
                        flashCard  = state.flashcards[idx],
                        showAnswer = showAnswer,
                        onToggle   = { showAnswer = !showAnswer }
                    )
                }
                Button(
                    onClick = {
                        showAnswer = false
                        viewModel.goToNextCard()
                    }
                ) { Text("Suivant") }
            }
        }
}
@Composable
fun ProgressIndicator(currentIndex: Int, total: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val progress = (currentIndex + 1).toFloat() / total

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Carte ${currentIndex + 1} / $total")
    }
}

/* ---------- Carte “flip 3-D” ---------- */
@Composable
fun FlashcardCard(
    flashCard: FlashCard,
    showAnswer: Boolean,
    onToggle: () -> Unit
) {
    val scope      = rememberCoroutineScope()
    val rotation   = remember { Animatable(0f) }
    val density    = LocalDensity.current.density

    LaunchedEffect(flashCard.id) {
        rotation.snapTo(0f)
    }

    val handleClick = {
        scope.launch {
            val target = if (rotation.value < 90f) 180f else 0f
            rotation.animateTo(
                target,
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
            )
            onToggle()
        }
    }

    Card(
        Modifier
            .fillMaxWidth()
            .clickable { handleClick() }
            .padding(16.dp)
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 8 * density
            },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(Modifier.padding(24.dp), Alignment.Center) {
            val isAnswer = rotation.value >= 90f
            /* remet le texte à l’endroit au dos */
            val textRotation = if (isAnswer) 180f else 0f

            Text(
                text  = if (isAnswer) flashCard.answer else flashCard.question,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.graphicsLayer { rotationY = textRotation }
            )
        }
    }
}
