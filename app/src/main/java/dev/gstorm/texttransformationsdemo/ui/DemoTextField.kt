package dev.gstorm.texttransformationsdemo.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun DemoTextField(
    modifier: Modifier = Modifier,
    text: String,
    singleLine: Boolean = true,
    numLines: Int = 2,
    onValueChanged: (String) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Unspecified
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = { Text("Value Based TextField") },
        supportingText = { Text(text) },
        value = text,
        singleLine = singleLine,
        minLines = numLines,
        maxLines = numLines,
        onValueChange = onValueChanged,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun DemoTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardType: KeyboardType = KeyboardType.Unspecified
) {
    val label = StringBuilder().apply {
        if (inputTransformation != null) append("Input")
        if (outputTransformation != null) append("Output")
        if (inputTransformation == null && outputTransformation == null) append("Non")
        append("-transformed State Based TextField")
    }.toString()

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        supportingText = { Text(state.text.toString()) },
        state = state,
        lineLimits = TextFieldLineLimits.SingleLine,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}