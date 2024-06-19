package com.devx.domain.useCase

import com.devx.domain.model.ProfileMatch
import com.devx.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileMatchesFromLocalUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(count: Int): Flow<List<ProfileMatch>> {
        return profileRepository.getMatchesFromLocal(count = count)
    }
}
