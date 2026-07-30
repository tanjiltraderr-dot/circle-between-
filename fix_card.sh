sed -i '720,845c\
fun CircleDealProductCard(\
    title: String,\
    price: String,\
    oldPrice: String,\
    imageRes: Int,\
    discount: String,\
    progress: Float,\
    leftText: String,\
    onNavigateToProduct: () -> Unit,\
    modifier: Modifier = Modifier\
) {\
    Card(\
        modifier = modifier\
            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))\
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp), spotColor = Color.Gray.copy(alpha = 0.1f))\
            .clip(RoundedCornerShape(8.dp))\
            .clickable { onNavigateToProduct() },\
        colors = CardDefaults.cardColors(containerColor = Color.White),\
        shape = RoundedCornerShape(8.dp)\
    ) {\
        Column(modifier = Modifier.fillMaxSize()) {\
            Box(\
                modifier = Modifier\
                    .fillMaxWidth()\
                    .aspectRatio(1f)\
            ) {\
                Image(\
                    painter = painterResource(id = imageRes),\
                    contentDescription = title,\
                    modifier = Modifier.fillMaxSize().padding(8.dp),\
                    contentScale = ContentScale.Fit,\
                    alignment = Alignment.Center\
                )\
                \
                // Discount Badge - Top Left\
                Box(\
                    modifier = Modifier\
                        .align(Alignment.TopStart)\
                        .background(Color(0xFFE53935), RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp))\
                        .padding(horizontal = 6.dp, vertical = 4.dp),\
                    contentAlignment = Alignment.Center\
                ) {\
                    Text(\
                        text = discount, \
                        color = Color.White, \
                        fontSize = 10.sp, \
                        fontWeight = FontWeight.Bold\
                    )\
                }\
                \
                // Favorite Icon\
                Icon(\
                    Icons.Outlined.FavoriteBorder, \
                    contentDescription = "Wishlist", \
                    tint = Color.Gray, \
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).size(16.dp)\
                )\
            }\
            \
            Column(\
                modifier = Modifier\
                    .fillMaxWidth()\
                    .background(Color.White)\
                    .padding(8.dp),\
                verticalArrangement = Arrangement.Bottom\
            ) {\
                Box(\
                    modifier = Modifier\
                        .clip(RoundedCornerShape(4.dp))\
                        .background(Color(0xFFE8F5E9))\
                        .padding(horizontal = 4.dp, vertical = 2.dp),\
                    contentAlignment = Alignment.Center\
                ) {\
                    Text(\
                        text = "CIRCLE DEALS",\
                        color = Color(0xFF43A047),\
                        fontSize = 8.sp,\
                        fontWeight = FontWeight.Bold\
                    )\
                }\
                Spacer(modifier = Modifier.height(4.dp))\
                \
                Text(\
                    text = title,\
                    fontSize = 12.sp,\
                    fontWeight = FontWeight.Medium,\
                    color = Color.Black,\
                    maxLines = 2,\
                    lineHeight = 14.sp,\
                    overflow = TextOverflow.Ellipsis\
                )\
                \
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {\
                    Text(\
                        text = price,\
                        fontSize = 14.sp,\
                        fontWeight = FontWeight.Bold,\
                        color = Color(0xFF388E3C)\
                    )\
                    Spacer(modifier = Modifier.width(4.dp))\
                    Text(\
                        text = oldPrice,\
                        fontSize = 10.sp,\
                        color = Color.Gray,\
                        textDecoration = TextDecoration.LineThrough,\
                        maxLines = 1\
                    )\
                }\
                \
                Spacer(modifier = Modifier.height(6.dp))\
                \
                // Progress Bar\
                LinearProgressIndicator(\
                    progress = { progress },\
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),\
                    color = Color(0xFFE53935),\
                    trackColor = Color(0xFFFFEBEE),\
                )\
                \
                Text(\
                    text = leftText,\
                    color = Color(0xFFE53935),\
                    fontSize = 10.sp,\
                    fontWeight = FontWeight.Medium,\
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 4.dp)\
                )\
            }\
        }\
    }\
}' app/src/main/java/com/example/ui/screens/HomeScreen.kt
