package ucne.edu.proyectofinalaplicada2.data.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import ucne.edu.proyectofinalaplicada2.data.remote.dto.ClienteDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.MarcaDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.ModeloDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.ProveedorDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.RentaDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.TipoCombustibleDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.TipoVehiculoDto
import ucne.edu.proyectofinalaplicada2.data.remote.dto.VehiculoDto

interface RentCarApi {
    // clientes
    @GET("api/Usuarios")
    suspend fun getClientes(): List<ClienteDto>
    @POST("api/Usuarios")
    suspend fun addCliente(@Body clienteDto: ClienteDto): ClienteDto
    @PUT("api/Usuarios/{id}")
    suspend fun updateCliente(@Path ("id") id: Int ,@Body clienteDto: ClienteDto): ClienteDto
    @GET("api/Usuarios/api/Clientes/GetByEmail/{email}")
    suspend fun getClienteByEmail(@Path("email") email: String): ClienteDto

    // rentas
    @GET("api/Rentas")
    suspend fun getRentas(): List<RentaDto>
    @POST("api/Rentas")
    suspend fun addRenta(@Body rentaDto: RentaDto): RentaDto
    @PUT("api/Rentas/{id}")
    suspend fun updateRenta(@Path ("id") id: Int ,@Body rentaDto: RentaDto): RentaDto
    @DELETE("api/Rentas/{id}")
    suspend fun deleteRenta(@Path("id") id: Int): RentaDto

    // vehiculos
    @GET("api/Vehiculos")
    suspend fun getVehiculos(): List<VehiculoDto>

    @GET("api/Vehiculos/{id}")
    suspend fun getVehiculoById(@Path("id") id: Int): VehiculoDto


    @DELETE("api/Vehiculos/{id}")
    suspend fun deleteVehiculo(@Path("id") id: Int): VehiculoDto
    @Multipart
    @POST("api/Vehiculos")
    suspend fun addVehiculo(
        @Part("tipoCombustibleId") tipoCombustibleId: RequestBody,
        @Part("tipoVehiculoId") tipoVehiculoId: RequestBody,
        @Part("proveedorId") proveedorId: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part("descripcion") descripcion: RequestBody?,
        @Part("marcaId") marcaId: RequestBody,
        @Part("modeloId") modeloId: RequestBody,
        @Part images: List<MultipartBody.Part>?,
        @Part("anio") anio: RequestBody,
    ): VehiculoDto

    @PUT("update/{id}")
    suspend fun updateVehiculo(@Path ("id") id: Int ,@Body vehiculoDto: VehiculoDto): VehiculoDto
    // Marca
    @GET("api/Marcas")
    suspend fun getMarcas(): List<MarcaDto>
    @GET("api/Marcas/{id}")
    suspend fun getMarcaById(@Path("id") id: Int): MarcaDto
    @POST("api/Marcas")
    suspend fun addMarca(@Body marcaDto: MarcaDto): MarcaDto
    @PUT("api/Marcas/{id}")
    suspend fun updateMarca(@Path ("id") id: Int ,@Body marcaDto: MarcaDto): MarcaDto

    // TipoCombustible
    @GET("api/TipoCombustibles")
    suspend fun getTipoCombustibles(): List<TipoCombustibleDto>
    @POST("api/TipoCombustibles")
    suspend fun addTipoCombustible(@Body tipoCombustibleDto: TipoCombustibleDto): TipoCombustibleDto
    @PUT("api/TipoCombustibles/{id}")
    suspend fun updateTipoCombustible(@Path ("id") id: Int ,@Body tipoCombustibleDto: TipoCombustibleDto): TipoCombustibleDto

    // TipoVehiculo
    @GET("api/TipoVehiculoes")
    suspend fun getTipoVehiculos(): List<TipoVehiculoDto>


    // Proveedores
    @GET("api/Proveedores")
    suspend fun getProveedores(): List<ProveedorDto>
    @POST("api/Proveedores")
    suspend fun addProveedor(@Body proveedorDto: ProveedorDto): ProveedorDto


    //Modelos
    @GET("api/Modelos/{id}")
    suspend fun getModelosById(@Path("id") id: Int): List<ModeloDto>
    @GET("api/Modelos")
    suspend fun getModelos(): List<ModeloDto>


}