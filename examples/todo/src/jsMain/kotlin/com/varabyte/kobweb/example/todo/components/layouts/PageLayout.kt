package com.varabyte.kobweb.example.todo.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.fillMaxSize
import com.varabyte.kobweb.compose.ui.minWidth
import org.jetbrains.compose.web.css.px

@Composable
fun PageLayout(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().minWidth(600.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        content()
    }
}
