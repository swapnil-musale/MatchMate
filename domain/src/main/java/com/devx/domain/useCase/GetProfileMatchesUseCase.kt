package com.devx.domain.useCase

import com.devx.domain.model.ProfileMatch
import com.devx.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfileMatchesUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(count: Int): Flow<List<ProfileMatch>> {
        return profileRepository.getMatches(count = count)
    }
}
