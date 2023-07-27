package com.mssoftinc.bttsg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {

        homeScreen(
            onItemClick={title,type->navController.navigateToCategoryScreenRoute(null,title,type)},
            onHistory = {navController.navigateToHistoryCategoryScreenRoute()}
        )
        categoryScreen (
            onBackClick=onBackClick,
            onItemClick = {title,categoryId->navController.navigateToQuestionScreenRoute(null,title,categoryId)}
        )
        questionScreen(
            onBackClick=onBackClick,
            goToHistory = {title,categoryId->navController.navigateToResultScreenRoute(null,title,categoryId)}
        )
        historyScreen (onBackClick)
        resultScreen { navController.navigateToHomeScreenRoute() }
        historyCategoryScreen(
            onBackClick,
            onItemClick = {title,categoryId->navController.navigateToHistoryScreenRoute(null,title,categoryId)}
        )




    }
}