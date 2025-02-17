package com.example.notele.presentation.views

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notele.presentation.viewmodel.NoteleEvent
import com.example.notele.data.util.Utils
import com.example.notele.presentation.views.components.ItemScreen
import com.example.notele.presentation.views.components.OrderSection
import com.example.notele.presentation.viewmodel.HomeViewModel
import com.example.notele.presentation.views.navigation.DestinationScreen
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun ScreenHome(
    vm: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val stateVm = vm.state.value
    val rememberScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var iconArrow by remember { mutableStateOf(true) }

    Scaffold(
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                /*Navegamos a un nuevo AddScreen*/
                onClick = { navController.navigate(DestinationScreen.AddScreen.value) },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.Black
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Notas",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 12.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        vm.getEvent(NoteleEvent.ToggleOrderSection)
                        iconArrow = !iconArrow
                    }
                ) {
                    Icon(
                        imageVector = if (iconArrow) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Down",
                    )
                }
            }
            AnimatedVisibility(
                visible = stateVm.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .testTag(Utils.TEST_TAG_TITLE),
                    order = stateVm.noteOrder, /*recompone la vista segun el orden seleccionado por el usuario*/
                    onChangeSelect = { onChangeItemNotele ->
                        /*Inicializa la seleccion con titulo en descendente mediante */
                        vm.getEvent(NoteleEvent.Order(onChangeItemNotele))
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)) {
                items(items = stateVm.noteList) { item ->
                    Log.e("aqui lista:", "${stateVm.noteList}")
                    ItemScreen(
                        modifier = Modifier
                            .clickable {
                                /*Navegamos a una nota ya creada*/
                                navController.navigate(
                                    DestinationScreen.AddScreen.value +
                                            "?idNotele=${item.idNotele}&noteColor=${item.color}"
                                )
                            },
                        notele = item,
                        onDeleteClick = {
                            rememberScope.launch {
                                /*Cada ves que se elimine una nota aparecera un snackBar
                                  donde podemos recuperar la nota eliminada con la accion de deshacer
                                  obteniendo la ultima nota eliminada almacenada desde un parametro de
                                  tipo orden proporcionado por el evento */
                                vm.getEvent(NoteleEvent.Delete(item))
                                val resultSnackBar = snackBarHostState.showSnackbar(
                                    message = "Deseas recuperar? :",
                                    actionLabel = "Deshacer",
                                    duration = SnackbarDuration.Short
                                )
                                //La acción del boton deshacer, deshacera la eliminación lo que restaura
                                //la ultima nota eliminada
                                if (resultSnackBar == ActionPerformed) {
                                    vm.getEvent(NoteleEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}






