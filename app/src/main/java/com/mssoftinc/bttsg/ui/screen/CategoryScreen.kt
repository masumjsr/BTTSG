package com.mssoftinc.bttsg.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mssoftinc.bttsg.R
import com.mssoftinc.bttsg.model.Category
import com.mssoftinc.bttsg.model.sampleCategory
import com.mssoftinc.bttsg.ui.viewmodel.CategoryViewModel

@Composable
fun CategoryScreenRoute(
    onBackClick: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel(),
    onItemClick: (String, Long) -> Unit,
) {
    val title = viewModel.title
    val category by viewModel.category.collectAsStateWithLifecycle()
    CategoryScreen(onBackClick = onBackClick, title = title, category = category,onItemClick=onItemClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    onBackClick: () -> Unit,
    title: String,
    category: List<Category>,
    onItemClick: (String, Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(category) {
                ElevatedCard(
                    onClick={
                        onItemClick.invoke(it.title, it.id.toLong())
                    },
                    modifier= Modifier.padding(8.dp)
                ) {
                    Row (
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                        AsyncImage(
                            modifier= Modifier.size(75.dp),
                            model = "file:///android_asset/${it.imageName}.png",
                            contentDescription = "thumbnails",
                            placeholder =  painterResource(id = R.drawable.animals)
                        )
                        Column(
                            modifier= Modifier
                                .weight(1f)
                                .padding(10.dp),
                        ) {
                            Text(text = it.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = "${it.total_question} Questions", style = MaterialTheme.typography.bodyMedium)
                        }
                        Column (){
                            Text(text = "Score:")
                            Button(onClick = {  }) {
                                Text(
                                    text =it.last_attempt.toString())
                            }
                        }
                    }
                }

            }
        }

    }

}

@Preview
@Composable
fun PreviewCategoryScreen() {
    CategoryScreen(
        onBackClick = {},
        title = "Practice Question",
        category = sampleCategory,
        onItemClick = {_, _ ->}
    )

}