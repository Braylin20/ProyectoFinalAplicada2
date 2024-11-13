package ucne.edu.proyectofinalaplicada2.presentation.vehiculo

import ucne.edu.proyectofinalaplicada2.data.remote.dto.MarcaDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.ModeloDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.ProveedorDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.TipoCombustibleDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.TipoVehiculoDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.VehiculoDto

data class Uistate(
    val vehiculoId: Int? = null,
    val tipoCombustibleId: Int? = null,
    val tipoVehiculoId: Int? = null,
    val marcaId: Int = 0,
    val modeloId: Int = 0,
    val precio: Int? = null,
    val descripcion: String = "",
    val imagePath: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val marcas: List<MarcaDto> = emptyList(),
    val tiposCombustibles: List<TipoCombustibleDto> = emptyList(),
    val vehiculos: List<VehiculoDto> = emptyList(),
    val tiposVehiculos: List<TipoVehiculoDto> = emptyList(),
    val proveedores: List<ProveedorDto> = emptyList(),
    val modelos: List<ModeloDto> = emptyList(),

)

