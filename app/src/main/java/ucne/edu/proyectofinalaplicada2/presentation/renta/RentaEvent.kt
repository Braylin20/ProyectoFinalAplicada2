package ucne.edu.proyectofinalaplicada2.presentation.renta

sealed interface RentaEvent {
    data class OnchangeClienteId(val clienteId: Int) : RentaEvent
    data class OnchangeVehiculoId(val vehiculoId: Int) : RentaEvent
    data class OnchangeFechaRenta(val fechaRenta: String) : RentaEvent
    data class OnchangeFechaEntrega(val fechaEntrega: String) : RentaEvent
    data class OnchangeTotal(val total: Double) : RentaEvent
    data class CalculeTotal(val fechaRenta: String, val fechaEntrega: String, val costoDiario: Int) : RentaEvent
    data class PrepareRentaData(val emailCliente: String?, val vehiculoId: Int, val rentaId: Int) : RentaEvent
    data object ConfirmRenta : RentaEvent
    data object CloseModal : RentaEvent
    data class MostraDatosVehiculoByRole(val isAdmin: Boolean) : RentaEvent
    data object MostraDatosVehiculo : RentaEvent
    data class HandleDatePickerResult(val dateMillis: Long, val isStartDate: Boolean) : RentaEvent
    data object Nuevo : RentaEvent
    data class SelectedRenta(val vehiculoId: Int) : RentaEvent
    data object UpdateRenta : RentaEvent
    data object ClearSuccess: RentaEvent
    data class DeleteRenta(val rentaId: Int, val vehiculoId: Int) : RentaEvent
    data object ClearError : RentaEvent
    data object GetRentas: RentaEvent
}