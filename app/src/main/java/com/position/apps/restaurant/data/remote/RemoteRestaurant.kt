package com.position.apps.restaurant.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteRestaurant(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String
)