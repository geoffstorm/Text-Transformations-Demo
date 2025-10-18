package dev.gstorm.texttransformationsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.gstorm.texttransformationsdemo.ui.navigation.AppNavigation
import dev.gstorm.texttransformationsdemo.ui.theme.TextTransformationsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextTransformationsDemoTheme {
                AppNavigation()
            }
        }
    }
}