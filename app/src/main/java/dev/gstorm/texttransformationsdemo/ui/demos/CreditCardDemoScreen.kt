package dev.gstorm.texttransformationsdemo.ui.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
fun CreditCardDemoScreen(
    onNavigateUp: () -> Unit
) {
    BaseScreen(
        title = "Credit Card Demo",
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
            if (it.length <= 16) {
                text = it
            }
        },
        visualTransformation = VisualTransformation {
            val trimmed = if (text.length > 16) text.substring(0..15) else text
            var out = ""
            for (i in trimmed.indices) {
                out += trimmed[i]
                if (i % 4 == 3 && i != 15) {
                    out += "-"
                }
            }
            val creditCardOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 7) return offset + 1
                    if (offset <= 11) return offset + 2
                    if (offset <= 16) return offset + 3
                    return 19
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 4) return offset
                    if (offset <= 9) return offset - 1
                    if (offset <= 14) return offset - 2
                    if (offset <= 19) return offset - 3
                    return 16
                }
            }
            TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
        },
        keyboardType = KeyboardType.Number
    )
}

@Composable
private fun StateBasedInputDemo() {
    val state = rememberTextFieldState("")
    DemoTextField(
        state = state,
        inputTransformation = InputTransformation {
            if (length > 19) revertAllChanges()
            if (length == 4) append("-")
            if (length == 9) append("-")
            if (length == 14) append("-")
            placeCursorAtEnd()
        },
        keyboardType = KeyboardType.Number
    )
}

@Composable
private fun StateBasedOutputDemo() {
    val state = rememberTextFieldState("")
    DemoTextField(
        state = state,
        outputTransformation = OutputTransformation {
            if (length > 4) insert(4, "-")
            if (length > 9) insert(9, "-")
            if (length > 14) insert(14, "-")
        },
        keyboardType = KeyboardType.Number
    )
}