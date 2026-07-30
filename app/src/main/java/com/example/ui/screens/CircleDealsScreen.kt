package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CircleDealsScreen(onNavigateBack: () -> Unit, onNavigateToProduct: (String) -> Unit, selectedDealTitle: String? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Circle Deals", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            
            // Timer header
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ends in: ", fontWeight = FontWeight.Medium, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                TimeBox("12", "HRS")
                Spacer(modifier = Modifier.width(6.dp))
                TimeBox("48", "MINS")
                Spacer(modifier = Modifier.width(6.dp))
                TimeBox("36", "SECS")
            }
            
            val deals = listOf(
                Deal("Haylou Solar Lite Smart Watch", "৳2,450", "৳4,450", R.drawable.img_product_watch, "-45%", 0.1f, "Only 10 Left"),
                Deal("Pro Wireless Earbuds", "৳1,250", "৳2,000", R.drawable.img_product_headphones, "-38%", 0.05f, "Only 5 Left"),
                Deal("Women's Premium Hand Bag", "৳1,290", "৳3,150", R.drawable.img_product_shoes, "-40%", 0.08f, "Only 8 Left"),
                Deal("Luxury Perfume For Women", "৳1,450", "৳2,900", R.drawable.img_product_watch, "-50%", 0.15f, "Only 7 Left"),
                Deal("Smart Fitness Band", "৳950", "৳1,500", R.drawable.img_product_watch, "-35%", 0.12f, "Only 12 Left"),
                Deal("Noise Cancelling Headphones", "৳3,450", "৳5,900", R.drawable.img_product_headphones, "-41%", 0.09f, "Only 4 Left")
            )
            
            val sortedDeals = if (selectedDealTitle != null) {
                deals.sortedByDescending { it.title == selectedDealTitle }
            } else {
                deals
            }
                
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(sortedDeals) { deal ->
                    ProductCard(
                        id = "1",
                        title = deal.title,
                        price = deal.price,
                        oldPrice = deal.oldPrice,
                        imageRes = deal.imageRes,
                        discount = deal.discount,
                        rating = 4.8f,
                        soldCount = 450,
                        isCircleDeal = true,
                        progress = deal.progress,
                        leftText = deal.leftText,
                        onNavigateToProduct = { onNavigateToProduct("1") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
