package com.example.hospitalv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospitalv1.ui.theme.HospitalV1Theme
import com.example.hospitalv1.ui.screens.SearchScreen
import com.example.hospitalv1.ui.screens.NurseScreen
import com.example.hospitalv1.ui.screens.LoginScreen
import kotlin.text.contains

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

sealed class Screen {
    object Main : Screen()
    object Nurses : Screen()
    object Login : Screen()
    object Search : Screen()
}

data class Nurse(val id: Int, val name: String, val password: String)

val nurses = listOf(
    Nurse(1, "Juan", "password123"),
    Nurse(2, "Carlos", "securePass456"),
    Nurse(3, "Miguel", "qwerty789"),
    Nurse(4, "Javier", "abc123"),
    Nurse(5, "Carlos", "wyz789"),
    Nurse(6, "Marcos", "pass1234")
)

@Composable
fun MyApp(){
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Main) }
    when(currentScreen){
        Screen.Main -> MainScreen(
            onShowNurses = {currentScreen = Screen.Nurses},
            onShowLogin = {currentScreen = Screen.Login},
            onShowSearch = {currentScreen = Screen.Search},
        )
        Screen.Nurses -> NurseScreen(onBack = { currentScreen = Screen.Main})
        Screen.Login -> LoginScreen(onBack = { currentScreen = Screen.Main})
        Screen.Search -> SearchScreen(onBack = { currentScreen = Screen.Main})
    }
}

@Composable
fun MainScreen(onShowNurses: () -> Unit, onShowLogin: () -> Unit, onShowSearch: () -> Unit){
    Column {
        Row {
            Button(onClick = onShowNurses) {
                Text(
                    text = "Nurses information"
                )
            }
            Button(onClick = onShowLogin) {
                Text(
                    text = "Login"
                )
            }
            Button(onClick = onShowSearch) {
                Text(
                    text = "Search nurse"
                )
            }
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
        MyApp()
    }
}