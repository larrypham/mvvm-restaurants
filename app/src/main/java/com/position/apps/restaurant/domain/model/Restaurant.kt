package com.position.apps.restaurant.domain.model

data class Restaurant(
    val id: String,
    val title: String,
    val description: String,
    val isFavorite: Boolean = false
)