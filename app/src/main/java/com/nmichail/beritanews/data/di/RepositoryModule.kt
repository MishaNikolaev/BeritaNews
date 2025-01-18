package com.nmichail.beritanews.data.di

import com.nmichail.beritanews.data.repository.LocalNewsRepositoryImpl
import com.nmichail.beritanews.domain.repository.LocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocalRepository(
        localNewsRepositoryImpl: LocalNewsRepositoryImpl
    ): LocalRepository
}