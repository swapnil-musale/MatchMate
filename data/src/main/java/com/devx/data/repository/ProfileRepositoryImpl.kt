package com.devx.data.repository

import com.devx.data.dataSource.local.dao.ProfileDao
import com.devx.data.dataSource.local.entity.toDomain
import com.devx.data.dataSource.remote.ProfileApi
import com.devx.data.model.ProfileMatchDto
import com.devx.data.model.toEntity
import com.devx.data.utils.network.NetworkConnectivityManager
import com.devx.domain.model.ProfileMatch
import com.devx.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val profileApi: ProfileApi,
        private val profileDao: ProfileDao,
        private val connectivityManager: NetworkConnectivityManager,
    ) : ProfileRepository {
        override suspend fun getMatches(count: Int): Flow<List<ProfileMatch>> {
            val profileCount = profileDao.getCount()
            val isConnected = connectivityManager.isConnected.first()

            if (profileCount < 1 && isConnected) {
                val response = profileApi.getProfileMatches(count = count)
                if (response.isSuccessful) {
                    saveProfileMatchesInDatabase(response = response.body())
                }
            }

            return profileDao
                .getAllMatches()
                .map {
                    it.map { profileMatchEntity ->
                        profileMatchEntity.toDomain()
                    }
                }
        }

        private fun saveProfileMatchesInDatabase(response: ProfileMatchDto?) {
            response?.let { profileMatchDto ->
                val profileMatches =
                    profileMatchDto.results.map { matchResult ->
                        matchResult.toEntity()
                    }
                profileDao.insertProfiles(profileMatches)
            }
        }

        override suspend fun updateProfileMatchStatus(
            userId: String,
            status: Int,
        ) {
            profileDao.updateStatus(userId = userId, status = status)
        }
    }
