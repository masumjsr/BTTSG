package com.mssoftinc.bttsg.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.mssoftinc.bttsg.model.Category
import com.mssoftinc.bttsg.model.Question
import com.mssoftinc.bttsg.model.sampleCategory
import com.mssoftinc.bttsg.model.sampleQuestions
import com.mssoftinc.bttsg.ui.viewmodel.HistoryViewModel

@Composable
fun HistoryScreenRoute(
    onBackClick: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val title = viewModel.title
    val category by viewModel.category.collectAsStateWithLifecycle()
    val questions by viewModel.questions.collectAsStateWithLifecycle()

    HistoryScreen(
        onBackClick=onBackClick,
        title=title,
        category=category,
        questions=questions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryScreen(
    onBackClick: () -> Unit,
    questions: List<Question>,
    category: Category,
    title: String
) {
Scaffold(
    topBar = {
        TopAppBar(title = { Text(text = title) }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })
    }
) { paddingValues ->
    Column(modifier= Modifier
        .padding(paddingValues)
        .fillMaxSize(),) {
        ElevatedCard(modifier= Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Column {
                    Text(text = "Total Question\n${category.total_question}", textAlign = TextAlign.Center)
                    Text(modifier= Modifier.padding(top=8.dp),text = "RW Ratio\n${(100*category.last_attempt)/(category.total_question)}%", textAlign = TextAlign.Center)
                }

                PieChart(
                    pieChartData = PieChartData(
                        listOf(
                            PieChartData.Slice((category.last_attempt/(category.total_question).toFloat()), color =  Color(0xff1B5E20)),
                            PieChartData.Slice(((category.total_question-category.last_attempt)/(category.total_question).toFloat()), color = Color.Red),
                    )

                ),

    modifier = Modifier.size(75.dp),
    animation = simpleChartAnimation(),
    sliceDrawer = SimpleSliceDrawer()
                )

                Column {
                    Text(text = "Correct Answer\n${category.last_attempt}", textAlign = TextAlign.Center)
                    Text(modifier= Modifier.padding(top=8.dp),text = "Wrong Answer\n${(category.total_question-category.last_attempt)}", textAlign = TextAlign.Center)
                }
            }
        }
        LazyColumn {
            items(questions){
                val options= listOf(it.choice0,it.choice1,it.choice2)
                Log.i("123321", "$it")
               ElevatedCard(
                   modifier= Modifier.padding(8.dp),
               ) {
                   Text(
                       modifier= Modifier
                           .fillMaxWidth()
                           .padding(8.dp)
                           .background(
                               Color(0xFF424242),
                               RoundedCornerShape(25)
                           )
                           .padding(24.dp),
                       text = it.question,
                       textAlign = TextAlign.Center,color = Color.White
                   )
                   Text(
                       modifier= Modifier
                           .fillMaxWidth()
                           .padding(8.dp)
                           .background(
                               Color(0xff1B5E20),
                               RoundedCornerShape(25)
                           )
                           .padding(24.dp),
                       text = options[it.answer],
                       textAlign = TextAlign.Center,
                       color= Color.White
                   )
                   if((it.lastAnswer!=-1)&&it.answer!=it.lastAnswer){

                       Text(
                           modifier= Modifier
                               .fillMaxWidth()
                               .padding(8.dp)
                               .background(
                                   Color(0xffC62828),
                                   RoundedCornerShape(25)
                               )
                               .padding(24.dp),

                           text = options[it.lastAnswer],
                           textAlign = TextAlign.Center,
                           color= Color.White

                       )
                   }
               }
            }
        }
    }

}
}

@Preview
@Composable
fun PreviewHistoryScreen() {
    HistoryScreen (onBackClick={}, questions = sampleQuestions, category = sampleCategory[0], title = "title")

}