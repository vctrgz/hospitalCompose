package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospitalv1.nurses

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
                loginError = "Login successful"
                // Implement successful login action (e.g., navigate to another screen)
            } else {
                loginError = "Invalid credentials. Please try again."
            }
        }) {
            Text(text = "Login")
        }
        Text(
            text = loginError,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )

    }
}
