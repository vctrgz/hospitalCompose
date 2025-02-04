package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospitalv1.AppViewModel
//import com.example.hospitalv1.Nurse
import com.example.hospitalv1.nurses
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.example.hospitalv1.ui.remote.Nurse
import com.example.hospitalv1.ui.remote.RemoteNurseUiState
import com.example.hospitalv1.ui.remote.RemoteRegisterUiState

@Composable
fun RegisterScreen(viewModel: AppViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var registerError by remember { mutableStateOf("") }
    var registerSucceed by remember { mutableStateOf("") }
    val remoteRegisterUiState = viewModel.remoteRegisterUiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create an Account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Enter your username") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (username.isNotBlank() && password.isNotBlank()) {
//                    if (nurses.any { it.name == username }) {
//                        registrationError = "Username already exists. Please try another."
//                    } else {
//                        val newNurse = Nurse(id = nurses.size + 1, name = username, password = password, profilePictureUrl = "https://static.nationalgeographicla.com/files/styles/image_3200/public/3897187267_f36b5e4e7a_c.webp?w=1600&h=1200")
//                        nurses.add(newNurse)
//                        viewModel.updateScreen("Login")
//                    }
                    viewModel.postRemoteRegister(username, password)
                    when (remoteRegisterUiState) {
                        is RemoteRegisterUiState.Cargant -> registerSucceed = "Register correct"
                        is RemoteRegisterUiState.Error -> registerError = "This name already exists, please try another name."
                        is RemoteRegisterUiState.Success-> viewModel.registerSuccess()
                    }
                } else {
                    registerError = "Please fill in both fields."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Register", fontSize = 16.sp)
        }

        TextButton(
            onClick = { viewModel.updateScreen("Login") },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Back to Login", color = MaterialTheme.colorScheme.primary)
        }

        if (registerError.isNotEmpty()) {
            Text(
                text = registerError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
