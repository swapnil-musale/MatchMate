package com.devx.data.mapper

/*
This interface is used to create mapper between domain and data layer objects.
Wither we can use interface or extension functions to create mapping.
 */

interface Mapper<T> {
    fun mapToDomain(): T
}
