package com.devx.data.model

import androidx.annotation.Keep
import com.devx.data.dataSource.local.entity.ProfileMatchEntity
import com.devx.data.mapper.Mapper
import com.devx.domain.model.ProfileMatch
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ProfileMatchDto(
    val results: List<MatchResult>,
) : Mapper<List<ProfileMatch>> {
    override fun mapToDomain(): List<ProfileMatch> =
        results
            .map { matchResult ->
                ProfileMatch(
                    name = "${matchResult.name.first} ${matchResult.name.last}",
                    userId = matchResult.login.uuid,
                    profilePicUrl = matchResult.picture.large,
                    address = "${matchResult.location.street.number}, ${matchResult.location.street.name}",
                )
            }.toList()
}

@Keep
@JsonClass(generateAdapter = true)
data class MatchResult(
    val name: Name,
    val login: Login,
    val location: Location,
    val picture: Picture,
)

@Keep
@JsonClass(generateAdapter = true)
data class Name(
    val title: String,
    val first: String,
    val last: String,
)

@Keep
@JsonClass(generateAdapter = true)
data class Login(val uuid: String)

@Keep
@JsonClass(generateAdapter = true)
data class Location(val street: Street)

@Keep
@JsonClass(generateAdapter = true)
data class Street(
    val name: String,
    val number: Int,
)

@Keep
@JsonClass(generateAdapter = true)
data class Picture(val large: String)

fun MatchResult.toEntity() =
    ProfileMatchEntity(
        userId = login.uuid,
        name = "${name.first} ${name.last}",
        profilePicUrl = picture.large,
        address = "${location.street.number}, ${location.street.name}",
    )
