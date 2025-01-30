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

interface RemoteLoginInterface {

    @POST("/nurse/login")
    suspend fun postRemoteLogin(@Body nurse: Nurse): Nurse

    @GET("/nurse/id/1")
    suspend fun getRemoteFindById(): Nurse
}
