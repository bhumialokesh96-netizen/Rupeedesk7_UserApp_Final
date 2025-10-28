package com.rupeedesk7.smsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rupeedesk7.smsapp.ui.theme.TopBarGradient
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding

@Composable
fun TopBarWithGradient(title: String, actions: @Composable RowScope.() -> Unit = {}) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .background(TopBarGradient)
            .height(64.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)) {
            Text(text = title, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onPrimary)
        }
        Row(modifier = Modifier.weight(1f)) {} // spacer
        actions()
    }
}