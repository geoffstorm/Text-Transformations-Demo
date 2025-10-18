package dev.gstorm.texttransformationsdemo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.gstorm.texttransformationsdemo.ui.navigation.AppNavKeys
import dev.gstorm.texttransformationsdemo.ui.navigation.DESTINATIONS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToScreen: (AppNavKeys) -> Unit
) {
    // select state or value based demos
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Text Transformation Demo") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            ) {
                itemsIndexed(DESTINATIONS) { index, item ->
                    DemoRow(
                        item = item,
                        onRowSelected = { onNavigateToScreen(item) }
                    )
                    if (index < DESTINATIONS.lastIndex) {
                        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun DemoRow(
    item: AppNavKeys,
    onRowSelected: () -> Unit
) {
    Card(
        onClick = onRowSelected,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                text = item.description,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}