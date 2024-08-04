package com.techsavvy.ubs.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techsavvy.ubs.ui.DashboardScreen
import com.techsavvy.ubs.ui.EnvoyerScreen
import com.techsavvy.ubs.ui.LoginScreen
import com.techsavvy.ubs.ui.PayerScreen
import com.techsavvy.ubs.ui.SuccessScreen

@androidx.compose.runtime.Composable
fun AppNavHost() {
    val navController = rememberNavController();
    Scaffold {
        NavHost(
            navController = navController, startDestination = "login",
            modifier = Modifier.padding(it)
        ) {
            composable("login") {
                LoginScreen(navController)
            }
            composable("dashboard") {
                DashboardScreen(navController)
            }
            composable("payer"){
                PayerScreen(navController)
            }
            composable("envoyer"){
                EnvoyerScreen(navController)
            }
            composable("success/{amount}/{name}",
                arguments = listOf(
                    androidx.navigation.navArgument("amount"){
                        type = androidx.navigation.NavType.StringType
                    },
                    androidx.navigation.navArgument("name") {
                        type = androidx.navigation.NavType.StringType
                    }
                )){
                val amount = it.arguments?.getString("amount")
                val name = it.arguments?.getString("name")
                SuccessScreen(amount = amount!!, name = name!!, navController)
            }

        }

    }
}