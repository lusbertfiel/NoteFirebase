package com.fiel.notefirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fiel.notefirebase.ui.presentation.add.AddScreen
import com.fiel.notefirebase.ui.presentation.home.HomeScreen
import com.fiel.notefirebase.ui.presentation.update.UpdateScreen
import com.fiel.notefirebase.ui.theme.NoteFirebaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            NoteFirebaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(route = Screen.Home.route) {
                            HomeScreen( navController)
                        }
                        composable(route=Screen.Add.route){
                            AddScreen(navController)
                        }
                        composable(
                            arguments = listOf(
                                navArgument("nota"){
                                    type= NavType.StringType
                                }
                            ),
                            route = Screen.Update.route){
                            UpdateScreen(navController)
                        }
                    }
                }
            }
        }
    }
}


sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Add : Screen("add")
    data object Update : Screen("update/{nota}"){
        fun sendNota(nota:String)="update/$nota"
    }
}