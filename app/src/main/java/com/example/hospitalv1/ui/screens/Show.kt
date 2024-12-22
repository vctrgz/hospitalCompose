package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.hospitalv1.AppViewModel
import com.example.hospitalv1.ElementColumn
import com.example.hospitalv1.nurses
import coil.compose.AsyncImage

@Composable
fun NurseScreen(viewModel: AppViewModel){
    Column {
        Button(onClick = {viewModel.updateScreen("Main")}) {
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
fun ShowAllNursesInformation(){
    Column {
        Text(
            text = "ALL NURSES INFORMATION:",
            modifier = Modifier.padding(20.dp)
        )

        LazyColumn(modifier = Modifier) {
            items(items = nurses) { nurse ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Imagen de perfil
                    AsyncImage(
                        model = nurse.profilePictureUrl ?: "https://www.example.com/default-image.png",
                        contentDescription = "Profile Picture of ${nurse.name}",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 16.dp),
                        contentScale = ContentScale.Crop, // Recorta la imagen de forma cuadrada
                        onError = {},
                        onLoading = {}


                    )

                    // Información del enfermero
                    Column {
                        Text(
                            text = "ID: ${nurse.id}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                        Text(
                            text = "Name: ${nurse.name}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                        Text(
                            text = "Password: ${nurse.password}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

