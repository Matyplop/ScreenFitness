package com.example.myapplication

import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.theme.*


data class Actividad_Data(
    val nombre: String,
    val color: Color
)

data class MesesActividadData(
    val mesesNombre: String,
    val totalPasos: Int,
    val actividadesMes: List<Double>
)

val Actividades = listOf(
    Actividad_Data("Caminar", CaminarColor),
    Actividad_Data("Carrera", CarreraColor),
    Actividad_Data("Bicicleta", BicicletaColor),
    Actividad_Data("Nadar", NadarColor)
)

val mesesData = listOf(
    MesesActividadData("Mayo", 20700, listOf(2.1, 1.0, 1.9, 3.7)),
    MesesActividadData("Junio", 2000, listOf(3.0, 1.5, 2.0, 1.0)),
    MesesActividadData("Julio", 18000, listOf(1.5, 0.8, 1.0, 2.0)),
    MesesActividadData("Agosto", 30000, listOf(4.0, 2.0, 3.0, 3.0)),
    MesesActividadData("Septiembre", 22000, listOf(2.5, 1.2, 1.5, 6.0)),
    MesesActividadData("Octubre", 19000, listOf(2.0, 1.0, 1.2, 5.0))
)