package com.position.apps.restaurant.di

import android.content.Context
import androidx.room.Room
import com.position.apps.restaurant.data.local.RestaurantsDao
import com.position.apps.restaurant.data.local.RestaurantsDb
import com.position.apps.restaurant.data.remote.RestaurantsApiService
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
            .baseUrl("https://us-central1-restaurants-1103.cloudfunctions.net/restaurants")
            .build()
    }

    @Provides
    fun provideRetrofiApi(retrofit: Retrofit): RestaurantsApiService {
        return retrofit.create(RestaurantsApiService::class.java)
    }
}