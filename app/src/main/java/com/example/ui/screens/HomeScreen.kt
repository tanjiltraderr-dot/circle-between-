package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.basicMarquee
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import com.example.R

@Composable
fun Modifier.cleanClickable(onClick: () -> Unit): Modifier {
    return this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}

@Composable
fun AnimatedSearchPlaceholder() {
    val placeholders = androidx.compose.runtime.remember {
        listOf(
            "Search in Circle Bazar",
            "Search Apple iPhone 14 128GB",
            "Search Men's Running Shoes",
            "Search Haylou Smart Watch",
            "Search Pro Wireless Earbuds",
            "Search Women's Long Dress",
            "Search Luxury Perfume",
            "Search Women's Hand Bag",
            "Search NAVIFORCE Watch"
        )
    }
    var currentIndex by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(0) }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(2500)
            currentIndex = (currentIndex + 1) % placeholders.size
        }
    }

    AnimatedContent(
        targetState = placeholders[currentIndex],
        transitionSpec = {
            (slideInVertically { height -> height } + fadeIn(tween(300)))
                .togetherWith(slideOutVertically { height -> -height } + fadeOut(tween(300)))
        },
        label = "SearchPlaceholderAnimation"
    ) { text ->
        Text(
            text = text,
            fontSize = 13.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, 
    unreadNotificationCount: Int = 0,
    onNavigateToSearch: () -> Unit, 
    onNavigateToProduct: (String) -> Unit, 
    onNavigateToCategory: () -> Unit = {},
    onNavigateToCircleDeals: (String?) -> Unit = {},
    onNavigateToNotification: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val isScrolled = scrollState.value > 40
    
    Box(modifier = modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
            HeroBanner()
            CircleDealsSection(onNavigateToProduct = onNavigateToProduct, onNavigateToCircleDeals = onNavigateToCircleDeals)
            CategorySection(onNavigateToCategory = onNavigateToCategory)
            JustForYouSection(onNavigateToProduct = onNavigateToProduct)
            Spacer(modifier = Modifier.height(24.dp))
        }
        
        // Floating Top Bars:
        // 1. When NOT scrolled (<= 40px): Search bar floating over top of Hero Banner
        // 2. When scrolled (> 40px): Full solid white header with Logo + Search Bar + Notification slides in
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            AnimatedVisibility(
                visible = !isScrolled,
                enter = fadeIn(tween(250)),
                exit = fadeOut(tween(250))
            ) {
                BannerOverlayTopBar(
                    onNavigateToSearch = onNavigateToSearch,
                    onNavigateToNotification = onNavigateToNotification,
                    unreadNotificationCount = unreadNotificationCount
                )
            }
            
            AnimatedVisibility(
                visible = isScrolled,
                enter = slideInVertically(tween(300)) { -it } + fadeIn(tween(300)),
                exit = slideOutVertically(tween(300)) { -it } + fadeOut(tween(300))
            ) {
                HomeHeader(
                    onNavigateToSearch = onNavigateToSearch,
                    onNavigateToNotification = onNavigateToNotification,
                    unreadNotificationCount = unreadNotificationCount
                )
            }
        }
    }
}

