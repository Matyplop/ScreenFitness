package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.awa

//val es una variable que no cambia
@Composable
fun GraficoCircular(
    modificador: Modifier,
    valor: List<Double>, //por ejempo en la data tenemos listof(2.0, 3.0,1.0)
    // me sirve para sumar el total de los pasos , caminar , correr ,etc.
    colores: List<Color>,
    espacioenGrados: Int =5, //espacio en blanco en gradoos , para dejar en cada pedazo de el circulo
    lineaGrosor: Float = 80f //grosor y f es float decimal
) {
    val totalValor = valor.sum() //aqui sumamos la lista values
    val gradosDisponibles = 360f - (espacioenGrados * valor.size) //esto es para dar separacion entre las partes
    //un circulo tiene 360 grados , habria que restarle la separacion
    // values.size es lo que esta adentro (2.0,1.0,3.0) en este caso hay 3
    //por ende se dibuja solo 320° del circulo ya que se restan 4 que 4 son las actividades


    // un box para meter todo el dibujo y los textos adentro.

    Box(modifier = modificador, contentAlignment = Alignment.Center) {
        //alignment.center para que todo este alineado al centro en este caso el grafico
        Canvas(modifier = Modifier.matchParentSize()) {
            //matchparentsize() es para que el grafico use todo el espacio disponible dentro de la caja
            val estilo_linea = Stroke(width = lineaGrosor, cap = StrokeCap.Round)
            //aqui le damos que el estilo linea sea el grosor y al final del extremo sea redondeado
            var empezarDibujarCirculo = -90f //

            for (i in valor.indices) {
                //recorremos el valor de la lista [2.0 3.0 1.0)
                // para hacerle el calculo abajo
                val calculo = (valor[i] / totalValor * gradosDisponibles).toFloat()
                //aqui hacemos el calculo de cuantos grados ocupará en el circulo
                //
                drawArc(
                    color = colores[i],
                    startAngle = empezarDibujarCirculo,
                    sweepAngle = calculo,
                    useCenter = false,
                    style = estilo_linea
                )
                //y esto es basicamente para que cuando empieze de nuevo a dibujar el circulo no se sobreponga
                // es para que el nuevo pedazo no se vea encima de los demas
                    empezarDibujarCirculo += calculo + espacioenGrados
            }
        }

        //aqui mostramos el texto al centro de la dona con el numero total.

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //columna ya que todo se va a mostrar de  arriba hacia abajo
            //y centramos horizontalmente
            Text(
                text = "${totalValor}k", //insertamos una variable val dentro de un texto
                style = MaterialTheme.typography.headlineMedium, //tamaño de letra
                fontWeight = FontWeight.Bold //aqui le damos que la fuente sea en negrita
            )
            Text(
                text = "Pasos totales",
                style = MaterialTheme.typography.bodyMedium, //le letra tamaño
            )
        }
    }
}

@Composable
fun Inicio() {
    var currentMonthIndex by remember { mutableStateOf(0) }
    val currentMonth = mesesData[currentMonthIndex]

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Tarjetas_componente{ Usuario() }
            Tarjetas_componente { Informacion_awa() }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {
                            if (currentMonthIndex > 0) currentMonthIndex--
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Mes anterior")
                        }

                        Text(currentMonth.mesesNombre, fontWeight = FontWeight.Bold)

                        IconButton(onClick = {
                            if (currentMonthIndex < mesesData.size - 1) currentMonthIndex++
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Mes siguiente")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    GraficoCircular(
                        modificador = Modifier.size(180.dp),
                        valor = currentMonth.actividadesMes,
                        colores = Actividades.map { it.color }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        for (i in Actividades.indices) {
                            Lista_pasos(
                                color = Actividades[i].color,
                                text = "${Actividades[i].nombre} ${currentMonth.actividadesMes[i]}k"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Tarjetas_componente(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        content = content
    )
}

@Composable
fun Usuario() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_linknet), // tu imagen debe estar en res/drawable
            contentDescription = "Imagen de perfil",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "Usuario Ejemplo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Perfil 1",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun Informacion_awa() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Grafico_awa( progress = 0.68f)
        Spacer(modifier = Modifier.width(16.dp))
        Text("68%", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Agua consumida", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun Grafico_awa(progress: Float) {
    Box(modifier = Modifier.size(60.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(awa.copy(alpha = 0.15f), CircleShape)
        )
        Canvas(modifier = Modifier.size(60.dp)) {
            drawArc(
                color = awa,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Composable
fun Lista_pasos(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Box(modifier = Modifier.size(12.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}
