package com.mssoftinc.bttsg.ui.screen

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.mssoftinc.bttsg.MainActivity
import com.mssoftinc.bttsg.R
import com.mssoftinc.bttsg.model.UserData
import com.mssoftinc.bttsg.ui.viewmodel.InitialViewModel
import com.mssoftinc.bttsg.utils.DATA
import com.mssoftinc.bttsg.utils.PRACTICE
import com.mssoftinc.bttsg.utils.RANDOM_QUESTION
import com.mssoftinc.bttsg.utils.TEST_QUESTION
import com.mssoftinc.bttsg.utils.ThemeRadioGroupUsage
import com.mssoftinc.bttsg.utils.findActivity

@Composable
fun HomeScreenRoute(
    viewModel: InitialViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit,
    onHistory: () -> Unit,
) {
    val userData by viewModel.settings.collectAsStateWithLifecycle()

    val context = LocalView.current.context
    BackHandler(true) {
        context.findActivity()?.finish()
    }
    HomeScreen(onItemClick = onItemClick,onHistory=onHistory,userData=userData,onThemeChange={theme->viewModel.onThemeChange(theme)})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onItemClick: (String, String) -> Unit, onHistory: () -> Unit, userData: UserData,onThemeChange:(Int)->Unit) {



    var openThemeDialog by remember { mutableStateOf(false) }
    if(openThemeDialog){
        AlertDialog(onDismissRequest = {},

            title = {
                Text("Theme Change")

            },
            text = {
                ThemeRadioGroupUsage(userData.theme,onItemClick ={
                    onThemeChange.invoke(it)
                    openThemeDialog=false
                })
            },
            confirmButton = {
                Text(
                    modifier = Modifier
                        .clickable {
                            openThemeDialog=false
                        },
                    text = "Cancel",
                )
            },

            dismissButton = {

            }
        )
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "BTT SG") },
            actions = {
                IconButton(onClick = { openThemeDialog=true }) {
                    Icon(imageVector = Icons.Default.Settings,contentDescription = null)
                }
            }
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Let's Practice", fontSize = 48.sp
            )


            HomeItem(
                text = "Practice Question",
                description = stringResource(R.string.practice_desc),
                image = R.drawable.exam,
                type= PRACTICE,
                onItemClick = onItemClick
            )
            HomeItem(
                text = "Random Question",
                description = stringResource(R.string.random_desc),
                image = R.drawable.dices,
                onItemClick = onItemClick,
                type = RANDOM_QUESTION
            )
            HomeItem(
                text = "Test Question",
                description = stringResource(R.string.test_desc),
                image = R.drawable.quiz,
                onItemClick = onItemClick,
                type = TEST_QUESTION
            )
            HomeItem(
                text = "Test Question 2",
                description = stringResource(R.string.test_desc),
                image = R.drawable.quiz,
                onItemClick = onItemClick,
                type = DATA
            )
            HomeItem(
                text = "History",
                description = stringResource(R.string.history_desc),
                image = R.drawable.clock,
                onItemClick = {_,_-> onHistory.invoke() },
                type = TEST_QUESTION
            )

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeItem(
    text: String,
    description: String,
    image: Int,
    onItemClick: (String, String) -> Unit,
    type: String
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = { onItemClick.invoke(text,type) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(75.dp),
                painter = painterResource(id = image),
                contentDescription = text
            )
            Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp)) {
                Text(text = text, style = MaterialTheme.typography.titleLarge)
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(onItemClick = { _, _ -> }, onHistory = {}, userData = UserData("admob",0), onThemeChange = {})

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeSetting(setting: UserData, updateTheme: (Int) -> Unit) {

    val modes = listOf("Auto",  "Dark","Light")

    var openThemeDialog by remember { mutableStateOf(true) }
    if(openThemeDialog){
        AlertDialog(onDismissRequest = {},

            title = {
                Text("Change Theme")
            },
            text = {
                ThemeRadioGroupUsage(setting.theme,onItemClick ={
                    updateTheme.invoke(it)
                    openThemeDialog=false
                })
            },
            confirmButton = {
                Text(
                    modifier = Modifier
                        .clickable {
                            openThemeDialog=false
                        },
                    text = "Cancel",
                )
            },

            dismissButton = {

            }
        )
    }

    Text("App Theme", style = MaterialTheme.typography.bodySmall)

    OutlinedCard(

        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, color = Color(54, 168, 160)),
        onClick = {openThemeDialog=true}
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "থিম ")
            Text(text = modes[setting.theme])
        }
    }
}
