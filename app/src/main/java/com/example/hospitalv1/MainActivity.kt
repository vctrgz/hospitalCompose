package com.example.hospitalv1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hospitalv1.ui.remote.Nurse
import com.example.hospitalv1.ui.remote.RemoteInterface
import com.example.hospitalv1.ui.remote.RemoteNurseUiState
import com.example.hospitalv1.ui.screens.SearchScreen
import com.example.hospitalv1.ui.screens.NurseScreen
import com.example.hospitalv1.ui.screens.LoginScreen
import com.example.hospitalv1.ui.screens.RegisterScreen
import com.example.hospitalv1.ui.theme.HospitalV1Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.hospitalv1.ui.remote.RemoteProfileUiState
import com.example.hospitalv1.ui.screens.ProfileScreen
import com.example.hospitalv1.ui.remote.RemoteNurseListState
import com.example.hospitalv1.ui.remote.RemoteRegisterUiState
import com.example.hospitalv1.ui.remote.RemoteSearchUiState


data class Screen(val screen: String = "", val logged: Boolean = false)

class AppViewModel : ViewModel() {
    var remoteNurseUiState: RemoteNurseUiState by mutableStateOf(RemoteNurseUiState.Cargant)
        private set
    var remoteProfileUiState: RemoteProfileUiState by mutableStateOf(RemoteProfileUiState.Cargant)
        private set
    var remoteRegisterUiState: RemoteRegisterUiState by mutableStateOf(RemoteRegisterUiState.Cargant)
        private set
    var remoteSearchUiState: RemoteSearchUiState by mutableStateOf(RemoteSearchUiState.Cargant)
        private set
    var remoteNurseListState: RemoteNurseListState by mutableStateOf(RemoteNurseListState.Cargant)
        private set
    val connection = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RemoteInterface::class.java)
    private val _currentScreen = MutableStateFlow(Screen())
    val currentScreen: StateFlow<Screen> get() = _currentScreen.asStateFlow()
    private val _loggedInNurse = MutableStateFlow<Nurse?>(null)
    val loggedInNurse: StateFlow<Nurse?> get() = _loggedInNurse.asStateFlow()

    // formatear pantalla de login a no login
    init {
        _currentScreen.value = Screen("Login", false)
    }

    // despues de login actualizar estado
    fun loginSuccess() {
        _currentScreen.update { it.copy(screen = "Main", logged = true) }
    }
    
    fun registerSuccess() {
        _currentScreen.update { it.copy(screen = "Login") }
    }
    
    fun postRemoteLogin(name: String, password: String) {
        viewModelScope.launch{
            remoteNurseUiState=RemoteNurseUiState.Cargant
            try {
                val answer = connection.postRemoteLogin(Nurse( name = name, password = password))
                Log.d("Login", "RESPUESTA ${answer}")
                remoteNurseUiState= RemoteNurseUiState.Success(answer)
                _loggedInNurse.update { answer }
            }catch (e: Exception){
                Log.d("Login", "RESPUESTA ERROR ${e.message}${e.printStackTrace()}")
                remoteNurseUiState = RemoteNurseUiState.Error
            }
        }
    }
    fun postRemoteRegister(name: String, password: String) {
        viewModelScope.launch{
            remoteRegisterUiState=RemoteRegisterUiState.Cargant
            try {
                val answer = connection.postRemoteRegister(Nurse( name = name, password = password))
                Log.d("Register", "RESPUESTA ${answer}")
                remoteRegisterUiState= RemoteRegisterUiState.Success(answer)
            }catch (e: Exception){
                Log.d("Register", "RESPUESTA ERROR ${e.message}${e.printStackTrace()}")
                remoteRegisterUiState = RemoteRegisterUiState.Error
            }
        }
    }
    fun getRemoteFindByNameAndId(response: String) {
        viewModelScope.launch{
            remoteSearchUiState=RemoteSearchUiState.Cargant
            try {
                val answer = connection.getRemoteAllNurses()
                Log.d("Search", "RESPUESTA ${answer}")
                val filteredList = if (response.toIntOrNull() != null) {
                    answer.filter { it.id.toString() == response } // Filtrar por ID
                } else {
                    answer.filter { it.name.contains(response, ignoreCase = true) } // Filtrar por nombre
                }
                remoteSearchUiState= RemoteSearchUiState.Success(filteredList)
            }catch (e: Exception){
                Log.d("Search", "RESPUESTA ERROR ${e.message}${e.printStackTrace()}")
                remoteSearchUiState = RemoteSearchUiState.Error
            }
        }
    }

    fun fetchAllNurses() {
        viewModelScope.launch {
            remoteNurseListState = RemoteNurseListState.Cargant
            try {
                val nurses = connection.getRemoteAllNurses()
                Log.d("Nurses", "Fetched nurses: $nurses")
                remoteNurseListState = RemoteNurseListState.Success(nurses)
            } catch (e: Exception) {
                Log.e("Nurses", "Error fetching nurses: ${e.message}")
                remoteNurseListState = RemoteNurseListState.Error
            }
        }
    }
    

    fun updateNurseInfo(id: Int, name: String, password: String) {
        viewModelScope.launch {
            remoteProfileUiState=RemoteProfileUiState.Cargant
            try {
                val nurse = Nurse(id = id, name = name, password = password)
                val response = connection.updateNurse(nurse, name = name, password = password)
                _loggedInNurse.update { nurse } // Actualiza el enfermero en el estado
                Log.d("Update", "RESPUESTA ${response}")
                remoteProfileUiState=RemoteProfileUiState.Success(nurse)
            } catch (e: Exception) {
                Log.d("Update", "RESPUESTA ERROR ${e.message}${e.printStackTrace()}")
                remoteProfileUiState=RemoteProfileUiState.Error
            }
        }
    }

    fun deleteNurse(id: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = connection.deleteNurse(id)

                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            } catch (e: Exception) {
                callback(false)
            }
        }
    }


    // actualizar pantalla
    fun updateScreen(newScreen: String) {
        _currentScreen.update { it.copy(screen = newScreen) }
    }

    // verificar si el usuario se ha registado
    fun isLogged(): Boolean {
        return _currentScreen.value.logged
    }

    fun logout() {
        _loggedInNurse.update { null }  // Elimina el enfermero logueado
        _currentScreen.update { it.copy(screen = "Login", logged = false) }
    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HospitalV1Theme {
                MyApp()
            }
        }
    }
}

