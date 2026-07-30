package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(onNavigateBack: () -> Unit, onNavigateToProduct: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val isSearching = searchQuery.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Search Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f).height(36.dp),
                placeholder = { Text("Search products...") },
                shape = RoundedCornerShape(26.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    Row {
                        Icon(Icons.Default.Mic, contentDescription = "Voice", modifier = Modifier.padding(end = 8.dp))
                        Icon(Icons.Default.CameraAlt, contentDescription = "Camera", modifier = Modifier.padding(end = 12.dp))
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        
        if (isSearching) {
            val products = listOf(
                JFYProduct("s2", "Pro Wireless Earbuds", R.drawable.img_product_headphones, "৳1,250", "৳2,000", "-38%", 4.8f, 850)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(products) { product ->
                    ProductCard(
                        id = product.id,
                        title = product.title,
                        price = product.price,
                        oldPrice = product.oldPrice,
                        imageRes = product.imageRes,
                        discount = product.discount,
                        rating = product.rating,
                        soldCount = product.soldCount,
                        isCircleDeal = product.isCircleDeal,
                        onNavigateToProduct = onNavigateToProduct,
                        modifier = Modifier.fillMaxWidth().aspectRatio(0.6f)
                    )
                }
            }
        } else {
            // Search Content
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Recent Searches", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        TextButton(onClick = {}) { Text("Clear All", color = MaterialTheme.colorScheme.primary) }
                    }
                }
                
                val recents = listOf("Premium Sneakers", "Wireless Headphones", "Men's Watch")
                items(recents) { query ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.History, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(query, modifier = Modifier.weight(1f))
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Fill", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Trending Searches", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp))
                }
                
                val trending = listOf("Smart Watches", "Running Shoes", "Winter Jackets", "Gaming Laptops")
                items(trending) { query ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(query, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
