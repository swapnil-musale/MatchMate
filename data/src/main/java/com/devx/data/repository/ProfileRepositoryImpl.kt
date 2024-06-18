package com.devx.data.repository

import com.devx.data.dataSource.remote.ProfileApi
import com.devx.data.utils.network.safeApiCall
import com.devx.domain.model.ProfileMatch
import com.devx.domain.repository.ProfileRepository
import com.devx.domain.util.NetworkResponse
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileApi: ProfileApi): ProfileRepository {
    override suspend fun getMatches(count: Int): NetworkResponse<List<ProfileMatch>> {
        return safeApiCall { profileApi.getProfileMatches(count = count) }
    }
}