package dev.gstorm.texttransformationsdemo.ui.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.gstorm.texttransformationsdemo.ui.BaseScreen
import dev.gstorm.texttransformationsdemo.ui.DemoTextField

@Composable
fun PasswordDemoScreen(
    onNavigateUp: () -> Unit
) {
    BaseScreen(
        title = "Password Demo",
        onNavigateUp = onNavigateUp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            ValueBasedDemo()
            Spacer(Modifier.height(16.dp))
            StateBasedInputDemo()
            Spacer(Modifier.height(16.dp))
            StateBasedOutputDemo()
        }
    }
}

@Composable
private fun ValueBasedDemo() {
    var text by rememberSaveable { mutableStateOf("") }
    DemoTextField(
        text = text,
        onValueChanged = { text = it },
        visualTransformation = VisualTransformation {
            TransformedText(
                AnnotatedString("*".repeat(it.text.length)),
                OffsetMapping.Identity
            )
        }
    )
}

@Composable
private fun StateBasedInputDemo() {
    val state = rememberTextFieldState("")
    DemoTextField(
        state = state,
        inputTransformation = InputTransformation {
            replace(0, length, "*".repeat(length))
        }
    )
}

@Composable
private fun StateBasedOutputDemo() {
    val state = rememberTextFieldState("")
    DemoTextField(
        state = state,
        outputTransformation = OutputTransformation {
            replace(0, length, "*".repeat(length))
        }
    )
}