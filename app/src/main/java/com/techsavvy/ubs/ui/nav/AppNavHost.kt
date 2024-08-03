package com.techsavvy.ubs.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techsavvy.ubs.ui.DashboardScreen
import com.techsavvy.ubs.ui.LoginScreen

@androidx.compose.runtime.Composable
fun AppNavHost() {
    val navController = rememberNavController();
    Scaffold {
        NavHost(
            navController = navController, startDestination = "dashboard",
            modifier = Modifier.padding(it)
        ) {
            composable("login") {
                LoginScreen(navController)
            }
            composable("dashboard") {
                DashboardScreen(navController)
            }

        }

    }
}