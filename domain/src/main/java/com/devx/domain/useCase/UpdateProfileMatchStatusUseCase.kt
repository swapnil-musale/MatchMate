package com.devx.domain.useCase

import com.devx.domain.repository.ProfileRepository

class UpdateProfileMatchStatusUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(userId: String, status: Int) {
        return profileRepository.updateProfileMatchStatus(userId = userId, status = status)
    }
}
