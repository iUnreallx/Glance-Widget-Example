package com.unreallx.widgetforge

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

import androidx.glance.Button
import androidx.glance.appwidget.cornerRadius
import androidx.datastore.preferences.core.edit

private val COUNT_KEY = intPreferencesKey("count")

class MyGlanceWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {

            val prefs = currentState<Preferences>()
            val count = prefs[COUNT_KEY] ?: 0

            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ColorProvider(Color.DarkGray))
                    .cornerRadius(16),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = count.toString(),
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = (24.sp)
                    ),
                    modifier = GlanceModifier.padding(bottom = 8)
                )

                Button(
                    text = "Add",
                    onClick = actionRunCallback<IncrementAction>(),
                    modifier = GlanceModifier.cornerRadius(12)
                )
            }
        }
    }
}

class IncrementAction : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
            val currentCount = prefs[COUNT_KEY] ?: 0

            prefs.toMutablePreferences().apply {
                this[COUNT_KEY] = currentCount + 1
            }
        }

        MyGlanceWidget().update(context, glanceId)
    }
}
