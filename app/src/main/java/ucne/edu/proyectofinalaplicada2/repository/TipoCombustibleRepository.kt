package ucne.edu.proyectofinalaplicada2.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ucne.edu.proyectofinalaplicada2.data.remote.RentCarRemoteDataSource
import ucne.edu.proyectofinalaplicada2.data.remote.dto.RentaDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.TipoCombustibleDto
import ucne.edu.proyectofinalaplicada2.utils.Resource
import javax.inject.Inject

class TipoCombustibleRepository @Inject constructor(
    private val rentCarRemoteDataSource: RentCarRemoteDataSource
){
    fun getTiposCombustibles(): Flow<Resource<List<TipoCombustibleDto>>> = flow {
        try {
            emit(Resource.Loading())
            val tipoCombustibles = rentCarRemoteDataSource.getTiposCombustibles()
            emit(Resource.Success(tipoCombustibles))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }

}