package ucne.edu.proyectofinalaplicada2.presentation.marca

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.proyectofinalaplicada2.repository.MarcaRepository
import ucne.edu.proyectofinalaplicada2.repository.VehiculoRepository
import javax.inject.Inject

@HiltViewModel
class MarcaViewModel @Inject constructor(
    private val marcaRepository: MarcaRepository,
    private val vehiculoRepository: VehiculoRepository
):ViewModel() {
    private val _uistate = MutableStateFlow(MarcaUiState())
    val uistate = _uistate.asStateFlow()


    init {
        getMarcas()
        getNombreMarca()
    }

    private fun getMarcas() {
        viewModelScope.launch {
            val result = marcaRepository.getMarcas().data
            _uistate.update {
                it.copy(
                    marcas = result ?: emptyList(),
                )
            }
        }
    }
    private fun getNombreMarca() {
        viewModelScope.launch {
            val vehiculos = vehiculoRepository.getVehiculos().last().data
            val marcasUnicas = vehiculos?.distinctBy { it.marcaId }
            val marcas = marcasUnicas?.map { vehiculo ->
                marcaRepository.getMarcaById(vehiculo.marcaId?:0).data
            }

            _uistate.update {
                it.copy(
                    marcas = marcas?: emptyList(),
                    vehiculos = vehiculos?: emptyList()
                )
            }
        }
    }
    private fun onChangeMarcaId(marcaId: Int) {
        _uistate.update {
            it.copy(
                marcaId = marcaId
            )
        }
    }
    private fun onChangeNombreMarca(nombreMarca: String) {
        _uistate.update {
            it.copy(
                nombreMarca = nombreMarca
            )
        }
    }
    fun onEvent(event: MarcaEvent) {
        when (event) {
            is MarcaEvent.OnchangeMarcaId -> onChangeMarcaId(event.marcaId)
            is MarcaEvent.OnchangeNombreMarca -> onChangeNombreMarca(event.nombreMarca)
        }
    }

}