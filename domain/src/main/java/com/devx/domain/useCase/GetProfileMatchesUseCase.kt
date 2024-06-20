package com.devx.domain.useCase

import com.devx.domain.model.ProfileMatch
import com.devx.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

/*
Although I'm aware that here UseCase class is not necessary as we're not doing any kind of
sorting, filtering operation on received object or list and as it's delegating responsibility to the repository.
I've added it just for demonstration purposes.
 */

class GetProfileMatchesUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(count: Int): Flow<List<ProfileMatch>> {
        return profileRepository.getMatches(count = count)
    }
}
