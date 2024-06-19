package com.devx.matchmate.di

import com.devx.domain.repository.ProfileRepository
import com.devx.domain.useCase.GetProfileMatchesUseCase
import com.devx.domain.useCase.UpdateProfileMatchStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetProfileMatchesUseCase(profileRepository: ProfileRepository): GetProfileMatchesUseCase {
        return GetProfileMatchesUseCase(profileRepository = profileRepository)
    }

    @Provides
    fun provideUpdateProfileMatchStatusUseCase(profileRepository: ProfileRepository): UpdateProfileMatchStatusUseCase {
        return UpdateProfileMatchStatusUseCase(profileRepository = profileRepository)
    }
}
