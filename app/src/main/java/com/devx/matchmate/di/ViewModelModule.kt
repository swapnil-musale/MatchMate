package com.devx.matchmate.di

import com.devx.data.repository.NetworkConnectivityManager
import com.devx.domain.useCase.GetProfileMatchesFromLocalUseCase
import com.devx.domain.useCase.GetProfileMatchesUseCase
import com.devx.domain.useCase.UpdateProfileMatchStatusUseCase
import com.devx.matchmate.ui.profileMatch.ProfileMatchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideProfileMatchViewModel(
        getProfileMatchesUseCase: GetProfileMatchesUseCase,
        getProfileMatchesFromLocalUseCase: GetProfileMatchesFromLocalUseCase,
        updateProfileMatchStatusUseCase: UpdateProfileMatchStatusUseCase,
        networkConnectivityManager: NetworkConnectivityManager,
    ): ProfileMatchViewModel =
        ProfileMatchViewModel(
            getProfileMatchesUseCase = getProfileMatchesUseCase,
            getProfileMatchesFromLocalUseCase = getProfileMatchesFromLocalUseCase,
            updateProfileMatchStatusUseCase = updateProfileMatchStatusUseCase,
            networkConnectivityManager = networkConnectivityManager,
        )
}
