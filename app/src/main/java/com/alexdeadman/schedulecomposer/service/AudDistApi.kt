package com.alexdeadman.schedulecomposer.service

import com.alexdeadman.schedulecomposer.data.model.DataList
import com.alexdeadman.schedulecomposer.data.model.auth.AuthToken
import com.alexdeadman.schedulecomposer.data.model.entity.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AudDistApi {

    @POST("/auth/token/login/")
    suspend fun getToken(@Body requestBody: RequestBody): AuthToken

    @GET("/api/classrooms/")
    suspend fun getClassrooms(): DataList<Classroom>

    @GET("/api/directions/")
    suspend fun getDirections(): DataList<Direction>

    @GET("/api/disciplines/")
    suspend fun getDisciplines(): DataList<Discipline>

    @GET("/api/groups/")
    suspend fun getGroups(): DataList<Group>

    @GET("/api/lecturers/")
    suspend fun getLecturers(): DataList<Lecturer>

    @GET("/api/schedules/")
    suspend fun getSchedule(): DataList<Schedule>

    @GET("/api/syllabuses/")
    suspend fun getSyllabuses(): DataList<Syllabus>

}