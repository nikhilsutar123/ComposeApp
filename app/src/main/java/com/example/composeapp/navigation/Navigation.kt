package com.example.composeapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeapp.screen.DetailScreen
import com.example.composeapp.screen.HomeScreen
import com.example.composeapp.bottomnavscreens.*
import com.example.composeapp.screen.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    //val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen") {
//        composable(route = Screen.Home.route) {
//            HomeScreen(navController = navController)
//        }
//
//        composable(route = Screen.Details.route + "/{name}",
//            arguments = listOf(
//                navArgument("name") {
//                    type = NavType.StringType
//                    defaultValue = "Nikhil"
//                }
//            )
//        ) {
//            DetailScreen(name = it.arguments?.getString("name"))
//        }
        /** Splash screen Navigation **/

//        composable("splash_screen") {
//            SplashScreen(navController = navController)
//        }
//        composable("home_screen") {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(text = "Home screen")
//            }
//        }

        /** Bottom Navigation bar **/
        composable("home_screen") {
            HomeScreen()
        }
        composable("notifications") {
            Notifications()
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}

