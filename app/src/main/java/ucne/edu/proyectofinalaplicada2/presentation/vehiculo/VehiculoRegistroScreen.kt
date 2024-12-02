package ucne.edu.proyectofinalaplicada2.presentation.vehiculo

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import ucne.edu.proyectofinalaplicada2.R
import ucne.edu.proyectofinalaplicada2.presentation.components.InputSelect
import ucne.edu.proyectofinalaplicada2.presentation.permisos.PermisoGallery
import ucne.edu.proyectofinalaplicada2.utils.Constant

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun VehiculoRegistroScreen(
    vehiculoViewModel: VehiculoViewModel = hiltViewModel(),
    vehiculoId: Int,
) {
    val vehiculoUiState by vehiculoViewModel.uistate.collectAsStateWithLifecycle()

    VehiculoBodyRegistroScreen(
        vehiculoUiState = vehiculoUiState,
        onVehiculoEnvent = { vehiculoEvent -> vehiculoViewModel.onEvent(vehiculoEvent) },
        vehiculoId = vehiculoId,
    )
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun VehiculoBodyRegistroScreen(
    vehiculoUiState: VehiculoUistate,
    onVehiculoEnvent: (VehiculoEvent) -> Unit,
    vehiculoId: Int,
) {
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = vehiculoId) {
        if (vehiculoId > 0) {

            onVehiculoEnvent(VehiculoEvent.SelectedVehiculo(vehiculoId))
        }
    }
    if (vehiculoUiState.isLoadingData == true && vehiculoId > 0) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 30.dp),
        ) {

            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 25.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    AsyncImage(
                        model = "https://rentcarblobstorage.blob.core.windows.net/images/carlogo.png",
                        contentDescription = "Imagen"
                    )
                }

                InputSelect(
                    label = "Marca",
                    options = vehiculoUiState.marcas,
                    selectedOption = vehiculoUiState.marcas.firstOrNull { it.marcaId == vehiculoUiState.marcaId },
                    onOptionSelected = {
                        onVehiculoEnvent(VehiculoEvent.OnChangeMarcaId(it.marcaId))
                    },
                    labelSelector = { it.nombreMarca },
                    errorMessage = vehiculoUiState.marcaError
                )
                if (vehiculoUiState.marcaError != "") {
                    Text(
                        text = vehiculoUiState.marcaError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                InputSelect(
                    label = "Tipo Combustible",
                    options = vehiculoUiState.tipoCombustibles ?: emptyList(),
                    selectedOption = vehiculoUiState.tipoCombustibles?.firstOrNull { it.tipoCombustibleId == vehiculoUiState.tipoCombustibleId },
                    onOptionSelected = {
                        onVehiculoEnvent(VehiculoEvent.OnChangeTipoCombustibleId(it.tipoCombustibleId))
                    },
                    labelSelector = { it.nombreTipoCombustible },
                    errorMessage = vehiculoUiState.tipoCombustibleError
                )
                if (vehiculoUiState.tipoCombustibleError != "") {
                    Text(
                        text = vehiculoUiState.tipoCombustibleError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                InputSelect(
                    label = "Tipo Vehiculo",
                    options = vehiculoUiState.tipoVehiculos ?: emptyList(),
                    selectedOption = vehiculoUiState.tipoVehiculos?.firstOrNull { it.tipoVehiculoId == vehiculoUiState.tipoVehiculoId },
                    onOptionSelected = {
                        onVehiculoEnvent(VehiculoEvent.OnChangeTipoVehiculoId(it.tipoVehiculoId))
                    },
                    labelSelector = { it.nombreTipoVehiculo },
                    errorMessage = vehiculoUiState.tipoVehiculoError
                )
                if (vehiculoUiState.tipoVehiculoError != "") {
                    Text(
                        text = vehiculoUiState.tipoVehiculoError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                InputSelect(
                    label = "Modelo",
                    options = vehiculoUiState.modelos,
                    selectedOption = vehiculoUiState.modelos.firstOrNull { it.modeloId == vehiculoUiState.modeloId },
                    onOptionSelected = {
                        onVehiculoEnvent(VehiculoEvent.OnChangeModeloId(it.modeloId))
                    },
                    labelSelector = { it.modeloVehiculo },
                    errorMessage = vehiculoUiState.modeloError
                )
                if (vehiculoUiState.modeloError != "") {
                    Text(
                        text = vehiculoUiState.modeloError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                InputSelect(
                    label = "Proveedor",
                    options = vehiculoUiState.proveedores ?: emptyList(),
                    selectedOption = vehiculoUiState.proveedores?.firstOrNull { it.proveedorId == vehiculoUiState.proveedorId },
                    onOptionSelected = {
                        onVehiculoEnvent(VehiculoEvent.OnChangeProveedorId(it.proveedorId))
                    },
                    labelSelector = { it.nombre },
                    errorMessage = vehiculoUiState.proveedorError
                )
                if (vehiculoUiState.proveedorError != "") {
                    Text(
                        text = vehiculoUiState.proveedorError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                OutlinedTextField(
                    value = vehiculoUiState.precio?.toString() ?: "",
                    onValueChange = {
                        onVehiculoEnvent(
                            VehiculoEvent.OnChangePrecio(
                                it.toIntOrNull() ?: 0
                            )
                        )
                    },
                    label = { Text(text = "Precio") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = vehiculoUiState.precioError.isNotEmpty()
                )
                if (vehiculoUiState.precioError != "") {
                    Text(
                        text = vehiculoUiState.precioError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
                OutlinedTextField(
                    value = vehiculoUiState.anio?.toString() ?: "",
                    onValueChange = {
                        onVehiculoEnvent(
                            VehiculoEvent.OnChangeAnio(
                                it.toIntOrNull() ?: 0
                            )
                        )
                    },
                    label = { Text(text = "Año") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = vehiculoUiState.anioError.isNotEmpty()

                )
                if (vehiculoUiState.anioError != "") {
                    Text(
                        text = vehiculoUiState.anioError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                OutlinedTextField(
                    value = vehiculoUiState.descripcion,
                    onValueChange = { onVehiculoEnvent(VehiculoEvent.OnChangeDescripcion(it)) },
                    label = { Text(text = "Descripcion") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = vehiculoUiState.descripcionError.isNotEmpty()
                )
                if (vehiculoUiState.descripcionError.isNotEmpty()) {
                    Text(
                        text = vehiculoUiState.descripcionError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                if (vehiculoId > 0) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(vehiculoUiState.imagePath) { imageUrl ->
                            AsyncImage(
                                model = Constant.URL_BLOBSTORAGE + imageUrl,
                                contentDescription = "Imagen del Vehículo",
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                } else {
                    PermisoGallery()
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(onClick = {
                        if (vehiculoId > 0) {
                            onVehiculoEnvent(VehiculoEvent.UpdateVehiculo)
                        } else {
                            onVehiculoEnvent(VehiculoEvent.Save)
                        }
                    }) {
                        Text(text = if (vehiculoId > 0) "Actualizar" else "Guardar")
                    }
                }
                if (vehiculoUiState.imageError.isNotEmpty() && showDialog) {
                    CustomDialog(
                        message = vehiculoUiState.imageError,
                        isError = vehiculoUiState.imageError.isNotEmpty(),
                        onDismiss = {
                            showDialog = false
                            onVehiculoEnvent(VehiculoEvent.ClearImageError)
                        }
                    )
                } else if (vehiculoUiState.imageError.isNotEmpty()) {
                    showDialog = true
                }
                if (vehiculoUiState.success.isNotEmpty() && showDialog) {
                    CustomDialog(
                        message = vehiculoUiState.success,
                        isError = vehiculoUiState.success.isEmpty(),
                        onDismiss = {
                            showDialog = false
                        }
                    )
                }
                if(vehiculoUiState.error.isNotEmpty()) {
                    CustomDialog(
                        message = vehiculoUiState.error,
                        isError = vehiculoUiState.error.isNotEmpty(),
                        onDismiss = {
                            showDialog = false
                            onVehiculoEnvent(VehiculoEvent.ClearError)
                        }
                    )
                }
                else if (vehiculoUiState.error.isNotEmpty()) {
                    showDialog = true
                }
            }
        }

    }

}

@Composable
fun CustomDialog(
    message: String,
    isError: Boolean,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Aceptar")
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = if (isError) painterResource(id = R.drawable.nocheck) else painterResource(
                        id = R.drawable.check
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isError) Color.Red else Color.Black
                )
            }

        },
        containerColor = Color.White
    )
}


@Composable
fun SelectMultipleImages() {
    val viewModel: VehiculoViewModel = hiltViewModel()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.onEvent(VehiculoEvent.OnImagesSelected(uris, context))
        }
    }

    OutlinedButton(onClick = {
        imagePickerLauncher.launch("image/*")
    }) {
        Text(text = "Seleccionar Imágenes")
    }

    val uiState by viewModel.uistate.collectAsStateWithLifecycle()

    if (uiState.imagePath.isNotEmpty()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.imagePath) { imagePath ->
                Image(
                    painter = rememberAsyncImagePainter(imagePath),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}



