package com.liordahan.justeattakeaway.di

import android.content.Context
import androidx.room.Room
import com.liordahan.justeattakeaway.data.local.database.JustEatTakeawayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideJustEatTakeawayDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            JustEatTakeawayDatabase::class.java,
            "just_eat_database"
        ).build()

    @Singleton
    @Provides
    fun provideFavoriteRestaurantDao(database: JustEatTakeawayDatabase) = database.favoriteRestaurantsDao()
}