package com.position.apps.restaurant.domain.usecase

import com.position.apps.restaurant.domain.model.Restaurant
import com.position.apps.restaurant.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantRepository
)  {

    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants().sortedBy { it.title }
    }
}