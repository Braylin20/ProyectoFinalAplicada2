package ucne.edu.proyectofinalaplicada2.presentation.cliente

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.proyectofinalaplicada2.repository.ClienteRepository
import ucne.edu.proyectofinalaplicada2.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {
    private val _uistate = MutableStateFlow(Uistate())
    val uistate = _uistate.asStateFlow()

    init {
        getClientes()
    }

    private fun getClientes() {
        viewModelScope.launch {
            clienteRepository.getClientes().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uistate.update {
                            it.copy(
                                error = result.message ?: "Error"
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uistate.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        _uistate.update {
                            it.copy(
                                clientes = result.data ?: emptyList()

                            )
                        }
                    }
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            val cliente = if (_uistate.value.clienteId != null && _uistate.value.clienteId != 0){
                clienteRepository.updateCliente(_uistate.value.clienteId!!, uistate.value.toEntity())
            }else{
                clienteRepository.addCliente(uistate.value.toEntity())
            }
            cliente.collect { result ->
                when (result){
                    is Resource.Error -> {
                        _uistate.update {
                            it.copy(
                                error = result.message ?: "Error"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _uistate.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uistate.update {
                            it.copy(
                                success = "Cliente agregado"
                            )
                        }
                        nuevo()
                    }
                }
            }
        }
    }

    fun selectedClient(id: Int){
        viewModelScope.launch {
            clienteRepository.findCliente(id).collectLatest { result->
                when(result){
                    is Resource.Loading ->{
                        _uistate.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uistate.update {
                            it.copy(
                                clienteId = result.data?.clienteId,
                                cedula = result.data?.cedula ?: "",
                                nombre = result.data?.nombre ?: "",
                                apellidos = result.data?.apellidos ?: "",
                                direccion = result.data?.direccion ?: "",
                                celular = result.data?.celular ?: "",
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uistate.update {
                            it.copy(
                                error = result.message ?: "Error desconocido"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun nuevo() {
        _uistate.update {
            it.copy(
                clienteId = null,
                cedula = "",
                nombre = "",
                apellidos = "",
                direccion = "",
                celular = "",
                success = "",
                error = "",
            )
        }
    }

    private fun onChangeCedula(cedula: String) {
        _uistate.update {
            it.copy(
                cedula = cedula
            )
        }
    }
    private fun onChangeNombre(nombre: String) {
        _uistate.update {
            it.copy(
                nombre = nombre
            )
        }
    }

    private fun onChangeApellidos(apellidos: String) {
        _uistate.update {
            it.copy(
                apellidos = apellidos
            )
        }
    }

    private fun onChangeDireccion(direccion: String) {
        _uistate.update {
            it.copy(
                direccion = direccion
            )
        }
    }

    private fun onChangeCelular(celular: String) {
        _uistate.update {
            it.copy(
                celular = celular
            )
        }
    }

    fun onEvent(event: ClienteEvent) {
        when (event) {
            is ClienteEvent.OnchangeApellidos -> onChangeApellidos(event.apellidos)
            is ClienteEvent.OnchangeCedula -> onChangeCedula(event.cedula)
            is ClienteEvent.OnchangeCelular -> onChangeCelular(event.celular)
            is ClienteEvent.OnchangeDireccion -> onChangeDireccion(event.direccion)
            is ClienteEvent.OnchangeNombre -> onChangeNombre(event.nombre)
            is ClienteEvent.selectedClient -> selectedClient(event.id)
            ClienteEvent.Save -> save()
        }
    }
}
