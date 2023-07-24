package com.position.apps.restaurant.di

import android.content.Context
import androidx.room.Room
import com.position.apps.restaurant.data.local.RestaurantsDao
import com.position.apps.restaurant.data.local.RestaurantsDb
import com.position.apps.restaurant.data.remote.RestaurantsApiService
import com.position.apps.restaurant.domain.repository.RestaurantRepository
import com.position.apps.restaurant.domain.usecase.GetInitialRestaurantsUseCase
import com.position.apps.restaurant.domain.usecase.GetSortedRestaurantsUseCase
import com.position.apps.restaurant.domain.usecase.ToggleRestaurantsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantsModule {

    @Provides
    fun provideRoomDao(database: RestaurantsDb): RestaurantsDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): RestaurantsDb {
        return Room.databaseBuilder(
            appContext,
            RestaurantsDb::class.java,
            "restaurants"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://us-central1-restaurants-1103.cloudfunctions.net/restaurants/")
            .build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): RestaurantsApiService {
        return retrofit.create(RestaurantsApiService::class.java)
    }

    @Provides
    fun provideRestaurantsRepository(
        restaurantsApiService: RestaurantsApiService,
        restaurantsDao: RestaurantsDao
    ) = RestaurantRepository(restaurantsApiService, restaurantsDao)

    @Provides
    @Singleton
    fun provideToggleRestaurantsUseCase(restaurantRepository: RestaurantRepository): ToggleRestaurantsUseCase {
        return ToggleRestaurantsUseCase { id, oldValue ->
            val newValue = oldValue.not()
            restaurantRepository.toggleFavoriteRestaurant(id, newValue)
            restaurantRepository.getRestaurants()
        }
    }

    @Provides
    @Singleton
    fun provideGetSortedRestaurantsUseCase(restaurantRepository: RestaurantRepository): GetSortedRestaurantsUseCase {
        return GetSortedRestaurantsUseCase {
            restaurantRepository.getRestaurants().sortedBy { it.title }
        }
    }

    @Provides
    @Singleton
    fun provideGetInitialRestaurantsUseCase(restaurantRepository: RestaurantRepository): GetInitialRestaurantsUseCase {
        return GetInitialRestaurantsUseCase {
            restaurantRepository.loadRestaurants()
            provideGetSortedRestaurantsUseCase(restaurantRepository).invoke()
        }
    }
}