package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospitalv1.AppViewModel
import com.example.hospitalv1.ElementColumn
import com.example.hospitalv1.Nurse
import com.example.hospitalv1.nurses

@Composable
fun SearchScreen(viewModel: AppViewModel){
    var searchNurse by remember { mutableStateOf("") }
    var showResults by remember { mutableStateOf(emptyList<Nurse>()) }
    Column {
        Button(onClick = {viewModel.updateScreen("Main")}) {
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
