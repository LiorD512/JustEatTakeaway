package com.liordahan.justeattakeaway.di

import com.liordahan.justeattakeaway.data.local.database.dao.FavoritesRestaurantsDao
import com.liordahan.justeattakeaway.data.remote.RestaurantApiService
import com.liordahan.justeattakeaway.data.repositories.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRestaurantRepository(
        restaurantApiService: RestaurantApiService,
        favoritesRestaurantsDao: FavoritesRestaurantsDao
    ): RestaurantRepository {
        return RestaurantRepository(restaurantApiService, favoritesRestaurantsDao)
    }
}