package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.example.hospitalv1.ui.remote.RemoteNurseUiState
import com.example.hospitalv1.ui.remote.RemoteSearchUiState

@Composable
fun SearchScreen(viewModel: AppViewModel) {
    val remoteSearchUiState = viewModel.remoteSearchUiState
    var searchError by remember { mutableStateOf("") }
    var searchSucceed by remember { mutableStateOf("") }
    var searchNurse by remember { mutableStateOf("") }
    var filteredResults by remember { mutableStateOf(emptyList<Nurse>()) }
//    val filteredResults by remember(searchNurse) {
//        derivedStateOf {
//            viewModel.getRemoteFindByNameAndId(searchNurse)
//            when (remoteSearchUiState) {
//                is RemoteSearchUiState.Cargant -> searchSucceed = "Login correct"
//                is RemoteSearchUiState.Error -> searchError = "Invalid credentials. Please try again."
//                is RemoteSearchUiState.Success-> viewModel.loginSuccess()
//            }
//        }
//    }

    // Llama a la búsqueda cuando el usuario escribe
    LaunchedEffect(searchNurse) {
        if (searchNurse.isNotEmpty()) {
            viewModel.getRemoteFindByNameAndId(searchNurse)
        }
    }

    // Actualiza los resultados cuando cambia el estado
    LaunchedEffect(remoteSearchUiState) {
        if (remoteSearchUiState is RemoteSearchUiState.Success) {
            filteredResults = remoteSearchUiState.nurses
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // 修改为垂直居中
    ) {
        Text(
            text = "Search for a Nurse",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Text field
        TextField(
            value = searchNurse,
            onValueChange = { searchNurse = it },
            label = { Text(text = "Search by Name or ID") },
            placeholder = { Text(text = "Enter nurse name or ID") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Query result
        if (filteredResults.isNotEmpty()) {
            filteredResults.forEach { nurse ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, top = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    //horizontalArrangement = Arrangement.Start
                ) {
                    // Imagen de perfil
                    AsyncImage(
                        model = nurse.profilePictureUrl
                            ?: "https://www.example.com/default-image.png",
                        contentDescription = "Profile Picture of ${nurse.name}",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 16.dp),
                        contentScale = ContentScale.Crop, // Recorta la imagen de forma cuadrada
                        onError = {},
                        onLoading = {}
                    )
                    Text(
                        text = "ID: ${nurse.id}, Name: ${nurse.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        } else if (searchNurse.isNotEmpty()) {
            Text(
                text = "No results found.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Back button
        Button(
            onClick = { viewModel.updateScreen("Main") },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = "Back", color = Color.White)
        }
    }
}

