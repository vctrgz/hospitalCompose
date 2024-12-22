package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hospitalv1.AppViewModel
import com.example.hospitalv1.nurses

@Composable
fun SearchScreen(viewModel: AppViewModel) {
    var searchNurse by remember { mutableStateOf("") }
    val filteredResults by remember(searchNurse) {
        derivedStateOf {
            if (searchNurse.isNotEmpty()) {
                nurses.filter {
                    it.name.contains(searchNurse, ignoreCase = true) || it.id.toString() == searchNurse
                }
            } else {
                emptyList()
            }
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

        // 搜索输入框
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

        // 搜索结果
        if (filteredResults.isNotEmpty()) {
            filteredResults.forEach { nurse ->
                Text(
                    text = "ID: ${nurse.id}, Name: ${nurse.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        } else if (searchNurse.isNotEmpty()) {
            Text(
                text = "No results found.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // 返回按钮
        Button(
            onClick = { viewModel.updateScreen("Main") },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = "Back", color = Color.White)
        }
    }
}

