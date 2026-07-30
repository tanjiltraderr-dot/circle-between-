import re

with open('app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

pattern = re.compile(r'                if \(isCircleDeal\) \{\s*Row\(verticalAlignment = Alignment\.CenterVertically, modifier = Modifier\.padding\(bottom = 4\.dp\)\) \{\s*Icon\(Icons\.Default\.FlashOn, contentDescription = null, tint = Color\(0xFFE53935\), modifier = Modifier\.size\(14\.dp\)\)\s*Spacer\(modifier = Modifier\.width\(2\.dp\)\)\s*Text\("Circle Deals", fontSize = 10\.sp, fontWeight = FontWeight\.Bold, color = Color\(0xFFE53935\)\)\s*\}\s*\}')

replacement = '''                Box(modifier = Modifier.fillMaxWidth().height(18.dp)) {
                    if (isCircleDeal) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color(0xFFE53935), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("Circle Deals", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE53935))
                        }
                    }
                }'''

content = re.sub(pattern, replacement, content, count=1)

with open('app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)
