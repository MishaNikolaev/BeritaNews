package com.nmichail.beritanews.data.di

import android.content.Context
import androidx.room.Room
import com.nmichail.beritanews.data.local.AppDatabase
import com.nmichail.beritanews.data.local.ArticleDao
import com.nmichail.beritanews.data.repository.LocalNewsRepositoryImpl
import com.nmichail.beritanews.domain.repository.LocalRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "berita_news_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articleDao()
    }
}
