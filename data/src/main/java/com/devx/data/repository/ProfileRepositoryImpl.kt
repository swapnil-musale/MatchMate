package com.devx.data.repository

import com.devx.data.dataSource.local.dao.ProfileDao
import com.devx.data.dataSource.local.entity.toDomain
import com.devx.data.dataSource.local.entity.toEntity
import com.devx.data.dataSource.remote.ProfileApi
import com.devx.data.utils.network.safeApiCall
import com.devx.domain.model.ProfileMatch
import com.devx.domain.repository.ProfileRepository
import com.devx.domain.util.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val profileApi: ProfileApi,
        private val profileDao: ProfileDao,
    ) : ProfileRepository {
        override suspend fun getMatchesFromNetwork(count: Int): NetworkResponse<List<ProfileMatch>> {
            val response = safeApiCall { profileApi.getProfileMatches(count = count) }
            response.data?.map { it.toEntity() }?.let {
                profileDao.insertProfiles(it)
            }

            return response
        }

        override suspend fun getMatchesFromLocal(count: Int): Flow<List<ProfileMatch>> =
            profileDao
                .getAllMatches()
                .map {
                    it.map { profileMatch ->
                        profileMatch.toDomain()
                    }
                }

        override suspend fun updateProfileMatchStatus(
            userId: String,
            status: Int,
        ) {
            profileDao.updateStatus(userId = userId, status = status)
        }
    }
