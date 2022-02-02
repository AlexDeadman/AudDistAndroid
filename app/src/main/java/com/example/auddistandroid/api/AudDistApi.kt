package com.example.auddistandroid.api

import com.example.auddistandroid.data.model.AuthToken
import com.example.auddistandroid.data.model.DisciplineList
import com.example.auddistandroid.data.model.LecturerList
import com.example.auddistandroid.data.model.ScheduleList
import okhttp3.RequestBody
import retrofit2.http.*

interface AudDistApi {
    companion object {
        const val CONTENT_TYPE = "Content-Type: application/vnd.api+json"
    }

    @Headers(CONTENT_TYPE)
    @POST("auth/token/login/")
    suspend fun getToken(@Body requestBody: RequestBody): AuthToken

    @Headers(CONTENT_TYPE)
    @GET("api/lecturers/")
    suspend fun getLecturers(@Header("Authorization") authToken: String): LecturerList

    @Headers(CONTENT_TYPE)
    @GET("api/disciplines/")
    suspend fun getDisciplines(@Header("Authorization") authToken: String): DisciplineList

    @Headers(CONTENT_TYPE)
    @GET("api/schedules/")
    suspend fun getSchedules(@Header("Authorization") authToken: String): ScheduleList
}