@Composable
fun BannerOverlayTopBar(
    onNavigateToSearch: () -> Unit,
    onNavigateToNotification: () -> Unit = {},
    unreadNotificationCount: Int = 0
) {
    val topInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topInset + 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .shadow(6.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .cleanClickable { onNavigateToSearch() },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Search, 
                    contentDescription = "Search", 
                    tint = Color.Gray, 
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    AnimatedSearchPlaceholder()
                }
                Icon(
                    Icons.Default.CameraAlt, 
                    contentDescription = "Image Search", 
                    tint = Color.Gray, 
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    Icons.Default.Mic, 
                    contentDescription = "Voice Search", 
                    tint = Color.Gray, 
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun HomeHeader(
    onNavigateToSearch: () -> Unit,
    onNavigateToNotification: () -> Unit = {},
    unreadNotificationCount: Int = 0
) {
    val topInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(Color.White)
            .padding(top = topInset + 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo
        Row(
            modifier = Modifier.padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingBag,
                contentDescription = "Logo",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(
                    text = "CIRCLE",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    lineHeight = 11.sp
                )
                Text(
                    text = "BAZAR",
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    lineHeight = 11.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(38.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFF5F5F5))
                .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .clickable { onNavigateToSearch() },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    AnimatedSearchPlaceholder()
                }
                Icon(
                    Icons.Default.CameraAlt,
                    contentDescription = "Image Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    Icons.Default.Mic,
                    contentDescription = "Voice Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        IconButton(onClick = onNavigateToNotification, modifier = Modifier.size(32.dp)) {
            BadgedBox(
                badge = {
                    if (unreadNotificationCount > 0) {
                        Badge(
                            containerColor = Color(0xFF388E3C),
                            contentColor = Color.White,
                            modifier = Modifier.offset(x = (-4).dp, y = 4.dp).size(16.dp)
                        ) {
                            Text(unreadNotificationCount.toString(), color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun HeroBanner() {
    val pagerState = rememberPagerState(pageCount = { 4 })
    
    LaunchedEffect(Unit) {
        while(true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % 4
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val imageRes = when(page) {
                    0 -> R.drawable.img_hero_banner_new
                    1 -> R.drawable.img_hero_banner
                    else -> R.drawable.img_hero_banner_new
                }
                
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Promo Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 24.dp, end = 24.dp, bottom = 20.dp, top = 74.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    val subtitle = when(page) {
                        0 -> "Special Offer"
                        1 -> "New Arrival"
                        else -> "Limited Time"
                    }
                    
                    val title = when(page) {
                        0 -> "MEGA SALE"
                        1 -> "TRENDING NOW"
                        else -> "FLASH DEAL"
                    }
                    Text(
                        text = subtitle,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 28.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { /* Shop Now */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Shop Now", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
        
        // Indicator Dots
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 24.dp else 8.dp, 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                )
            }
        }
    }
}

@Composable
fun CircleDealsSection(onNavigateToProduct: (String) -> Unit, onNavigateToCircleDeals: (String?) -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val cardWidth = screenWidth * 0.23f
    val cardHeight = screenHeight * 0.20f

    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    androidx.compose.runtime.LaunchedEffect(Unit) {
        while(true) {
            kotlinx.coroutines.delay(4000)
            if (listState.layoutInfo.totalItemsCount > 0) {
                val nextIndex = (listState.firstVisibleItemIndex + 1) % listState.layoutInfo.totalItemsCount
                listState.animateScrollToItem(nextIndex)
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.FlashOn,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Circle Deals",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.cleanClickable { onNavigateToCircleDeals(null) }
            ) {
                Text("Shop More", color = Color(0xFF388E3C), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF388E3C), modifier = Modifier.size(16.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(6.dp))
        
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TimeBox("12", "HRS")
            TimeBox("48", "MINS")
            TimeBox("36", "SECS")
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val deals = listOf(
                Deal("Haylou Solar Lite Smart Watch", "৳2,450", "৳4,450", R.drawable.img_product_watch, "-45%", 0.1f, "Only 10 Left"),
                Deal("Pro Wireless Earbuds", "৳1,250", "৳2,000", R.drawable.img_product_headphones, "-38%", 0.05f, "Only 5 Left"),
                Deal("Women's Premium Hand Bag", "৳1,290", "৳3,150", R.drawable.img_product_shoes, "-40%", 0.08f, "Only 8 Left"),
                Deal("Luxury Perfume For Women", "৳1,450", "৳2,900", R.drawable.img_product_watch, "-50%", 0.15f, "Only 7 Left")
            )
            items(deals) { deal ->
                CircleDealProductCard(
                    title = deal.title,
                    price = deal.price,
                    oldPrice = deal.oldPrice,
                    imageRes = deal.imageRes,
                    discount = deal.discount,
                    progress = deal.progress,
                    leftText = deal.leftText,
                    onNavigateToProduct = { onNavigateToCircleDeals(deal.title) },
                    modifier = Modifier.width(cardWidth)
                )
            }
        }
    }
}

data class Deal(
    val title: String, val price: String, val oldPrice: String,
    val imageRes: Int, val discount: String, val progress: Float, val leftText: String
)

@Composable
fun TimeBox(number: String, label: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFFFFD54F))
            .width(36.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = number, color = Color.Black, fontWeight = FontWeight.Black, fontSize = 13.sp, lineHeight = 13.sp)
        Spacer(modifier = Modifier.height(1.dp))
        Text(text = label, color = Color.Black, fontSize = 8.sp, fontWeight = FontWeight.Bold, lineHeight = 8.sp)
    }
}

@Composable
fun CategorySection(onNavigateToCategory: () -> Unit = {}) {
    val categories = listOf<Triple<String, ImageVector, Color>>(
        Triple("Electronics", Icons.Outlined.Headphones, Color(0xFF1976D2)),
        Triple("Fashion", Icons.Outlined.Checkroom, Color(0xFF43A047)),
        Triple("Home & Living", Icons.Outlined.Weekend, Color(0xFFF57C00)),
        Triple("Beauty", Icons.Outlined.Face, Color(0xFFD81B60)),
        Triple("Groceries", Icons.Outlined.ShoppingBasket, Color(0xFF7CB342)),
        Triple("Mobiles", Icons.Outlined.PhoneIphone, Color(0xFF1E88E5)),
        Triple("Appliances", Icons.Outlined.LocalLaundryService, Color(0xFF546E7A)),
        Triple("Baby & Kids", Icons.Outlined.ChildCare, Color(0xFF8D6E63)),
        Triple("Sports", Icons.Outlined.SportsSoccer, Color(0xFF212121)),
        Triple("Automotive", Icons.Outlined.DirectionsCar, Color(0xFF455A64)),
        Triple("Books", Icons.Outlined.MenuBook, Color(0xFF5E35B1)),
        Triple("More", Icons.Outlined.GridView, Color(0xFF388E3C))
    )
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(vertical = 16.dp, horizontal = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            categories.chunked(6).forEach { rowCategories ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowCategories.forEach { (name, icon, color) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .cleanClickable { if (name == "More") onNavigateToCategory() }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .border(1.dp, Color.LightGray.copy(alpha = 0.3f), CircleShape)
                                    .shadow(elevation = 1.dp, shape = CircleShape, spotColor = Color.Red.copy(alpha = 0.05f))
                                    .clip(CircleShape)
                                    .background(if (name == "More") color else Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = name.replace("\n", " "),
                                    tint = if (name == "More") Color.White else color,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = name,
                                fontSize = 10.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Clip,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                lineHeight = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

data class JFYProduct(
    val id: String,
    val title: String,
    val imageRes: Int,
    val price: String,
    val oldPrice: String?,
    val discount: String?,
    val rating: Float,
    val soldCount: Int,
    val isCircleDeal: Boolean = false
)

@Composable
fun JustForYouSection(onNavigateToProduct: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Just For You",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = Color.Black
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.cleanClickable { }
            ) {
                Text("View All", color = Color(0xFF388E3C), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF388E3C), modifier = Modifier.size(16.dp))
            }
        }
        
        // Mock Grid since LazyVerticalGrid inside Scrollable Column is tricky without fixed height
        val products = listOf(
            JFYProduct("j1", "Apple iPhone 14 128GB", R.drawable.img_product_headphones, "৳89,990", "৳112,000", "-20%", 4.8f, 120, isCircleDeal = true),
            JFYProduct("j2", "Men's Running Shoes", R.drawable.img_product_shoes, "৳2,590", "৳3,700", "-30%", 4.6f, 85),
            JFYProduct("j3", "NAVIFORCE Men's Chronograph Watch", R.drawable.img_product_watch, "৳2,990", "৳3,990", "-25%", 4.7f, 65),
            JFYProduct("j4", "Stylish Women's Long Dress", R.drawable.img_product_headphones, "৳1,690", "৳2,600", "-35%", 4.5f, 43)
        )
        
        products.chunked(2).forEach { rowProducts ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowProducts.forEach { product ->
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
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun BenefitsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BenefitItem(Icons.Outlined.LocalShipping, "Free\nDelivery")
        BenefitItem(Icons.Outlined.CheckCircle, "Best\nQuality")
        BenefitItem(Icons.Outlined.Security, "Secure\nPayment")
        BenefitItem(Icons.Outlined.Payment, "Easy\nReturns")
    }
}

@Composable
fun BenefitItem(icon: ImageVector, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontSize = 11.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center, lineHeight = 14.sp)
    }
}

@Composable
fun ProductCard(
    id: String,
    title: String,
    price: String,
    oldPrice: String?,
    imageRes: Int,
    discount: String?,
    rating: Float,
    soldCount: Int,
    onNavigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    isCircleDeal: Boolean = false,
    progress: Float? = null,
    leftText: String? = null,
    ratingCount: Int = 120
) {
    var isFavorite by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    Card(
        modifier = modifier
            .border(1.dp, Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(12.dp), spotColor = Color.Gray.copy(alpha = 0.1f))
            .clip(RoundedCornerShape(12.dp))
            .cleanClickable { onNavigateToProduct(id) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                
                // Favorite Icon with background
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(28.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(if (isFavorite) Color(0xFFE53935) else Color.White.copy(alpha = 0.95f))
                        .cleanClickable { isFavorite = !isFavorite },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isFavorite) Color.White else Color(0xFF757575),
                        modifier = Modifier.size(16.dp)
                    )
                }
                
                if (discount != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp)
                            .background(Color(0xFFF44336), RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                            .padding(horizontal = 4.dp, vertical = 0.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = discount,
                            color = Color.White, 
                            fontSize = 8.sp, 
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                if (isCircleDeal) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(6.dp)
                            .background(Color(0xFFE53935), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Circle Deals",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            
            Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "$rating ($ratingCount)", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF388E3C)
                    )
                    if (oldPrice != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = oldPrice,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
                Text(text = "$soldCount Sold", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}

@Composable
fun CircleDealProductCard(
    title: String,
    price: String,
    oldPrice: String,
    imageRes: Int,
    discount: String,
    progress: Float,
    leftText: String,
    onNavigateToProduct: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFavorite by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    Card(
        modifier = modifier
            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp), spotColor = Color.Gray.copy(alpha = 0.1f))
            .clip(RoundedCornerShape(8.dp))
            .cleanClickable { onNavigateToProduct() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f).background(Color.White)) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                
                // Favorite Icon with background
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(26.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(if (isFavorite) Color(0xFFE53935) else Color.White.copy(alpha = 0.95f))
                        .cleanClickable { isFavorite = !isFavorite },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isFavorite) Color.White else Color(0xFF757575),
                        modifier = Modifier.size(15.dp)
                    )
                }
                
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 8.dp)
                        .background(Color(0xFFE53935), RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                        .padding(horizontal = 4.dp, vertical = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = discount, 
                        color = Color.White, 
                        fontSize = 8.sp, 
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF388E3C)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = oldPrice,
                        fontSize = 10.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough,
                        maxLines = 1
                    )
                }
                
                Spacer(modifier = Modifier.height(6.dp))
                
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color = Color(0xFF4CAF50),
                    trackColor = Color(0xFFE8F5E9),
                )
                
                Text(
                    text = leftText,
                    color = Color(0xFF388E3C),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 4.dp)
                )
            }
        }
    }
}
