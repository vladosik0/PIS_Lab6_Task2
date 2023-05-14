package com.example.pis_lab6_task2

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pis_lab6_task2.data.Team
import com.example.pis_lab6_task2.data.TeamsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: TeamsApi
) : ViewModel() {

    private val _state = mutableStateOf(TeamState())
    val state: State<TeamState> = _state

    init{
        getSpecificTeam("1")
    }

    fun getRandomTeam() {
        viewModelScope.launch{
            try{
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(
                    team = api.getRandomTeam(),
                    isLoading = false
                )
            }
            catch(e: Exception){
                Log.e("MainViewModel", "getRandomTeam",e)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }
    fun getSpecificTeam(teamIndex:String){
        viewModelScope.launch{
            try{
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(
                    team = api.getSpecificTeam(teamIndex),
                    isLoading = false
                )
            }
            catch(e: Exception){
                Log.e("MainViewModel", "getSpecificTeam",e)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }
    data class TeamState(
        val team: Team? = null,
        val isLoading: Boolean = false
    )
}