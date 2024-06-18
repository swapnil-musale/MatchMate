package com.devx.domain.useCase

import com.devx.domain.model.ProfileMatch
import com.devx.domain.repository.ProfileRepository
import com.devx.domain.util.NetworkResponse
import javax.inject.Inject

class GetProfileMatchesUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(count: Int): NetworkResponse<List<ProfileMatch>> {
        return profileRepository.getMatches(count = count)
    }
}
