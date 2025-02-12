package com.example.notele.presentation.views.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit,
    callNavigateBack: Boolean = false
){

    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        modifier = Modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (callNavigateBack)
            IconButton(onClick = navigateUp ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Black)
    )
}