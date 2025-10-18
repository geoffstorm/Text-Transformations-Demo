package dev.gstorm.texttransformationsdemo.ui.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import dev.gstorm.texttransformationsdemo.ui.BaseScreen
import dev.gstorm.texttransformationsdemo.ui.DemoTextField
import kotlin.math.max

@Composable
fun NumberInputDemoScreen(
    onNavigateUp: () -> Unit
) {
    BaseScreen(
        title = "Number Input Demo",
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
        onValueChanged = {
            if (it.isDigitsOnly()) {
                text = if (it.length < 3) {
                    it
                } else {
                    it.substring(it.length - 2, it.length)
                }
            }
        },
        visualTransformation = VisualTransformation {
            val requiredPadding = max(2 - it.text.length, 0)
            TransformedText(
                text = buildAnnotatedString {
                    repeat(requiredPadding) {
                        append('0')
                    }
                    append(it.text)
                },
                offsetMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        return offset + requiredPadding
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        return max(offset - requiredPadding, 0)
                    }
                }
            )
        },
        keyboardType = KeyboardType.Number
    )
}

@Composable
private fun StateBasedInputDemo() {
    val state = rememberTextFieldState("00")
    DemoTextField(
        state = state,
        inputTransformation = InputTransformation {
            placeCursorAtEnd()
            if (length < 2) insert(0, "0")
            if (length > 2) delete(0, length - 2)
            if (!asCharSequence().isDigitsOnly() || length > 2) {
                revertAllChanges()
            }
        },
        keyboardType = KeyboardType.Number
    )
}

@Composable
private fun StateBasedOutputDemo() {
    val state = rememberTextFieldState("0")
    DemoTextField(
        state = state,
        outputTransformation = OutputTransformation {
            val requiredPadding = max(2 - length, 0)
            if (length < 2) {
                repeat(requiredPadding) {
                    insert(0, "0")
                }
            }
            if (length > 2) {
                delete(0, length - 2)
            }
        },
        keyboardType = KeyboardType.Number
    )
}