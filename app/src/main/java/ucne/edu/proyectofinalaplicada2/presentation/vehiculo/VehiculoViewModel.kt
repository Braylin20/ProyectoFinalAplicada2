package ucne.edu.proyectofinalaplicada2.presentation.vehiculo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.proyectofinalaplicada2.repository.VehiculoRepository
import ucne.edu.proyectofinalaplicada2.utils.Resource
import javax.inject.Inject

@HiltViewModel
class VehiculoViewModel @Inject constructor(
    private val vehiculoRepository: VehiculoRepository
) : ViewModel() {
    private val _uistate = MutableStateFlow(Uistate())
    val uistate = _uistate.asStateFlow()


    init {
        getVehiculos()
    }

    private fun getVehiculos() {
        viewModelScope.launch {
            vehiculoRepository.getVehiculos().collect { result ->

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
                                vehiculos = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            val vehiculo =
                if (_uistate.value.vehiculoId != null && _uistate.value.vehiculoId != 0) {
                    vehiculoRepository.updateVehiculo(
                        _uistate.value.vehiculoId!!,
                        uistate.value.toEntity()
                    )
                } else {
                    vehiculoRepository.addVehiculo(uistate.value.toEntity())
                }
            vehiculo.collect { result ->
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
                                success = "Vehiculo agregado"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun selectedVehiculo(id: Int) {
        viewModelScope.launch {
            vehiculoRepository.findVehiculo(id).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uistate.update {
                            it.copy(
                                error = result.message ?: "Error desconocido"
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uistate.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uistate.update {
                            it.copy(
                                vehiculoId = result.data?.vehiculoId,
                                tipoCombustibleId = result.data?.tipoCombustibleId,
                                tipoVehiculoId = result.data?.tipoVehiculoId,
                                marca = result.data?.marca ?: "",
                                modelo = result.data?.modelo ?: "",
                                precio = result.data?.precio ?: 0,
                                descripcion = result.data?.descripcion ?: "",
                            )
                        }
                    }
                }
            }
        }
    }

    private fun deleteVehiculo() {
        viewModelScope.launch {
            vehiculoRepository.deleteVehiculo(uistate.value.vehiculoId!!).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uistate.update {
                            it.copy(
                                error = result.message ?: "Error desconocido"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _uistate.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uistate.update {
                            it.copy(
                                success = "Vehiculo eliminado"
                            )
                        }
                    }
                }

            }
        }
    }

    fun nuevo() {
        _uistate.update {
            it.copy(
                vehiculoId = null,
                tipoCombustibleId = null,
                tipoVehiculoId = null,
                marca = "",
                modelo = "",
                precio = 0,
                descripcion = "",
                success = "",
                error = "",
            )
        }
    }

    private fun onChangeMarca(marca: String) {
        _uistate.update {
            it.copy(
                marca = marca
            )
        }
    }

    private fun onChangeModelo(modelo: String) {
        _uistate.update {
            it.copy(
                modelo = modelo
            )
        }
    }

    private fun onChangePrecio(precio: Int) {
        _uistate.update {
            it.copy(
                precio = precio
            )
        }
    }

    private fun onChangeDescripcion(descripcion: String) {
        _uistate.update {
            it.copy(
                descripcion = descripcion
            )
        }
    }

    private fun onChangeTipoCombustibleId(tipoCombustibleId: Int) {
        _uistate.update {
            it.copy(
                tipoCombustibleId = tipoCombustibleId
            )
        }

    }

    private fun onChangeTipoVehiculoId(tipoVehiculoId: Int) {
        _uistate.update {
            it.copy(
                tipoVehiculoId = tipoVehiculoId
            )
        }
    }


    fun onEvent(event: VehiculoEvent) {
        when (event) {
            is VehiculoEvent.OnchangeDescripcion -> onChangeDescripcion(event.descripcion)
            is VehiculoEvent.OnchangeMarca -> onChangeMarca(event.marca)
            is VehiculoEvent.OnchangeModelo -> onChangeModelo(event.modelo)
            is VehiculoEvent.OnchangePrecio -> onChangePrecio(event.precio)
            is VehiculoEvent.OnchangeTipoCombustibleId -> onChangeTipoCombustibleId(event.tipoCombustibleId)
            is VehiculoEvent.OnchangeTipoVehiculoId -> onChangeTipoVehiculoId(event.tipoVehiculoId)
            is VehiculoEvent.SelectedVehiculo -> selectedVehiculo(event.id)
            VehiculoEvent.DeleteVehiculo -> deleteVehiculo()
            VehiculoEvent.Save -> save()
        }
    }

}