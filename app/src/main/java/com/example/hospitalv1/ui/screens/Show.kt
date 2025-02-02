package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hospitalv1.AppViewModel
import com.example.hospitalv1.ElementColumn
import com.example.hospitalv1.nurses
import com.example.hospitalv1.ui.remote.RemoteNurseListState

@Composable
fun NurseScreen(viewModel: AppViewModel) {
    var showAllNurses by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Alineación al centro vertical
    ) {
        Button(
            onClick = { viewModel.updateScreen("Main") },
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(text = "Back")
        }

        Button(
            onClick = {
                showAllNurses = !showAllNurses
                if (showAllNurses) {
                    viewModel.fetchAllNurses()
                }
            }
        ) {
            Text(text = if (showAllNurses) "Hide Nurses" else "Show All Nurses")
        }

        if (showAllNurses) {
            ShowAllNursesInformation(viewModel)
        }
    }
}



@Composable
fun ShowAllNursesInformation(viewModel: AppViewModel) {
    val nurseListState = viewModel.remoteNurseListState

    LaunchedEffect(Unit) {
        viewModel.fetchAllNurses()
    }

    Column {
        Text(
            text = "ALL NURSES INFORMATION:",
            modifier = Modifier.padding(20.dp)
        )

        when (nurseListState) {
            is RemoteNurseListState.Cargant -> {
                Text(text = "Loading nurses...", color = Color.Gray)
            }

            is RemoteNurseListState.Success -> {
                LazyColumn {
                    items(nurseListState.nurses) { nurse ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 8.dp, bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Imagen de perfil
                            AsyncImage(
                                model = nurse.profilePictureUrl,
                                contentDescription = "Profile Picture of ${nurse.name}",
                                modifier = Modifier
                                    .size(60.dp) // Tamaño de la imagen
                                    .padding(end = 16.dp),
                                contentScale = ContentScale.Crop
                            )

                            // Información del enfermero
                            Column {
                                Text(text = "ID: ${nurse.id}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Name: ${nurse.name}", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }

            is RemoteNurseListState.Error -> {
                Text(text = "Error fetching nurses", color = Color.Red)
            }
        }
    }
}




