package com.mssoftinc.bttsg.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mssoftinc.bttsg.ui.screen.CategoryScreenRoute
import com.mssoftinc.bttsg.ui.screen.HistoryCategoryScreenRoute
import com.mssoftinc.bttsg.ui.screen.HistoryScreenRoute
import com.mssoftinc.bttsg.ui.screen.HomeScreenRoute
import com.mssoftinc.bttsg.ui.screen.QuestionScreenRoute
import com.mssoftinc.bttsg.ui.screen.ResultScreenRoute

const val homeNavigationRoute="home_navigation_route"

internal const val typeArg="type"
internal const val categoryIdArg="category_id"
internal const val titleArg="title"




fun NavGraphBuilder.homeScreen(
    onItemClick: (String,String) -> Unit,
    onHistory:()->Unit
    ) {
    composable(route = homeNavigationRoute) {
        HomeScreenRoute(
            onItemClick=onItemClick,
            onHistory=onHistory
        )
    }
}

fun NavController.navigateToHomeScreenRoute(navOptions: NavOptions? = null){
    this.navigate(homeNavigationRoute,navOptions)
}
const val categoryScreenRoute="category_route"
fun NavGraphBuilder.categoryScreen(
    onBackClick: () -> Unit,
    onItemClick: (String,Long) -> Unit

){
    composable(
        route= "$categoryScreenRoute/{$titleArg}/{$typeArg}",
        arguments = listOf(
            navArgument(titleArg){type= NavType.StringType},
            navArgument(typeArg){type= NavType.StringType},
        )
    ){
        CategoryScreenRoute(
            onBackClick = onBackClick,
            onItemClick = onItemClick
        )
    }
}
fun NavController.navigateToCategoryScreenRoute(navOptions: NavOptions? = null,title: String,type: String){
    this.navigate("$categoryScreenRoute/${title}/${type}",navOptions)
}



const val historyCategoryScreenRoute="history_category_route"
fun NavGraphBuilder.historyCategoryScreen(
    onBackClick: () -> Unit,
    onItemClick: (String,Long) -> Unit

){
    composable(
        route= historyCategoryScreenRoute,
    ){
        HistoryCategoryScreenRoute(
            onBackClick = onBackClick,
            onItemClick = onItemClick
        )
    }
}
fun NavController.navigateToHistoryCategoryScreenRoute(navOptions: NavOptions? = null){
    this.navigate(historyCategoryScreenRoute,navOptions)
}

const val questionScreenRoute="question_route"
fun NavGraphBuilder.questionScreen(
    onBackClick: () -> Unit,
    goToHistory: (String,Long) -> Unit
){
    composable(
        route= "$questionScreenRoute/{$titleArg}/{$categoryIdArg}",
        arguments = listOf(
            navArgument(titleArg){type= NavType.StringType},
            navArgument(categoryIdArg){type= NavType.LongType},
        )
    ){
        QuestionScreenRoute(onBackClick = onBackClick,goToHistory=goToHistory)
    }
}
fun NavController.navigateToQuestionScreenRoute(navOptions: NavOptions? = null,title: String,categoryId: Long){
    this.navigate("$questionScreenRoute/${title}/${categoryId}",navOptions)
}
const val historyScreenRoute="history_route"
fun NavGraphBuilder.historyScreen(
    onBackClick: () -> Unit,
){
    composable(
        route= "$historyScreenRoute/{$titleArg}/{$categoryIdArg}",
        arguments = listOf(
            navArgument(titleArg){type= NavType.StringType},
            navArgument(categoryIdArg){type= NavType.LongType},
        )
    ){
        HistoryScreenRoute(onBackClick = onBackClick)
    }
}
fun NavController.navigateToHistoryScreenRoute(navOptions: NavOptions? = null,title: String,categoryId: Long){
    this.navigate("$historyScreenRoute/${title}/${categoryId}",navOptions)
}


const val resultScreenRoute="result_route"
fun NavGraphBuilder.resultScreen(
    onBackClick: () -> Unit,
){
    composable(
        route= "$resultScreenRoute/{$titleArg}/{$categoryIdArg}",
        arguments = listOf(
            navArgument(titleArg){type= NavType.StringType},
            navArgument(categoryIdArg){type= NavType.LongType},
        )
    ){
        ResultScreenRoute(onBackClick = onBackClick)
    }
}
fun NavController.navigateToResultScreenRoute(navOptions: NavOptions? = null,title: String,categoryId: Long){

    this.navigate("$resultScreenRoute/${title}/${categoryId}",navOptions)
}
