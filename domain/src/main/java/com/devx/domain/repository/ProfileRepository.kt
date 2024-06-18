package com.devx.domain.repository

import com.devx.domain.model.MatchProfile
import com.devx.domain.util.NetworkResponse

interface ProfileRepository {
    suspend fun getProfiles(): NetworkResponse<MatchProfile>
}
