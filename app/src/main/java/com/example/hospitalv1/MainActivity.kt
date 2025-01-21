package com.example.hospitalv1

import android.os.Bundle
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
import com.example.hospitalv1.ui.screens.SearchScreen
import com.example.hospitalv1.ui.screens.NurseScreen
import com.example.hospitalv1.ui.screens.LoginScreen
import com.example.hospitalv1.ui.screens.RegisterScreen
import com.example.hospitalv1.ui.theme.HospitalV1Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Screen(val screen: String = "", val logged: Boolean = false)

class AppViewModel : ViewModel() {
    private val _currentScreen = MutableStateFlow(Screen())
    val currentScreen: StateFlow<Screen> get() = _currentScreen.asStateFlow()

    // formatear pantalla de login a no login
    init {
        _currentScreen.value = Screen("Login", false)
    }

    // despues de login actualizar estado
    fun loginSuccess() {
        _currentScreen.update { it.copy(screen = "Main", logged = true) }
    }

    // actualizar pantalla
    fun updateScreen(newScreen: String) {
        _currentScreen.update { it.copy(screen = newScreen) }
    }

    // verificar si el usuario se ha registado
    fun isLogged(): Boolean {
        return _currentScreen.value.logged
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

data class Nurse(val id: Int, val name: String, val password: String, val profilePictureUrl: String)

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