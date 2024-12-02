package ucne.edu.proyectofinalaplicada2.presentation.vehiculo

import java.io.File

sealed interface VehiculoEvent {
    data object Save : VehiculoEvent
    data object GetVehiculos : VehiculoEvent
    data class OnChangePrecio(val precio: Int) : VehiculoEvent
    data class OnChangeDescripcion(val descripcion: String) : VehiculoEvent
    data class OnChangeTipoCombustibleId(val tipoCombustibleId: Int) : VehiculoEvent
    data class OnChangeTipoVehiculoId(val tipoVehiculoId: Int) : VehiculoEvent
    data class OnChangeProveedorId(val proveedorId: Int): VehiculoEvent
    data class OnChangeModeloId(val modeloId: Int): VehiculoEvent
    data class OnChangeMarcaId(val marcaId: Int): VehiculoEvent
    data class OnChangeAnio(val anio: Int): VehiculoEvent
    data class OnChangeImagePath(val imagePath: List<File?>): VehiculoEvent
    data class OnFilterVehiculos(val query: String): VehiculoEvent
    data class SelectedVehiculo(val vehiculoId: Int): VehiculoEvent
    data object UpdateVehiculo : VehiculoEvent

}