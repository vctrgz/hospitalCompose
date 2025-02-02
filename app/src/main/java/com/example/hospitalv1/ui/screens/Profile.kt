package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hospitalv1.AppViewModel
import com.example.hospitalv1.nurses
import com.example.hospitalv1.ui.remote.Nurse


@Composable
fun ProfileScreen(viewModel: AppViewModel) {
    val currentNurse = viewModel.loggedInNurse.collectAsState().value
    var name by remember { mutableStateOf(currentNurse?.name ?: "") }
    var password by remember { mutableStateOf(currentNurse?.password ?: "") }
    var updateSuccess by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Si el enfermero no está logueado, redirigimos a la pantalla de login
    if (currentNurse == null) {
        viewModel.updateScreen("Login")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profile", style = MaterialTheme.typography.headlineMedium)

        AsyncImage(
            model = currentNurse.profilePictureUrl,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Gray, RoundedCornerShape(50)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para modificar el nombre
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para modificar la contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para actualizar la información
        Button(
            onClick = {
                if (name.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.updateNurseInfo(currentNurse.id, name, password) { result ->
                        if (result) {
                            updateSuccess = "Profile updated successfully"
                            errorMessage = ""
                        } else {
                            updateSuccess = ""
                            errorMessage = "Failed to update profile"
                        }
                    }
                } else {
                    errorMessage = "Please fill out both fields"
                    updateSuccess = ""
                }
            },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Update Information")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para eliminar al enfermero
        Button(
            onClick = {
                viewModel.deleteNurse(currentNurse.id) { result ->
                    if (result) {
                        viewModel.logout()
                        updateSuccess = "Successfully unsubscribed"
                    } else {
                        errorMessage = "Failed to unsubscribe"
                        updateSuccess = ""
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Unsubscribe", color = Color.White)
        }

        // Mostrar mensaje de éxito
        if (updateSuccess.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = updateSuccess, color = Color.Green)
        }

        // Mostrar mensaje de error
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para regresar a la pantalla principal
        Button(onClick = { viewModel.updateScreen("Main") }) {
            Text("Back")
        }
    }
}
