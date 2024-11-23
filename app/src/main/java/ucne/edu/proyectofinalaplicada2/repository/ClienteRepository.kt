package ucne.edu.proyectofinalaplicada2.repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ucne.edu.proyectofinalaplicada2.data.remote.RentCarRemoteDataSource
import ucne.edu.proyectofinalaplicada2.data.remote.dto.ClienteDto
import ucne.edu.proyectofinalaplicada2.utils.Resource
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private  val rentCarRemoteDataSource: RentCarRemoteDataSource
) {
    fun getClientes(): Flow<Resource<List<ClienteDto>>> = flow{
        try {
            emit(Resource.Loading())
            val clientes = rentCarRemoteDataSource.getClientes()
            emit(Resource.Success(clientes))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }

    fun addCliente(clienteDto: ClienteDto): Flow<Resource<ClienteDto>> = flow{
        try {
            emit(Resource.Loading())
            val cliente = rentCarRemoteDataSource.addCliente(clienteDto)
            emit(Resource.Success(cliente))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))

        }
    }

    fun updateCliente(id:Int, clienteDto: ClienteDto): Flow<Resource<ClienteDto>> = flow{
        try {
            emit(Resource.Loading())
            val cliente = rentCarRemoteDataSource.updateCliente(id,clienteDto)
            emit(Resource.Success(cliente))
            } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }

    fun getClienteByEmail(email: String): Flow<Resource<ClienteDto>> = flow {
        try {
            emit(Resource.Loading())
            val cliente = rentCarRemoteDataSource.getClienteByEmail(email)
            emit(Resource.Success(cliente))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }

}