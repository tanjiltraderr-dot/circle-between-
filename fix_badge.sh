sed -i '/Column(modifier = Modifier.padding(12.dp)) {/a\
                if (isCircleDeal) {\
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {\
                        Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color(0xFFE53935), modifier = Modifier.size(14.dp))\
                        Spacer(modifier = Modifier.width(2.dp))\
                        Text("Circle Deals", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE53935))\
                    }\
                }' app/src/main/java/com/example/ui/screens/HomeScreen.kt
