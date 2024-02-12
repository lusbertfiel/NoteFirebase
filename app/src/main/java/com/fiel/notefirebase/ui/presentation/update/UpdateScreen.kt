package com.fiel.notefirebase.ui.presentation.update

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fiel.notefirebase.Screen
import com.fiel.notefirebase.ui.domain.model.DataResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(navController: NavHostController,viewModel: UpdateViewModel= hiltViewModel()) {
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.shadow(2.dp),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack(route = Screen.Home.route,inclusive = false)
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            },
            title = {
                Text(text = "Update Note")
            })
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Titulo") },
                value = viewModel.titulo,
                onValueChange = {
                viewModel.titulo=it
                })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Contenido") },
                minLines = 3,
                value = viewModel.contenido,
                onValueChange = {
                        viewModel.contenido=it
                })
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.updateNote()
                    navController.popBackStack(route = Screen.Home.route,inclusive = false)
                }) {
                Text(text = "UPDATE NOTE")
            }
        }
        when(val response=viewModel.updateResponse){
            is DataResponse.Failure -> {
                Toast.makeText(LocalContext.current, response.exception?.message, Toast.LENGTH_SHORT).show()
            }
            DataResponse.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            is DataResponse.Success -> {
                Toast.makeText(LocalContext.current, "Datos actualizados", Toast.LENGTH_SHORT).show()
                viewModel.updateResponse=null
            }
            null -> {
            }
        }
    }
}