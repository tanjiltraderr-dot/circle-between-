package com.example.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.NotificationRepository
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.MyApplicationTheme

@Composable
fun CircleBazarApp() {
    MyApplicationTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = androidx.compose.ui.graphics.Color.White) {
            val navController = rememberNavController()
            val notifications by NotificationRepository.notifications.collectAsStateWithLifecycle()
            val unreadCount = notifications.count { !it.isRead }
            
            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        unreadNotificationCount = unreadCount,
                        onNavigateToSearch = { navController.navigate("search") }, 
                        onNavigateToProduct = { navController.navigate("product/$it") },
                        onNavigateToCircleDeals = { selectedDeal ->
                            if (selectedDeal != null) {
                                val encodedTitle = java.net.URLEncoder.encode(selectedDeal, "UTF-8")
                                navController.navigate("circle_deals?selectedDeal=$encodedTitle")
                            } else {
                                navController.navigate("circle_deals")
                            }
                        },
                        onNavigateToNotification = {
                            
                            navController.navigate("notifications")
                        }
                    )
                }
                composable("notifications") {
                    NotificationScreen(onNavigateBack = { navController.popBackStack() })
                }
                composable(
                    route = "circle_deals?selectedDeal={selectedDeal}",
                    arguments = listOf(androidx.navigation.navArgument("selectedDeal") { 
                        type = androidx.navigation.NavType.StringType
                        nullable = true 
                    })
                ) { backStackEntry ->
                    val selectedDeal = backStackEntry.arguments?.getString("selectedDeal")
                    CircleDealsScreen(
                        onNavigateBack = { navController.popBackStack() }, 
                        onNavigateToProduct = { navController.navigate("product/$it") },
                        selectedDealTitle = selectedDeal
                    )
                }
                composable("search") {
                    SearchScreen(onNavigateBack = { navController.popBackStack() }, onNavigateToProduct = { navController.navigate("product/$it") })
                }
                composable("product/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId") ?: "1"
                    ProductDetailScreen(productId = productId, onNavigateBack = { navController.popBackStack() })
                }
            }
        }
    }
}
