package com.example.myapplication


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*


import com.patrykandpatryk.vico.compose.pie.pieChart // Para configurar el gráfico de dona
import com.patrykandpatryk.vico.compose.pie.slice.rememberSlice // Para definir cada segmento de la dona
import com.patrykandpatryk.vico.core.component.shape.Shapes // Para la forma de los segmentos
import com.patrykandpatryk.vico.core.component.shape.ShapeComponent // Para componentes de forma
import com.patrykandpatryk.vico.core.pie.PieChartEntryModel
import com.patrykandpatryk.vico.core.pie.PieChartEntry // Una entrada de datos para un segmento
import com.patrykandpatryk.vico.core.component.text.textComponent // Para texto en la leyenda (si se usara)
import com.patrykandpatryk.vico.compose.component.shape.shader.verticalGradient // Para gradientes (si se usaran)
import com.patrykandpatryk.vico.core.component.shape.shader.DynamicShader
import com.patrykandpatryk.vico.compose.pie.slice.Slice
import com.patrykandpatryk.vico.core.component.shape.shader.topBottomGradient


// --- Mock Data para Simulación ---
// Datos simulados de pasos por mes
val mockMonthStepsData = listOf(
    MonthSteps("Mayo", 20700, 2.1, 1.0, 1.9, 15.7),
    MonthSteps("Junio", 25000, 3.0, 1.5, 2.0, 18.0),
    MonthSteps("Julio", 18000, 1.5, 0.8, 1.0, 14.0),
    MonthSteps("Agosto", 30000, 4.0, 2.0, 3.0, 21.0),
    MonthSteps("Septiembre", 22000, 2.5, 1.2, 1.5, 16.0),
    MonthSteps("Octubre", 19000, 2.0, 1.0, 1.2, 15.0)
)

// --- Composable para la Pantalla Principal ---
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen() {


    // Estado para el mes actual en el gráfico de dona
    var currentMonthIndex by remember { mutableIntStateOf(0) }
    val currentMonthData = mockMonthStepsData[currentMonthIndex]

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Perfil 1", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sección del Perfil (simplificada y estática)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Usuario Ejemplo", // Nombre de usuario estático
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "¡Bienvenido a tu salud!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de Agua Consumida
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    WaterProgressBar(progress = 0.68f) // 68% de agua consumida
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "68%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Agua consumida",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de Pasos
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        text = "18,000/40,000",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Pasos",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            // Gráfico de Dona y Navegación por Meses (simplificado sin Canvas para los segmentos)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (currentMonthIndex > 0) currentMonthIndex--
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Mes anterior")
                        }
                        AnimatedContent(
                            targetState = currentMonthData.month,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                            }, label = "Mes"
                        ) { targetMonth ->
                            Text(
                                text = targetMonth,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(onClick = {
                            if (currentMonthIndex < mockMonthStepsData.size - 1) currentMonthIndex++
                        }) {
                            Icon(Icons.Default.ArrowForward, contentDescription = "Mes siguiente")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Representación Simplificada del Gráfico de Dona
                    VicoPieChart(currentMonthData)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Leyenda del gráfico de dona
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        LegendItem(color = WalkingColor, text = "Caminar ${currentMonthData.walking}k")
                        LegendItem(color = RunningColor, text = "Carrera ${currentMonthData.running}k")
                        LegendItem(color = CyclingColor, text = "Bicicleta ${currentMonthData.cycling}k")
                        LegendItem(color = SwimmingColor, text = "Nadar ${currentMonthData.swimming}k")
                    }
                }
            }
        }
    }
}

