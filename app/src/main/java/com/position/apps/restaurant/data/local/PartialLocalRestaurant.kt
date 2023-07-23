package com.position.apps.restaurant.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class PartialLocalRestaurant(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)