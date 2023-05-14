package com.example.pis_lab6_task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.pis_lab6_task2.ui.theme.PIS_Lab6_Task2Theme
import com.example.pis_lab6_task2.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalCoilApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PIS_Lab6_Task2Theme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    val viewModel: MainViewModel = hiltViewModel()
                    val team = viewModel.state.value.team
                    var currentTeamIndex = remember {mutableStateOf("1")}
                    val isLoading = viewModel.state.value.isLoading
                    Text(text = "Spanish Elite Division 2022/2023 team stats", fontSize = 15.sp)
                    Spacer(Modifier.height(2.dp))
                    OutlinedTextField (
                        value = currentTeamIndex.value,
                        onValueChange = { currentTeamIndex.value = it },
                        singleLine = true,
                        label = {Text(text = "Team position from 1 to 20")},
                        isError = currentTeamIndex.value.toIntOrNull() ?: 0 < 1 ||
                                currentTeamIndex.value.toIntOrNull() ?: 21 > 20
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    team?.let{
                        Image(painter = rememberImagePainter(
                            data = team.imageUrl,
                            builder = {crossfade(true)}
                        ),
                            contentDescription = team.name
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = team.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = team.description)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Button(
                        onClick = {
                            viewModel.getSpecificTeam(currentTeamIndex.value)
                                  },
                        modifier = Modifier.align(Alignment.End)
                    ){
                        Text(text = "Get team!")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if(isLoading){
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

