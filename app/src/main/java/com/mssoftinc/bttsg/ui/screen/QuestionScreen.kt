package com.mssoftinc.bttsg.ui.screen

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mssoftinc.bttsg.R
import com.mssoftinc.bttsg.model.Question
import com.mssoftinc.bttsg.model.sampleQuestions
import com.mssoftinc.bttsg.ui.viewmodel.QuestionViewModel
import com.mssoftinc.bttsg.utils.loadInterstitial
import com.mssoftinc.bttsg.utils.showInterstitial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun QuestionScreenRoute(
    onBackClick: () -> Unit,
    viewModel: QuestionViewModel = hiltViewModel(),
    goToHistory: (String, Long) -> Unit,
) {
    val title = viewModel.title
    val score = viewModel.score
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    val context = LocalView.current.context
    loadInterstitial(context)
    QuestionScreen(
        onBackClick = onBackClick,
        title = title,
        questions = questions,
        score = score,
        onAnswerSubmit = {id, answer,isCorrect ->
            if (isCorrect){
                viewModel.incrementScore()
            }
            viewModel.updateAttempt(id=id, answer=answer)

        },
        onComplete = {
            viewModel.updateCategoryScore()
            goToHistory.invoke(title,viewModel.categoryId)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun QuestionScreen(
    onBackClick: () -> Unit,
    title: String,
    questions: List<Question>,
    score: Int,
    onAnswerSubmit: (id: Int, answer: Int, isCorrect: Boolean) -> Unit,
    onComplete: () -> Unit,
) {
    val context = LocalView.current.context

    val `snack-barHostState` = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
        TopAppBar(title = { Text(text = title) }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })
    },
        snackbarHost = { SnackbarHost(`snack-barHostState`) },
    ) {
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp),
        ) {
            val pagerState = rememberPagerState()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Question: ${pagerState.currentPage + 1}/${questions.size}")
                Text(text = "Score: $score/${questions.size}")

            }


            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                pageCount = questions.size, state = pagerState,
                userScrollEnabled = false,


            ) { pageNumber ->
                val question = questions[pageNumber]
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = question.question)
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                       if(question.imageName!="."){
                           Log.i("123321", "QuestionScreen: ${question.imageName}")
                           AsyncImage(
                               modifier= Modifier
                                   .fillMaxSize()
                                   .padding(24.dp),
                               model = if(question.imageName.endsWith(".png"))"file:///android_asset/${question.imageName}" else "file:///android_asset/${question.imageName}.png",
                               contentDescription = "thumbnails",
                               placeholder =  painterResource(id = R.drawable.animals)
                           )
                       }
                    }

                    var selected by remember { mutableStateOf(-1) }
                    var correctAnswer by remember { mutableStateOf(-1) }
                    var isSubmitted by remember { mutableStateOf(false)}

                    Options(
                        question.choice0,
                        index = 0,
                        selected = selected,
                        correctAnswer = correctAnswer,
                        onClick = { index -> selected = index ;isSubmitted=true})
                    Options(
                        question.choice1,
                        index = 1,
                        selected = selected,
                        correctAnswer = correctAnswer,
                        onClick = { index -> selected = index ;isSubmitted=true})
                    Options(
                        question.choice2,
                        index = 2,
                        selected = selected,
                        correctAnswer = correctAnswer,
                        onClick = { index -> selected = index;isSubmitted=true })
                    val context = LocalView.current.context

                   if(isSubmitted){
                       isSubmitted=false
                        correctAnswer = question.answer
                        onAnswerSubmit.invoke(question.id,selected,selected==correctAnswer)
                        if (selected == correctAnswer){
                            val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.right)
                            mediaPlayer.start()
                        }
                        else{
                            val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.wrong)
                            mediaPlayer.start()
                        }

                      LaunchedEffect(isSubmitted){
                          showInterstitial(context){  coroutineScope.launch {
                              delay(3000)

                              // Call scroll to on pagerState
                              if (pagerState.canScrollForward) {

                                  pagerState.animateScrollToPage(pagerState.currentPage+1)}

                              else onComplete.invoke()
                          }
                          }
                      }

                    }


                /*    Button(onClick = {
                    onComplete.invoke()
                    },
                        modifier = Modifier.padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        ),
                        enabled = correctAnswer==-1
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Submit",
                            textAlign = TextAlign.Center
                        )
                    }*/
                }
            }

        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Options(
    question: String,
    index: Int,
    selected: Int,
    onClick: (Int) -> Unit,
    correctAnswer: Int,
) {
    val color = if (correctAnswer != -1) {

        when (index) {
            correctAnswer -> Color(0xff1B5E20)
            selected -> Color(0xffC62828)
            else -> Color(0xff424242)
        }

    } else {
        if (selected == index) Color(0xff757575)else Color(0xFF424242)
    }
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = color
        ),
        onClick = { onClick.invoke(index) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            text = question,
            textAlign = TextAlign.Center,
            color= Color.White
        )
    }
}


@Preview
@Composable
fun PreviewQuestionScreen() {
    QuestionScreen(
        onBackClick = {},
        title = "Driving Licences",
        questions = sampleQuestions,
        score = 0,
        onAnswerSubmit = {_, _, _ ->},
        onComplete = {}
    )

}