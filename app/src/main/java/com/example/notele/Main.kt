package com.example.notele

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(){
    val job = Job() // Crear un Job independiente
    val context = Dispatchers.IO + job //Asociamos

    val scope = CoroutineScope(context)

    scope.launch {
        delay(1000L)
        println("Tarea completada")
    }
    scope.cancel()

}