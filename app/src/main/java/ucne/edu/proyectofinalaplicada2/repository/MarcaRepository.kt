package ucne.edu.proyectofinalaplicada2.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ucne.edu.proyectofinalaplicada2.data.local.dao.MarcaDao
import ucne.edu.proyectofinalaplicada2.data.local.entities.VehiculoEntity
import ucne.edu.proyectofinalaplicada2.data.remote.RentCarRemoteDataSource
import ucne.edu.proyectofinalaplicada2.data.remote.dto.MarcaDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.toEntity
import ucne.edu.proyectofinalaplicada2.utils.Resource
import javax.inject.Inject

class MarcaRepository @Inject constructor(
    private val rentCarRemoteDataSource: RentCarRemoteDataSource,
) {
    fun getMarcas(): Flow<Resource<List<MarcaDto>>> = flow{
        try {
            emit(Resource.Loading())
            val marcas = rentCarRemoteDataSource.getMarcas()
            emit(Resource.Success(marcas))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
    fun getMarcaById(id: Int): Flow<Resource<MarcaDto>> = flow{
        try {
            emit(Resource.Loading())
            val marca = rentCarRemoteDataSource.getMarcas().first{ it.marcaId == id}
            emit(Resource.Success(marca))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        }
        catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }


}