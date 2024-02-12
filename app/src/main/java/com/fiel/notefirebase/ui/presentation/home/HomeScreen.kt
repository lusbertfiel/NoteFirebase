package com.fiel.notefirebase.ui.presentation.home


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fiel.notefirebase.Screen
import com.fiel.notefirebase.ui.domain.model.DataResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController,viewModel: HomeViewModel= hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(2.dp),
                title = { Text(text = "My Notes with Firestore") })
        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.Add.route){
                    launchSingleTop=true
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) {

        when(val response=viewModel.notesReponse){
            is DataResponse.Failure -> {
                Toast.makeText(LocalContext.current, response.exception?.message, Toast.LENGTH_SHORT).show()
            }
            DataResponse.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            is DataResponse.Success -> {
                if (response.data.isNotEmpty()){
                    LazyColumn(modifier = Modifier.padding(it)) {
                        items(response.data) {
                            Card(
                                modifier = Modifier
                                    .padding(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xffCFD8F1))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = "#${response.data.indexOf(it)+1}", color = Color.Gray)
                                        Spacer(modifier = Modifier.weight(1f))
                                        IconButton(onClick = {
                                            navController.navigate(Screen.Update.sendNota(it.toJson())){
                                                launchSingleTop=true
                                            }
                                        }) {
                                            Icon(
                                                tint = Color.Yellow,
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = ""
                                            )
                                        }
                                        IconButton(onClick = {
                                            viewModel.deleteNote(it)
                                        }) {

                                            Icon(
                                                tint = Color.Red.copy(0.5f),
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                    Text(
                                        text = it.titulo,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text =it.contenido)
                                }
                            }
                        }

                    }
                }else{
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text("Aun no tienes notas agregadas")
                    }
                }
            }
            null -> {}
        }

        when(val resDelete=viewModel.deleteResponse){
            is DataResponse.Failure -> {
                Toast.makeText(LocalContext.current, resDelete.exception?.message, Toast.LENGTH_SHORT).show()
            }
            DataResponse.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            is DataResponse.Success -> {
                Toast.makeText(LocalContext.current, "Se elimino la nota ", Toast.LENGTH_SHORT).show()
                viewModel.deleteResponse=null
            }
            null -> {
            }
        }
    }

}
