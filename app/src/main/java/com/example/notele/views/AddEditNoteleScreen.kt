package com.example.notele.views

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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notele.db.model.NoteleModel
import com.example.notele.util.Utils
import com.example.notele.viewmodel.AddEditNotelesEvent
import com.example.notele.viewmodel.AddEditViewModel
import com.example.notele.views.components.EditTextVisible
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditScreen(
    modifier: Modifier = Modifier,
    vm: AddEditViewModel = hiltViewModel(),
    noteColor: Int,
    navController: NavController
){

    val title = vm.editTitle.value
    val description = vm.editDescription.value
    val scope = rememberCoroutineScope()
    val scaffold = rememberScaffoldState()

    val animateColor = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else vm.priorityColor.value)
        )
    }

    LaunchedEffect(
        key1 = true
    ) {
        vm.stateEvent.collectLatest { event ->
            when(event) {
                is AddEditViewModel.UiEvent.ShowSnackBar -> {
                    scaffold.snackbarHostState.showSnackbar(
                        event.message
                    )
                }
                is AddEditViewModel.UiEvent.SaveNotele -> {
                    //Agregar guardado Ui
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = Color.Black
            ) {
                Icon(Icons.Rounded.Done, "Save")
            }
        }
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
                textStyle = MaterialTheme.typography.bodyMedium,
                focusState = { vm.onEvent(AddEditNotelesEvent.TitleChange(it))},
                hintVisible = title.isHintVisible,
                tag = Utils.TEST_TAG_TITLE
                )
            Spacer(modifier = Modifier.height(12.dp))
            EditTextVisible(
                text = description.text,
                hint = description.hint,
                onValueText = { vm.onEvent(AddEditNotelesEvent.Description(it))},
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall,
                focusState = { vm.onEvent(AddEditNotelesEvent.DescriptionChange(it))},
                hintVisible = description.isHintVisible,
                tag = Utils.TEST_TAG_DESCRIPTION
            )
        }
    }
}