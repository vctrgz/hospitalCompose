package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospitalv1.AppViewModel
import com.example.hospitalv1.R
import com.example.hospitalv1.nurses
import com.example.hospitalv1.ui.remote.RemoteNurseUiState


@Composable
fun LoginScreen(viewModel: AppViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }
    var loginSucceed by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val remoteNurseUiState = viewModel.remoteNurseUiState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Hospital Icon
        Image(
            painter = painterResource(id = R.drawable.hospital_icon), // 替换为你的资源
            contentDescription = "Hospital Icon",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )

        // Title
        Text(
            text = "Login to Your Account",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Username Input
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Enter your username") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Password Input
        TextField(  
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(
                            if (showPassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Login Button
        Button(
            onClick = {
                viewModel.postRemoteLogin(username, password)
                when (remoteNurseUiState) {
                    is RemoteNurseUiState.Cargant -> loginSucceed = "Login correct"
                    is RemoteNurseUiState.Error -> loginError = "Invalid credentials. Please try again."
                    is RemoteNurseUiState.Success-> viewModel.loginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Login", fontSize = 18.sp, color = Color.White)
        }

        // Register Button
        TextButton(onClick = { viewModel.updateScreen("Register") }) {
            Text(text = "Don't have an account? Register", color = MaterialTheme.colorScheme.primary)
        }

        // Error Message
        if (loginError.isNotEmpty()) {
            Text(
                text = loginError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

