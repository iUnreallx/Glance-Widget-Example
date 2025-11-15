package com.unreallx.widgetforge

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.unreallx.widgetforge.ui.theme.WidgetForgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WidgetForgeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AddWidget(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AddWidget(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello $name!")

        Button(onClick = {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val provider = ComponentName(context, CounterWidgetReceiver::class.java)

            val supported = appWidgetManager.isRequestPinAppWidgetSupported

            val result = if (supported) {
                appWidgetManager.requestPinAppWidget(provider, null, null)
            } else {
                false
            }

            if (!result) {
                Toast.makeText(
                    context,
                    "Add widget: Desktop -> Widget",
                    Toast.LENGTH_LONG
                ).show()
            }
        }) {
            Text("Add Widget to Home Screen")
        }
    }
}