// modificar usuario de list a mutable para modificarlo
val nurses = mutableListOf(
    Nurse(1, "Juan", "password123", "https://static.nationalgeographicla.com/files/styles/image_3200/public/3897187267_f36b5e4e7a_c.webp?w=1600&h=1200"),
    Nurse(2, "Carlos", "securePass456", "https://static.nationalgeographicla.com/files/styles/image_3200/public/3897187267_f36b5e4e7a_c.webp?w=1600&h=1200"),
    Nurse(3, "Miguel", "qwerty789", "https://static.nationalgeographicla.com/files/styles/image_3200/public/3897187267_f36b5e4e7a_c.webp?w=1600&h=1200"),
    Nurse(4, "Javier", "abc123", "https://static.nationalgeographicla.com/files/styles/image_3200/public/3897187267_f36b5e4e7a_c.webp?w=1600&h=1200"),
    Nurse(5, "Carlos", "wyz789", "https://static.nationalgeographicla.com/files/styles/image_3200/public/3897187267_f36b5e4e7a_c.webp?w=1600&h=1200"),
    Nurse(6, "Marcos", "pass1234", "https://static.nationalgeographicla.com/files/styles/image_3200/public/3897187267_f36b5e4e7a_c.webp?w=1600&h=1200")
)

@Composable
fun MyApp() {
    val viewModel: AppViewModel = ViewModelProvider(
        LocalContext.current as ComponentActivity
    ).get(AppViewModel::class.java)

    val currentScreen = viewModel.currentScreen.collectAsState().value

    if (!currentScreen.logged) {
        when (currentScreen.screen) {
            "Register" -> RegisterScreen(viewModel)
            else -> LoginScreen(viewModel)
        }
    } else {
        when (currentScreen.screen) {
            "Main" -> MainScreen(viewModel)
            "Nurses" -> NurseScreen(viewModel)
            "Search" -> SearchScreen(viewModel)
            "Profile" -> ProfileScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: AppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to the Hospital System",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { viewModel.updateScreen("Nurses") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "View Nurses Information", fontSize = 18.sp, color = Color.White)
        }

        Button(
            onClick = { viewModel.updateScreen("Search") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Search Nurse", fontSize = 18.sp, color = Color.White)
        }

        Button(
            onClick = { viewModel.updateScreen("Profile") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Nurse Profile", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun ElementColumn(text:String){
    Text(
        text = " -$text",
        fontSize= 20.sp,
        modifier= Modifier.padding(bottom = 20.dp))
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    HospitalV1Theme {
        MyApp() // Mostrar pantalla principal en la vista previa
    }
}
