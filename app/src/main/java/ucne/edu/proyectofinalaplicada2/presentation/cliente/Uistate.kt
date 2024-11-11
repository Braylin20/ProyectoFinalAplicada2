package ucne.edu.proyectofinalaplicada2.presentation.cliente

import ucne.edu.proyectofinalaplicada2.data.remote.dto.ClienteDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.RentaDto

data class Uistate(
    val clienteId: Int? = null,
    val cedula: String = "",
    val nombre: String = "",
    val apellidos: String = "",
    val direccion: String = "",
    val celular: String = "",
    val clientes: List<ClienteDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = ""

)

fun Uistate.toEntity() = ClienteDto(
    clienteId = clienteId,
    cedula = cedula,
    nombre = nombre,
    apellidos = apellidos,
    direccion = direccion,
    celular = celular
)
