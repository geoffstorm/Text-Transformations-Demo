package dev.gstorm.texttransformationsdemo.ui.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.gstorm.texttransformationsdemo.ui.BaseScreen
import dev.gstorm.texttransformationsdemo.ui.DemoTextField

@Composable
fun CaesarCipherDemoScreen(
    onNavigateUp: () -> Unit
) {
    var shiftText by rememberSaveable { mutableStateOf("1") }
    var shift by remember(shiftText) {
        mutableIntStateOf(shiftText.toIntOrNull() ?: 0)
    }

    BaseScreen(
        title = "Caesar Cipher Demo",
        onNavigateUp = onNavigateUp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Shift value") },
                value = shiftText,
                onValueChange = {
                    shiftText = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(Modifier.height(16.dp))
            ValueBasedDemo(shift)
            Spacer(Modifier.height(16.dp))
            StateBasedOutputDemo(shift)
        }
    }
}

@Composable
private fun ValueBasedDemo(shift: Int) {
    var text by rememberSaveable { mutableStateOf("") }
    DemoTextField(
        text = text,
        onValueChanged = {
            text = it.lowercase()
        },
        visualTransformation = VisualTransformation {
            val shifted = buildString {
                it.text.forEach { char ->
                    if (char.isLetter()) {
                        val offset = char - 'a'
                        val newChar = 'a' + (offset + shift) % 26
                        append(newChar)
                    } else {
                        append(char)
                    }
                }
            }
            TransformedText(AnnotatedString(shifted), OffsetMapping.Identity)
        }
    )
}

@Composable
private fun StateBasedOutputDemo(shift: Int) {
    val state = rememberTextFieldState("")
    DemoTextField(
        state = state,
        inputTransformation = InputTransformation {
            val original = asCharSequence().toString()
            val lowercased = original.lowercase()
            if (lowercased != original) {
                replace(0, length, lowercased)
            }
        },
        outputTransformation = OutputTransformation {
            val shifted = buildString {
                asCharSequence().forEach { char ->
                    if (char.isLetter()) {
                        val offset = char - 'a'
                        val newChar = 'a' + (offset + shift) % 26
                        append(newChar)
                    } else {
                        append(char)
                    }
                }
            }
            replace(0, length, shifted)
        }
    )
}