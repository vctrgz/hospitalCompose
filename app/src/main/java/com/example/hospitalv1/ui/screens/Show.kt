package com.example.hospitalv1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hospitalv1.ElementColumn
import com.example.hospitalv1.nurses

@Composable
fun NurseScreen(onBack: () -> Unit){
    Column {
        Button(onClick = onBack) {
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

        LazyColumn(modifier= Modifier) {
            items(items= nurses){
                    nurse->ElementColumn(nurse.toString())
            }
        }
    }
}
