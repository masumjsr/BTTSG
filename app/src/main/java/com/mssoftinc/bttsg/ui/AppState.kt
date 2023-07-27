package com.mssoftinc.bttsg.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberNavController(),
) :AppState{
    return remember(navHostController,coroutineScope){
        AppState(navHostController, coroutineScope)
    }
}

@Stable
class AppState (
    val navHostController: NavHostController,
    val coroutineScope: CoroutineScope
        ){
    val currentDestination: NavDestination?
    @Composable  get() = navHostController
        .currentBackStackEntryAsState().value?.destination

    val state   @Composable get()=(currentDestination?.route?.contains("{")?:true)



    fun onBackClick(){
        navHostController.popBackStack()
    }

   }
