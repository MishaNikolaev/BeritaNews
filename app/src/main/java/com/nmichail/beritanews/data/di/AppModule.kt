package com.nmichail.beritanews.data.di

import com.nmichail.beritanews.data.remote.NewsApi
import com.nmichail.beritanews.data.repository.NewsRepositoryImpl
import com.nmichail.beritanews.domain.repository.NewsRepository
import com.nmichail.beritanews.domain.usecases.GetTopHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetTopHeadlinesUseCase(repository: NewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(repository)
    }
}