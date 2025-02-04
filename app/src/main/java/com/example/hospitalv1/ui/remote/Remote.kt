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


interface RemoteLoginInterface {

    @POST("/nurse/login")
    suspend fun postRemoteLogin(@Body nurse: Nurse): Nurse

    @GET("/nurse/id/1")
    suspend fun getRemoteFindById(): Nurse

    @POST("/nurse/update")
    suspend fun updateNurse(@Body nurse: Nurse, @Query("newName") name: String, @Query("newPassword") password: String): Response<Nurse>

    @DELETE("/nurse/delete/{id}")
    suspend fun deleteNurse(@Path("id") id: Int): Response<Unit>
}
