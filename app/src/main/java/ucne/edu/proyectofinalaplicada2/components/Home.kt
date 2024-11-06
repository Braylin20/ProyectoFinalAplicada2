package ucne.edu.proyectofinalaplicada2.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ucne.edu.proyectofinalaplicada2.R
import ucne.edu.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun Home() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar()
        VehiculosMasDestacados()
        TiposDeVehiculos()
    }
}

@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
            focusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp)
            .padding(15.dp)
    )
}

@Composable
fun VehiculosMasDestacados() {
    val painter = painterResource(id = R.drawable.ferrari)
    val descripcion = "Un ferrari rojo"
    val title = "Ferrari posando duro duro"

    Column(
        modifier = Modifier.padding(bottom = 5.dp, top = 20.dp)
    ) {
        Text(
            text = "Vehiculos destacados",
            fontFamily = FontFamily.Serif,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.padding(vertical = 5.dp),
            contentPadding = PaddingValues(horizontal = 15.dp)
        ) {
            items(vehiculos) {
                Box {
                    ImageCard(
                        painter = painter,
                        contentDescription = descripcion,
                        title = title,
                        height = 180,
                        width = 150
                    )
                }
            }
        }
    }
}

@Composable
fun TiposDeVehiculos() {
    val painter = painterResource(id = R.drawable.toyota)
    val descripcion = "Vehiculo toyota"
    val title = "Toyota"

    Column(
        modifier = Modifier
            .padding(bottom = 5.dp, top = 20.dp)
    ) {
        Text(
            text = "Tipos de vehiculos",
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(),
        ) {
            vehiculos.chunked(2).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    rowItems.forEach { vehiculo ->
                        ImageCard(
                            painter = painter,
                            contentDescription = descripcion,
                            title = title,
                            height = 130,
                            width = 180,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 5.dp)
                        )
                    }

                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.width(180.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    height: Int,
    width: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(height.dp)
            .width(width.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Box{
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 170f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}


private val vehiculos = listOf(
    R.drawable.ferrari,
    R.drawable.ferrari,
    R.drawable.ferrari,
    R.drawable.ferrari,
    R.drawable.ferrari,
    R.drawable.ferrari,
    R.drawable.ferrari,
    R.drawable.ferrari,
    R.drawable.ferrari,
    R.drawable.ferrari,
)

@Preview(showSystemUi = true)
@Composable
private fun HomePreview() {
    ProyectoFinalAplicada2Theme {
        Home()
    }
}