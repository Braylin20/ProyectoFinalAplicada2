package ucne.edu.proyectofinalaplicada2.presentation.renta

import ucne.edu.proyectofinalaplicada2.data.remote.dto.MarcaDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.RentaDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.VehiculoDto

data class RentaUistate(
    val rentaId: Int? = null,
    val clienteId: Int? = null,
    val vehiculoId: Int? = null,
    val fechaRenta: String = "",
    val fechaEntrega: String? = null,
    val total: Int? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val rentas: List<RentaDto> = emptyList(),
)

fun RentaUistate.toEntity() = RentaDto(
    rentaId = rentaId,
    clienteId = clienteId,
    vehiculoId = vehiculoId,
    fechaRenta = fechaRenta,
    fechaEntrega = fechaEntrega,
    total = total
)