package ucne.edu.proyectofinalaplicada2.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import ucne.edu.proyectofinalaplicada2.R
import ucne.edu.proyectofinalaplicada2.presentation.vehiculo.VehiculoConMarca
import ucne.edu.proyectofinalaplicada2.presentation.vehiculo.VehiculoEvent
import ucne.edu.proyectofinalaplicada2.presentation.vehiculo.VehiculoUistate
import ucne.edu.proyectofinalaplicada2.presentation.vehiculo.VehiculoViewModel
import ucne.edu.proyectofinalaplicada2.utils.Constant
import ucne.edu.proyectofinalaplicada2.components.VehicleCard
@Composable
fun FiltraVehiculo(
    viewModel: VehiculoViewModel = hiltViewModel(),
    onGoRenta: (Int) -> Unit
) {
    val uiState by viewModel.uistate.collectAsStateWithLifecycle()
    Column {
        SearchBar(
            searchQuery = uiState.searchQuery,
            onEvent = {event -> viewModel.onEvent(event)}
        )
        FiltraVehiculoBody(
            uiState = uiState,
            onGoRenta = onGoRenta
        )
    }

}

@Composable
fun FiltraVehiculoBody(
    uiState: VehiculoUistate,
    onGoRenta: (Int) -> Unit,
) {
    when {
        uiState.isLoading == true -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.filteredListIsEmpty-> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            )
            {
                Text(text = "No se encontraron vehiculos")
            }
        }

        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(uiState.vehiculoConMarcas) { vehiculoConMarca ->
                    val painter = vehiculoConMarca.vehiculo.imagePath.firstOrNull()
                    VehicleCard(
                        imageUrl = Constant.URL_BLOBSTORAGE + painter,
                        vehicleName = vehiculoConMarca.nombreMarca?:"",
                        vehicleDetails = "Precio: ${vehiculoConMarca.vehiculo.precio}\nAño: ${vehiculoConMarca.vehiculo.anio}\nModelo: ${vehiculoConMarca.nombreModelo}",
                        vehiculoId = vehiculoConMarca.vehiculo.vehiculoId?:0,
                        onGoRenta = onGoRenta,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onEvent: (VehiculoEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        value = searchQuery,
        onValueChange = { onEvent(VehiculoEvent.OnFilterVehiculos(it)) },
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
            .focusRequester(focusRequester)
    )
}
