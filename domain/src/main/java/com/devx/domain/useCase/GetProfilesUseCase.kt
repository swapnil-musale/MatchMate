package com.devx.domain.useCase

import com.devx.domain.model.MatchProfile
import com.devx.domain.repository.ProfileRepository
import com.devx.domain.util.NetworkResponse
import javax.inject.Inject

class GetProfilesUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(): NetworkResponse<MatchProfile> {
        return profileRepository.getProfiles()
    }
}
