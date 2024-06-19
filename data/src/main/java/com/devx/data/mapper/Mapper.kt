package com.devx.data.mapper

interface Mapper<T> {
    fun mapToDomain(): T
}
