package ucne.edu.proyectofinalaplicada2.presentation.proveedor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.proyectofinalaplicada2.repository.ProveedorRepository
import ucne.edu.proyectofinalaplicada2.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ProveedorViewModel @Inject constructor(
    private val proveedorRepository: ProveedorRepository
) : ViewModel() {
    private val _uistate = MutableStateFlow(ProveedorUistate())
    val uistate = _uistate.asStateFlow()

    init {
        getProveedores()
    }

    private fun getProveedores(){
        viewModelScope.launch {
            proveedorRepository.getProveedores().collect { result ->
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
                                proveedores = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }
    private fun onProveedorModeloId(proeedorId: Int){
        _uistate.update {
            it.copy(
                proveedorId = proeedorId,
            )
        }
    }


    fun onEvent(event: ProveedorEvent) {
        when (event) {
            is ProveedorEvent.OnChangeProveedorID -> onProveedorModeloId(event.proveedorId)
        }
    }
}
