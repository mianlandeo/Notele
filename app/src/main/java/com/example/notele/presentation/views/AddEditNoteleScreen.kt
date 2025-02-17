package com.example.notele.presentation.views

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notele.R
import com.example.notele.data.db.model.NoteleModel
import com.example.notele.data.util.Utils
import com.example.notele.presentation.viewmodel.AddEditNotelesEvent
import com.example.notele.presentation.viewmodel.AddEditViewModel
import com.example.notele.presentation.views.components.EditTextVisible
import com.example.notele.presentation.views.components.NavigationTopBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    modifier: Modifier = Modifier,
    vm: AddEditViewModel = hiltViewModel(),
    noteColor: Int,
    navController: NavController,
    callNavigateBack: Boolean = true,
){

    val title = vm.editTitle.value
    val description = vm.editDescription.value
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val animateColor = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else vm.priorityColor.value)
        )
    }

    LaunchedEffect(true) {
        vm.stateEvent.collectLatest { event ->
            when(event) {
                is AddEditViewModel.UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        event.message
                    )
                }
                is AddEditViewModel.UiEvent.SaveNotele -> {
                    navController.navigateUp()
                }
            }
            Log.e("eventevent:", "$event")
        }
    }
    Scaffold (
        modifier = Modifier,
        topBar = {
            NavigationTopBar(
                title = stringResource(R.string.add_note),
                navigateUp = { navController.navigateUp() },
                callNavigateBack = callNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { vm.onEvent(AddEditNotelesEvent.OnSaveItem) },
                contentColor = MaterialTheme.colorScheme.primary,
                elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(12.dp)
            ) {
                Icon(Icons.Rounded.Done, "Save", tint = Color.White)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .background(animateColor.value)
                .fillMaxSize()
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                /* action for each color is what user selection */
                NoteleModel.notelePriority.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (vm.priorityColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    animateColor.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                vm.onEvent(AddEditNotelesEvent.Color(colorInt))
                            }
                        )

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            EditTextVisible(
                text = title.text,
                hint = title.hint,
                onValueText = { vm.onEvent(AddEditNotelesEvent.Title(it)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.labelSmall,
                focusState = { vm.onEvent(AddEditNotelesEvent.TitleChange(it)) },
                hintVisible = title.isHintVisible,
                tag = Utils.TEST_TAG_TITLE,
                keyBoardOption = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                maxLine = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            EditTextVisible(
                modifier = Modifier.fillMaxSize(),
                text = description.text,
                hint = description.hint,
                onValueText = { vm.onEvent(AddEditNotelesEvent.Description(it))},
                textStyle = MaterialTheme.typography.labelSmall,
                focusState = { vm.onEvent(AddEditNotelesEvent.DescriptionChange(it))},
                hintVisible = description.isHintVisible,
                tag = Utils.TEST_TAG_DESCRIPTION,
                keyBoardOption = KeyboardOptions.Default.copy( imeAction = ImeAction.None),
                maxLine = Int.MAX_VALUE,
                singleLine = false
            )
        }
    }
}