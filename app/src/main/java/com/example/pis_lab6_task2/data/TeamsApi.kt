package com.example.pis_lab6_task2.data

import retrofit2.http.GET
import retrofit2.http.Query

interface TeamsApi {
    @GET("/randomteam")
    suspend fun getRandomTeam():Team

    @GET("/specificteam/{teamIndex}")
    suspend fun getSpecificTeam(@Query("teamIndex") teamIndex:String):Team

    companion object{
        const val BASE_URL = "http://192.168.1.6:8080"
    }
}
