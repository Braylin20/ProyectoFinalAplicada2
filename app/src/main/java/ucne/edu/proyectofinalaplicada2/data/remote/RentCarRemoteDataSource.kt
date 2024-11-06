package ucne.edu.proyectofinalaplicada2.data.remote

import ucne.edu.proyectofinalaplicada2.data.remote.api.RentCarApi
import ucne.edu.proyectofinalaplicada2.data.remote.dto.ClienteDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.RentaDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.VehiculoDto
import javax.inject.Inject

class RentCarRemoteDataSource @Inject constructor(
    private val rentCarApi: RentCarApi
) {
    //cliente
    suspend fun getCliente(id: Int) = rentCarApi.getCliente(id)
    suspend fun getClientes() = rentCarApi.getClientes()
    suspend fun addCliente(clienteDto: ClienteDto) = rentCarApi.addCliente(clienteDto)
    suspend fun updateCliente(id: Int ,clienteDto: ClienteDto) = rentCarApi.updateCliente(id, clienteDto)

    //renta
    suspend fun getRenta(id: Int) = rentCarApi.getRenta(id)
    suspend fun getRentas() = rentCarApi.getRentas()
    suspend fun addRenta(rentaDto: RentaDto) = rentCarApi.addRenta(rentaDto)
    suspend fun updateRenta(id: Int ,rentaDto: RentaDto) = rentCarApi.updateRenta(id, rentaDto)

    //vehiculo
    suspend fun getVehiculo(id: Int) = rentCarApi.getVehiculo(id)
    suspend fun getVehiculos() = rentCarApi.getVehiculos()
    suspend fun addVehiculo(vehiculoDto: VehiculoDto) = rentCarApi.addVehiculo(vehiculoDto)
    suspend fun updateVehiculo(id: Int ,vehiculoDto: VehiculoDto) = rentCarApi.updateVehiculo(id, vehiculoDto)
    suspend fun deleteVehiculo(id: Int) = rentCarApi.deleteVehiculo(id)



}