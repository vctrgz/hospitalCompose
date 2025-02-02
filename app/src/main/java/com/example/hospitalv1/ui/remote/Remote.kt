package com.example.hospitalv1.ui.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

data class Nurse(val id: Int=-1, val name: String, val password: String, val profilePictureUrl: String="")
//data class LoginRequest(val username: String, val password: String)

sealed interface RemoteNurseUiState {
    data class Success(val nurse: Nurse) : RemoteNurseUiState
    object Error : RemoteNurseUiState
    object Cargant : RemoteNurseUiState
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

    @GET("/nurse/name/{name}")
    suspend fun getRemoteFindByName(): List<Nurse>
}
