package com.devx.matchmate.di

import com.devx.data.dataSource.local.dao.ProfileDao
import com.devx.data.dataSource.remote.ProfileApi
import com.devx.data.repository.ProfileRepositoryImpl
import com.devx.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideProfileRepository(profileApi: ProfileApi, profileDao: ProfileDao): ProfileRepository {
        return ProfileRepositoryImpl(profileApi = profileApi, profileDao = profileDao)
    }
}
