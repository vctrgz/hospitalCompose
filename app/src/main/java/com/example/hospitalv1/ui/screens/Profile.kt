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
import com.example.hospitalv1.Nurse
import com.example.hospitalv1.nurses

@Composable
fun ProfileScreen(viewModel: AppViewModel) {
    val currentNurse = viewModel.loggedInNurse
    var name by remember { mutableStateOf(currentNurse?.name ?: "") }
    var password by remember { mutableStateOf(currentNurse?.password ?: "") }
    var updateSuccess by remember { mutableStateOf("") }

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

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                nurses.find { it.id == currentNurse.id }?.apply {
                    this.name = name
                    this.password = password
                }
                updateSuccess = "Profile updated successfully"
            },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Update Information")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                nurses.removeIf { it.id == currentNurse.id }
                viewModel.logout()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Unsubscribe", color = Color.White)
        }

        if (updateSuccess.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = updateSuccess, color = Color.Green)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.updateScreen("Main") }) {
            Text("Back")
        }
    }
}
