package com.devx.domain.useCase

import com.devx.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileMatchStatusUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(userId: String, status: Int) {
        return profileRepository.updateProfileMatchStatus(userId = userId, status = status)
    }
}
