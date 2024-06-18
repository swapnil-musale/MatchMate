package com.devx.domain.repository

import com.devx.domain.model.ProfileMatch
import com.devx.domain.util.NetworkResponse

interface ProfileRepository {
    suspend fun getMatches(count: Int): NetworkResponse<List<ProfileMatch>>
}
