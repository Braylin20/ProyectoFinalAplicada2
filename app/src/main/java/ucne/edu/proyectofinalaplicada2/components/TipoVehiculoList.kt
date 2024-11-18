package ucne.edu.proyectofinalaplicada2.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ucne.edu.proyectofinalaplicada2.data.remote.dto.VehiculoDto
import ucne.edu.proyectofinalaplicada2.presentation.vehiculo.VehiculoEvent

@Composable
fun TipoVehiculoList(
    painter: Painter,
    marca: String,
    listaModeloEjemplo: String,
    onGoVehiculePresentation:(Int)-> Unit,
    vehiculoDto: VehiculoDto,
    onEvent: (VehiculoEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                onEvent(VehiculoEvent.OnChangeMarcaId(vehiculoDto.marcaId?:0))
                onGoVehiculePresentation(vehiculoDto.marcaId?:0)
            }
            )
        ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )

    ) {
        Row {
            VehiculeImage(
                painter = painter
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = marca, style = MaterialTheme.typography.headlineSmall)
                Text(text = listaModeloEjemplo, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun VehiculeImage(
    painter: Painter
) {
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}