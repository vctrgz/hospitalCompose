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
fun ElementColumn(text:String){
    Text(
        text = " -$text",
        fontSize= 20.sp,
        modifier= Modifier.padding(bottom = 20.dp))
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
fun LoginScreen(onBack: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }

    Column {
        Button(onClick = onBack) {
            Text(text = "Back")
        }

        Text(text = "Login", fontSize = 24.sp, modifier = Modifier.padding(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Button(onClick = {
            val nurse = nurses.find { it.name.equals(username, ignoreCase = true) && it.password == password }
            if (nurse != null) {
                loginError = ""
                // Implement successful login action (e.g., navigate to another screen)
            } else {
                loginError = "Invalid credentials. Please try again."
            }
        }) {
            Text(text = "Login")
        }

        if (loginError.isNotEmpty()) {
            Text(
                text = loginError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun SearchScreen(onBack: () -> Unit){
    var searchNurse by remember { mutableStateOf("") }
    var showResults by remember { mutableStateOf(emptyList<Nurse>()) }
    Column {
        Button(onClick = onBack) {
            Text(
                text = "Back"
            )
        }
        TextField(
            value = searchNurse,
            onValueChange = { searchNurse = it },
            label = { Text(text = "Search Nurse by Name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        Button(onClick = {
            //showResults = nurses.filter { it.name.contains(searchNurse, ignoreCase = true) }
            showResults = nurses.filter { it.name.equals(searchNurse) }
        }) {
            Text(text = "Search")
        }
        if (showResults.isNotEmpty()) {
            Text(
                text = "Search Results:",
                modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 18.sp
            )
            LazyColumn(modifier= Modifier) {
                items(items= showResults){
                        nurse->ElementColumn(nurse.toString())
                }
            }
        } else if (searchNurse.isNotEmpty()) {
            Text(
                text = "No nurses found with the name \"$searchNurse\".",
            )
        }
    }
}


@Composable
fun ShowAllNursesInformation(){
    Column {
        Text(
            text = "ALL NURSES INFORMATION:",
            modifier = Modifier.padding(20.dp)
        )

        LazyColumn(modifier= Modifier) {
            items(items= nurses){
                    nurse->ElementColumn(nurse.toString())
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