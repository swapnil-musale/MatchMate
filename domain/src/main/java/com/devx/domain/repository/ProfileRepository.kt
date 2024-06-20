package com.devx.domain.repository

import com.devx.domain.model.ProfileMatch
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getMatches(count: Int, fromNetwork: Boolean): Flow<List<ProfileMatch>>

    suspend fun updateProfileMatchStatus(
        userId: String,
        status: Int,
    )
}
