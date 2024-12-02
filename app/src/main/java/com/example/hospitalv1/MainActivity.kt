package com.example.hospitalv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospitalv1.ui.theme.HospitalV1Theme

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
@Composable
fun MyApp(){
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Main) }
    when(currentScreen){
        Screen.Main -> MainScreen(
            onShowNurses = {currentScreen = Screen.Nurses},
            onShowWelcome = {currentScreen = Screen.Welcome}
        )
        Screen.Nurses -> NurseScreen(onBack = { currentScreen = Screen.Main})
        Screen.Welcome -> WelcomeScreen(onBack = { currentScreen = Screen.Main})
    }
}
sealed class Screen {
    object Main : Screen()
    object Nurses : Screen()
    object Welcome : Screen()
}

@Composable
fun ElementColumna(text:String){
    Text(
        text = " -$text",
        fontSize= 20.sp,
        modifier= Modifier.padding(bottom = 20.dp))
}
@Composable
fun MainScreen(onShowNurses: () -> Unit, onShowWelcome: () -> Unit){
    Column {
        Row {
            Button(onClick = onShowNurses) {
                Text(
                    text = "Nurses information"
                )
            }
            Button(onClick = onShowWelcome) {
                Text(
                    text = "Welcome message"
                )
            }
        }
    }
}

@Composable
fun NurseScreen(onBack: () -> Unit){
    Column {
        Button(onClick = onBack) {
            Text(text = "Back")
        }
        var showAllNurses by remember { mutableStateOf(false) }
        Button(
            onClick = {
                if (showAllNurses){
                showAllNurses = false
                }else{
                    showAllNurses = true
                }
            }
        ) {
            Text(
                text = "Show all nurses"
            )
        }
        if (showAllNurses){
            ShowAllNursesInformation()
        }
    }
}

@Composable
fun WelcomeScreen(onBack: () -> Unit){
    Column {
        Button(onClick = onBack) {
            Text(
                text = "Back"
            )
        }
        Text(
            text = "Bienvenido"
        )
    }
}
var nurses = listOf("Juan", "Carlos", "Miguel", "Javier", "Marcos")
@Composable
fun ShowAllNursesInformation(){
    Column {
        Text(
            text = "ALL NURSES INFORMATION:",
            modifier = Modifier.padding(20.dp)
        )

        LazyColumn(modifier= Modifier) {
            items(items= nurses){
                    nurse->ElementColumna(nurse)
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun ShowAllNursesInformationPreview() {
//    HospitalV1Theme {
//        showAllNursesInformation()
//    }
//}
@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    HospitalV1Theme {
        MyApp()
    }
}