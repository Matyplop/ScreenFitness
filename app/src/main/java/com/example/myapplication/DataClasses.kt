package com.example.myapplication


// Representa los datos de pasos para un mes específico
data class MonthSteps(
    val month: String,
    val totalSteps: Int,
    val walking: Double,
    val running: Double,
    val cycling: Double,
    val swimming: Double
)