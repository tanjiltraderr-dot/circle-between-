sed -i '/verticalAlignment = Alignment.CenterVertically\n        ) {/a\
            Row(verticalAlignment = Alignment.CenterVertically) {\
                Icon(\
                    Icons.Default.FlashOn,\
                    contentDescription = null,\
                    tint = Color(0xFFFFC107),\
                    modifier = Modifier.size(20.dp)\
                )\
                Spacer(modifier = Modifier.width(4.dp))\
                Text(\
                    text = "Circle Deals",\
                    fontWeight = FontWeight.Black,\
                    fontSize = 16.sp,\
                    color = Color.Black\
                )\
            }' app/src/main/java/com/example/ui/screens/HomeScreen.kt
