package com.position.apps.restaurant.presentation.list

import com.position.apps.restaurant.domain.model.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)