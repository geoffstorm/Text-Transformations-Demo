package dev.gstorm.texttransformationsdemo.ui.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.insert
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
fun PhoneNumberDemoScreen(
    onNavigateUp: () -> Unit
) {
    BaseScreen(
        title = "Phone Number Demo",
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
            if (it.length <= 10) {
                text = it
            }
        },
        visualTransformation = VisualTransformation {
            val trimmed = if (text.length > 10) text.substring(0..9) else text
            var out = ""
            for (i in trimmed.indices) {
                when (i) {
                    0 -> out += "("
                    3 -> out += ")"
                    6 -> out += "-"
                }
                out += text[i]
            }
            val phoneOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset == 0) return 0
                    if (offset <= 3) return offset + 1
                    if (offset <= 6) return offset + 2
                    if (offset <= 9) return offset + 3
                    return 13
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset == 0) return 0
                    if (offset <= 3) return offset - 1
                    if (offset <= 6) return offset - 2
                    if (offset <= 13) return offset - 3
                    return 10
                }
            }
            TransformedText(AnnotatedString(out), phoneOffsetTranslator)
        },
        keyboardType = KeyboardType.Phone
    )
}

@Composable
private fun StateBasedInputDemo() {
    val state = rememberTextFieldState("")
    DemoTextField(
        state = state,
        inputTransformation = InputTransformation {
            if (length > 13) revertAllChanges()
            if (length > 0 && charAt(0) != '(') insert(0, "(")
            if (length > 4 && charAt(4) != ')') insert(4, ")")
            if (length > 8 && charAt(8) != '-') insert(8, "-")
        },
        keyboardType = KeyboardType.Phone
    )
}

@Composable
private fun StateBasedOutputDemo() {
    val state = rememberTextFieldState("")
    DemoTextField(
        state = state,
        outputTransformation = OutputTransformation {
            if (length > 0) insert(0, "(")
            if (length > 4) insert(4, ")")
            if (length > 8) insert(8, "-")
        },
        keyboardType = KeyboardType.Phone
    )
}