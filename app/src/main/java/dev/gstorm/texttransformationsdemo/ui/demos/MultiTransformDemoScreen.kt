package dev.gstorm.texttransformationsdemo.ui.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.gstorm.texttransformationsdemo.ui.BaseScreen

@Composable
fun MultiTransformDemoScreen(
    onNavigateUp: () -> Unit
) {
    val state = rememberTextFieldState("")
    BaseScreen(
        title = "Multi Transform Demo",
        onNavigateUp = onNavigateUp
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            label = { Text("Multi Transform Demo") },
            supportingText = { Text(state.text.toString()) },
            state = state,
            lineLimits = TextFieldLineLimits.SingleLine,
            inputTransformation = InputTransformation.maxLength(10)
                .then(AlphaOnlyInputTransformation()),
            outputTransformation = CapitalizationOutputTransformation()
        )
    }
}

private class AlphaOnlyInputTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (!asCharSequence().all { it.isLetter() }) {
            revertAllChanges()
        }
    }
}

private class CapitalizationOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        replace(0, length, asCharSequence().toString().uppercase())
    }
}