package com.devx.domain.repository

import com.devx.domain.model.ProfileMatch
import com.devx.domain.util.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getMatchesFromNetwork(count: Int): NetworkResponse<List<ProfileMatch>>

    suspend fun getMatchesFromLocal(count: Int): Flow<List<ProfileMatch>>

    suspend fun updateProfileMatchStatus(
        userId: String,
        status: Int,
    )
}
