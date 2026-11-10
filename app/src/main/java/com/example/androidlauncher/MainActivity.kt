package com.example.androidlauncher

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LauncherScreen()
                }
            }
        }
    }
}

@Composable
fun LauncherScreen() {
    val context = LocalContext.current
    var limboInstalled by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("Проверка...") }

    // Пакет Limbo PC Emulator
    val limboPackage = "com.limbo.emu.main"

    fun checkLimbo() {
        limboInstalled = try {
            context.packageManager.getPackageInfo(limboPackage, 0)
            true
        } catch (e: Exception) {
            false
        }
        status = if (limboInstalled)
            "Limbo установлен ✅"
        else
            "Limbo не найден. Установите из Google Play"
    }

    LaunchedEffect(Unit) { checkLimbo() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.height(40.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = Color(0xFF2196F3)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Android Launcher",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            Text(
                status,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { checkLimbo() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Filled.Search, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Проверить Limbo")
            }

            OutlinedButton(
                onClick = {
                    try {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$limboPackage")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$limboPackage")
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Filled.Download, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Установить Limbo")
            }

            Button(
                onClick = {
                    try {
                        val intent = context.packageManager
                            .getLaunchIntentForPackage(limboPackage)
                        if (intent != null) {
                            context.startActivity(intent)
                            status = "Открываю Limbo..."
                        } else {
                            status = "Не удалось запустить Limbo"
                        }
                    } catch (e: Exception) {
                        status = "Ошибка: ${e.message}"
                    }
                },
                enabled = limboInstalled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Запустить эмулятор")
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}