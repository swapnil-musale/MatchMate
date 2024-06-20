package com.devx.matchmate.di

import android.content.Context
import com.devx.data.dataSource.remote.ProfileApi
import com.devx.data.utils.network.NetworkConnectivityManager
import com.devx.data.utils.network.Constants
import com.devx.matchmate.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/*
In this module @Singleton is used to ensure that only one instance of the object is created and other
will be re-created on demand. It's suggested to use @Singleton carefully - as if try to use for all the
other objects as well then it will increase the memory usage and user might will get OutOfMemoryException
if device is working on low memory.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .also { builder ->
                if (BuildConfig.DEBUG) {
                    builder.addInterceptor(interceptor = loggingInterceptor)
                }
            }
            .build()
    }

    /*
    Here we have Moshi converter to convert JSON data to Kotlin data class. Although we can use Gson
    as well but it's not efficient solution anymore after Moshi.
     */
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Provides
    fun provideNetworkConnectivityManager(
        @ApplicationContext context: Context,
    ): NetworkConnectivityManager = NetworkConnectivityManager(context = context)
}
