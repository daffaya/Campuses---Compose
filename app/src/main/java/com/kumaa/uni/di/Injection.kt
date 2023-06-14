package com.kumaa.uni.di

import com.kumaa.uni.data.UniRepository

object Injection {
    fun provideRepository(): UniRepository {
        return UniRepository.getInstance()
    }
}