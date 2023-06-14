package com.kumaa.uni.di

import com.kumaa.uni.data.UniRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUniRepository(): UniRepository {
        return UniRepository() // Replace with your actual implementation
    }
}
