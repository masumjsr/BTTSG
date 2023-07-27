package com.mssoftinc.bttsg

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fsit.sohojnamaj.update.OptionalUpdate
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.mssoftinc.bttsg.navigation.AppNavHost
import com.mssoftinc.bttsg.ui.AppState
import com.mssoftinc.bttsg.ui.rememberAppState
import com.mssoftinc.bttsg.ui.theme.BTTSGTheme
import com.mssoftinc.bttsg.ui.viewmodel.MainActivityUiState
import com.mssoftinc.bttsg.ui.viewmodel.MainActivityViewModel
import com.mssoftinc.bttsg.utils.BannerAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {

            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
                .check()
        }


        val splashScreen=installSplashScreen()
        splashScreen.setKeepOnScreenCondition{

            MainActivityUiState.Loading==uiState

        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collectLatest {
                        uiState = it
                    }
            }
        }
        setContent {
            val darkTheme = shouldUseDarkTheme(uiState)
            BTTSGTheme (darkTheme = darkTheme, dynamicColor = false){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OptionalUpdate {
                        Column {
                            BannerAds(modifier = Modifier.fillMaxWidth())
                            MainContent()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(
    appState: AppState = rememberAppState(),

    ) {

        AppNavHost(
            navController = appState.navHostController,
            onBackClick = appState::onBackClick,

        )
        //AdmobBanner()

}


@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> {
        Log.i("123321", "shouldUseDarkTheme: user data is ${uiState.userData.theme}")
        when (uiState.userData.theme) {
            0 -> isSystemInDarkTheme()
            1 -> true
            else -> false
        }
    }

}