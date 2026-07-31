import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

# Add animation imports after package line if not present
if "import androidx.compose.animation.AnimatedContent" not in content:
    content = content.replace(
        "package com.example.ui.screens",
        "package com.example.ui.screens\n\n"
        "import androidx.compose.animation.AnimatedContent\n"
        "import androidx.compose.animation.AnimatedVisibility\n"
        "import androidx.compose.animation.fadeIn\n"
        "import androidx.compose.animation.fadeOut\n"
        "import androidx.compose.animation.slideInVertically\n"
        "import androidx.compose.animation.slideOutVertically\n"
        "import androidx.compose.animation.togetherWith\n"
        "import androidx.compose.animation.core.tween\n"
    )

pattern = re.compile(r"@Composable\s*fun HomeScreen\(.*?(?=@Composable\s*fun CircleDealsSection)", re.DOTALL)

replacement = """@Composable
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .shadow(4.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
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
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    AnimatedSearchPlaceholder()
                }
                Icon(
                    Icons.Default.CameraAlt, 
                    contentDescription = "Image Search", 
                    tint = Color.Gray, 
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.Mic, 
                    contentDescription = "Voice Search", 
                    tint = Color.Gray, 
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .size(40.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onNavigateToNotification() },
            contentAlignment = Alignment.Center
        ) {
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
                    modifier = Modifier.size(24.dp)
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
    Box(modifier = Modifier.fillMaxWidth().height(235.dp)) {
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
                        .padding(start = 24.dp, end = 24.dp, bottom = 20.dp, top = 56.dp),
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

"""

new_content, count = re.subn(pattern, replacement, content, count=1)
if count > 0:
    with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
        f.write(new_content)
    print("Successfully updated HomeScreen.kt")
else:
    print("Failed to replace HomeScreen pattern")
