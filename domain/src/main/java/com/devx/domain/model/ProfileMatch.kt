package com.devx.domain.model

/*
Domain module data class which will be consumed by UI or presentation layer
 */

data class ProfileMatch(
    val userId: String,
    val name: String,
    val profilePicUrl: String,
    val address: String,
    val status: Int = -1,
)
