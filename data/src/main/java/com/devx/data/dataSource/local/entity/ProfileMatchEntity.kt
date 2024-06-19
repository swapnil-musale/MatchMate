package com.devx.data.dataSource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devx.domain.model.ProfileMatch

@Entity(tableName = "profile_match")
data class ProfileMatchEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val profilePicUrl: String,
    val address: String,
    val status: Int = -1,
)

fun ProfileMatch.toEntity() =
    ProfileMatchEntity(
        userId = userId,
        name = name,
        profilePicUrl = profilePicUrl,
        address = address,
        status = status,
    )

fun ProfileMatchEntity.toDomain() =
    ProfileMatch(
        userId = userId,
        name = name,
        profilePicUrl = profilePicUrl,
        address = address,
        status = status,
    )
