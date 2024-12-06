package com.example.notele.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notele.ui.theme.NoteleAppTheme
import com.example.notele.views.AddEditScreen
import com.example.notele.views.ScreenHome
import com.example.notele.views.navigation.DestinationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationScreen()
        }
    }
}


@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier
) {
    NoteleAppTheme {
        val navController= rememberNavController()

        Surface {
            NavHost(
                navController, DestinationScreen.HomeScreen.value
            ){
                composable(route = DestinationScreen.HomeScreen.value) {
                    ScreenHome(
                        navController = navController
                    )
                }
                composable(route = DestinationScreen.AddScreen.value +
                    "?noteId={noteId}&noteColor={noteColor}",
                    arguments = listOf(
                        navArgument(
                            name = "noteId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            name = "noteColor"
                        ) {
                            type =  NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) {
                    val color = it.arguments?.getInt("noteColor") ?: -1
                    AddEditScreen(
                        noteColor = color,
                        navController = navController
                    )
                }
            }
        }
    }
}