@Composable
fun WaterProgressBar(progress: Float) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color.Transparent, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Fondo del círculo (simulado)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryGreen.copy(alpha = 0.2f), CircleShape)
        )
        // Progreso (simulado con un arco más simple o Box si el 100% es un círculo)
        // Para una barra de progreso circular sin Canvas, una opción es un Stack de Box con gradientes o usar la librería Accompanist Drawable.
        // Pero para la simplicidad extrema, vamos a mostrar un círculo completo si no se usa Canvas.
        // Aquí mantendremos la representación del arco con Canvas, ya que solo pediste quitar el Canvas del *gráfico de dona*.
        Canvas(modifier = Modifier.size(60.dp)) {
            drawArc(
                color = PrimaryGreen,
                startAngle = 270f, // Empieza desde arriba
                sweepAngle = 360 * progress, // El ángulo del progreso
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}


@Composable
fun VicoPieChart(data: MonthSteps) {
    // 1. Preparamos los datos para Vico
    // Cada PieChartEntry representa un segmento de la dona.
    // Necesita un 'value' (el tamaño del segmento), un 'label' (nombre), y un 'color'.
    val entries = remember(data) { // 'remember' para que no se recalcule innecesariamente
        val entriesList = mutableListOf<PieChartEntry>()

        // Añadimos cada actividad como un segmento, solo si su valor es mayor que cero
        if (data.walking > 0) entriesList.add(PieChartEntry(value = data.walking.toFloat(), label = "Caminar", color = WalkingColor))
        if (data.running > 0) entriesList.add(PieChartEntry(value = data.running.toFloat(), label = "Carrera", color = RunningColor))
        if (data.cycling > 0) entriesList.add(PieChartEntry(value = data.cycling.toFloat(), label = "Bicicleta", color = CyclingColor))
        if (data.swimming > 0) entriesList.add(PieChartEntry(value = data.swimming.toFloat(), label = "Nadar", color = SwimmingColor))

        // Si no hay datos detallados para ninguna actividad (por ejemplo, si todas son 0),
        // añadimos una entrada "No hay datos" para que el gráfico no esté vacío.
        if (entriesList.isEmpty()) {
            entriesList.add(PieChartEntry(value = 1f, label = "No hay datos", color = Color.LightGray))
        }
        entriesList
    }

    // 2. Creamos el Modelo de Datos de Vico
    // PieChartEntryModel es lo que Vico usa para dibujar el gráfico.
    val model = remember(entries) { PieChartEntryModel(entries) }

    val donutSize = 150.dp // Tamaño total del Composable del dona
    val holeRadius = 0.5f // Define el tamaño del agujero central (0.0 = pastel, 1.0 = solo un borde fino)
    val sliceWidth = 25.dp // El grosor del anillo del donut

    Box(
        modifier = Modifier
            .size(donutSize)
            .background(MaterialTheme.colorScheme.surface, CircleShape), // El fondo de la tarjeta
        contentAlignment = Alignment.Center
    ) {
        // 3. El Composable principal de Vico para dibujar el gráfico de dona
        com.patrykandpatryk.vico.compose.pie.PieChart( // Usa el nombre completo para evitar conflictos con tu paquete
            modifier = Modifier.fillMaxSize(), // El gráfico ocupa todo el espacio disponible en el Box
            model = model, // Le pasamos nuestros datos preparados
            pieChart = pieChart { // Aquí configuramos cómo se verá el gráfico de dona
                // Definimos cómo se verá cada "slice" (segmento)
                slices = entries.map { entry -> // Para cada entrada de datos que creamos antes...
                    rememberSlice( // Creamos una configuración de segmento de Vico
                        color = entry.color, // Usamos el color de la entrada (WalkingColor, RunningColor, etc.)
                        shape = Shapes.pillShape, // Puedes probar 'Shapes.rectShape' para un borde más cuadrado, 'pillShape' es redondeado
                        width = sliceWidth, // El grosor del anillo del donut
                        gapWidth = 1.dp, // Pequeño espacio entre los segmentos (opcional, haz 0.dp para que se toquen)
                        // label = entry.label?.let { // Puedes mostrar labels DENTRO de los segmentos si quieres
                        //     textComponent { color = MaterialTheme.colorScheme.onSurface; textSize = 10.sp }
                        // }
                    )
                }
                // Configuración del agujero central del donut
                holeRadius = holeRadius // El tamaño del agujero, basado en la proporción del radio total
            },
            // Vico permite añadir leyendas automáticas, animaciones, etc., aquí con más configuraciones.
            // Por ahora, usamos nuestra leyenda manual de abajo para mayor simplicidad.
        )

        // 4. Texto central del donut (total de pasos)
        // Esto se superpone al agujero central del gráfico.
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${data.totalSteps}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Pasos en el mes",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}


@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = MaterialTheme.colorScheme.onSurface)
    }
}

