package com.example.notele.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notele.usecases.model.NoteleEvent
import com.example.notele.util.Utils
import com.example.notele.views.components.ItemScreen
import com.example.notele.views.components.OrderSection
import com.example.notele.viewmodel.HomeViewModel
import com.example.notele.views.navigation.DestinationScreen
import kotlinx.coroutines.launch

@Composable
fun ScreenHome(
    vm : HomeViewModel = hiltViewModel(),
    navController: NavController
){
    val stateVm = vm.state.value
    //Barra bocadillo + alcance de corrutina
    val scaffoldState = rememberScaffoldState()
    val rememberScope = rememberCoroutineScope()

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                /*Navegamos a un nuevo AddScreen*/
                onClick = { navController.navigate(DestinationScreen.AddScreen.value) },
                backgroundColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(12.dp)
        ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add..")
            }
        },
            scaffoldState = scaffoldState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Titulo",
                    style = MaterialTheme.typography.headlineLarge
                )
                IconButton(
                        onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown /*Icons.AutoMirrored.Filled.List*/,
                        contentDescription = "Down"
                    )
                }
                AnimatedVisibility(
                    visible = stateVm.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .testTag(Utils.TEST_TAG_TITLE),
                        order = stateVm.noteOrder, //recompone la vista segun el orden seleccionado por el usuario
                        onChangeSelect = { onChangeItemNotele ->
                            /* */
                            vm.getEvent(NoteleEvent.Order(onChangeItemNotele))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn (modifier = Modifier.fillMaxSize()){
                    items (stateVm.noteList) {item ->
                        ItemScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    /*Navegamos a una nota ya creada*/
                                    navController.navigate(
                                        DestinationScreen.AddScreen.value+
                                        "?idnote=${item.idNotele}&notecolor=${item.color}"
                                    )
                                },
                            notele = item,
                            onDeleteClick = {
                                //Cada ves que se elimine una nota aparecera un snackBar
                                //Donde podemos recuperar la nota eliminada por error
                                //la clase de dato Delete almacena en su parameto el tipo de nota
                                //Que se elimino hace poco
                                //NoteleEvent -> Delete(notele: noteleModel)
                                vm.getEvent(NoteleEvent.Delete(item))
                                rememberScope.launch{
                                    val resultSnackBar = scaffoldState.snackbarHostState.showSnackbar("Deseas recuperar? :",
                                        "Deshacer", SnackbarDuration.Short)
                                    if (resultSnackBar == SnackbarResult.ActionPerformed) {
                                        vm.getEvent(NoteleEvent.RestoreNote)
                                    }
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}




