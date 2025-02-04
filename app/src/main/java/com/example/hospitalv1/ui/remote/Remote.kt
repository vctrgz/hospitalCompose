package com.example.hospitalv1.ui.remote

import org.junit.runners.Parameterized.Parameter
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

data class Nurse(val id: Int=-1, var name: String, var password: String, val profilePictureUrl: String="")
//data class LoginRequest(val username: String, val password: String)

sealed interface RemoteNurseUiState {
    data class Success(val nurse: Nurse) : RemoteNurseUiState
    object Error : RemoteNurseUiState
    object Cargant : RemoteNurseUiState
}
sealed interface RemoteProfileUiState {
    data class Success(val nurse: Nurse) : RemoteProfileUiState
    object Error : RemoteProfileUiState
    object Cargant : RemoteProfileUiState
}
sealed interface RemoteRegisterUiState {
    data class Success(val nurse: Nurse) : RemoteRegisterUiState
    object Error : RemoteRegisterUiState
    object Cargant : RemoteRegisterUiState
}
sealed interface RemoteSearchUiState {
    data class Success(val nurses: List<Nurse>) : RemoteSearchUiState
    object Error : RemoteSearchUiState
    object Cargant : RemoteSearchUiState
}

sealed interface RemoteNurseListState {
    data class Success(val nurses: List<Nurse>) : RemoteNurseListState
    object Error : RemoteNurseListState
    object Cargant : RemoteNurseListState
}


interface RemoteInterface {

    @POST("/nurse/login")
    suspend fun postRemoteLogin(@Body nurse: Nurse): Nurse

    @POST("/nurse/create")
    suspend fun postRemoteRegister(@Body nurse: Nurse): Nurse

    @GET("/nurse/nurses")
    suspend fun getRemoteAllNurses(): List<Nurse>

    @GET("/nurse/id/{id}")
    suspend fun getRemoteFindById(): Nurse
  
    @POST("/nurse/update")
    suspend fun updateNurse(@Body nurse: Nurse, @Query("newName") name: String, @Query("newPassword") password: String): Response<Nurse>

    @DELETE("/nurse/delete/{id}")
    suspend fun deleteNurse(@Path("id") id: Int): Response<Unit>
  
    @GET("/nurse/name/{name}")
    suspend fun getRemoteFindByName(): List<Nurse>
}
