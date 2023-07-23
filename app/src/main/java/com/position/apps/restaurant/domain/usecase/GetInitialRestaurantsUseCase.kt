package com.position.apps.restaurant.domain.usecase

import com.position.apps.restaurant.domain.model.Restaurant
import com.position.apps.restaurant.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetInitialRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
){
    suspend operator fun invoke(): List<Restaurant> {
        repository.loadRestaurants()
        return getSortedRestaurantsUseCase()
    }
}