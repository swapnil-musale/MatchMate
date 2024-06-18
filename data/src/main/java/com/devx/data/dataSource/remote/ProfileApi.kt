package com.devx.data.dataSource.remote

import com.devx.data.model.ProfileMatchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {
    @GET("api/")
    suspend fun getProfileMatches(@Query("results") count: Int): Response<ProfileMatchDto>
}
