package com.example.hospitalv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hospitalv1.ui.theme.HospitalV1Theme
import com.example.hospitalv1.ui.screens.SearchScreen
import com.example.hospitalv1.ui.screens.NurseScreen
import com.example.hospitalv1.ui.screens.LoginScreen
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Screen (
    val screen : String = "",
    val logged: Boolean = false
){}
class AppViewModel:ViewModel(){
    private val _currentScreen = MutableStateFlow(Screen())
    val currentScreen: StateFlow<Screen> get()=_currentScreen.asStateFlow()
    init {
        _currentScreen.value= Screen("Main",false)
    }
    fun updateScreen(newScreen:String){
        _currentScreen.update { it.copy(screen = newScreen) }
    }
    fun getScreen():String{
        return _currentScreen.value.screen
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
  //  var currentScreen by remember { mutableStateOf<String>("Main") }
    val viewModel:AppViewModel= viewModel()
    when(viewModel.currentScreen.collectAsState().value.screen){
  //  when(currentScreen){
        "Main" -> MainScreen(viewModel
//            onShowNurses = {currentScreen = "Nurses"},
//            onShowLogin = {currentScreen = "Login"},
//            onShowSearch = {currentScreen = "Search"},
        )
        "Nurses" -> NurseScreen(viewModel)
        "Login" -> LoginScreen(viewModel)
        "Search" -> SearchScreen(viewModel)
    }
}
//            onShowNurses = {currentScreen = "Nurses"},
//            onShowLogin = {currentScreen = "Login"},
//            onShowSearch = {currentScreen = "Search"},
@Composable
fun MainScreen(viewModel: AppViewModel) {
    Column {
        Row {
            Button(onClick = {viewModel.updateScreen("Nurses")}) {
                Text(
                    text = "Nurses information"
                )
            }
            Button(onClick = {viewModel.updateScreen("Login")}) {
                Text(
                    text = "Login"
                )
            }
            Button(onClick = {viewModel.updateScreen("Search")}) {
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