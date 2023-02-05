package com.example.jokerapp.model

import com.google.gson.annotations.SerializedName

data class JokeDay(
    @SerializedName("value")val text: String,
    @SerializedName("icon_url")val iconUrl: String
)
