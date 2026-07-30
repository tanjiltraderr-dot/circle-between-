package com.example.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.material3.NavigationBarItemDefaults

@Composable
fun MainScreen(
    unreadNotificationCount: Int = 0,
    onNavigateToSearch: () -> Unit, 
    onNavigateToProduct: (String) -> Unit, 
    onNavigateToCircleDeals: (String?) -> Unit,
    onNavigateToNotification: () -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    
    val items = listOf("Home", "Categories", "Cart", "Orders", "Profile")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Category, Icons.Filled.ShoppingCart, Icons.Filled.ShoppingBag, Icons.Filled.Person)
    val unselectedIcons = listOf(Icons.Outlined.Home, Icons.Outlined.Category, Icons.Outlined.ShoppingCart, Icons.Outlined.ShoppingBag, Icons.Outlined.Person)

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                                contentDescription = item
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF388E3C),
                            selectedTextColor = Color(0xFF388E3C),
                            indicatorColor = Color(0xFFE8F5E9)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedItem) {
            0 -> HomeScreen(
                modifier = Modifier.padding(innerPadding), 
                unreadNotificationCount = unreadNotificationCount,
                onNavigateToSearch = onNavigateToSearch, 
                onNavigateToProduct = onNavigateToProduct,
                onNavigateToCategory = { selectedItem = 1 },
                onNavigateToCircleDeals = onNavigateToCircleDeals,
                onNavigateToNotification = onNavigateToNotification
            )
            1 -> CategoryScreen(modifier = Modifier.padding(innerPadding))
            2 -> CartScreen(modifier = Modifier.padding(innerPadding))
            3 -> OrdersScreen(modifier = Modifier.padding(innerPadding))
            4 -> ProfileScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
