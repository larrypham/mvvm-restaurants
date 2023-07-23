package com.position.apps.restaurant.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantsApiService {
    @GET("all")
    suspend fun getRestaurants(): List<RemoteRestaurant>

    @GET("{id}")
    suspend fun getRestaurant(@Path("id") id: String): RemoteRestaurant
}