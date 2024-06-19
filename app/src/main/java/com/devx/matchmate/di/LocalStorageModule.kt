package com.devx.matchmate.di

import android.content.Context
import androidx.room.Room
import com.devx.data.dataSource.local.dao.ProfileDao
import com.devx.data.dataSource.local.ProfileDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    @Provides
    @Singleton
    fun provideProfileDatabase(@ApplicationContext context: Context): ProfileDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProfileDatabase::class.java,
            "profile.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProfileDao(profileDatabase: ProfileDatabase): ProfileDao {
        return profileDatabase.profileDao
    }
}