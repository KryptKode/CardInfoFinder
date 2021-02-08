package com.kryptkode.cardinfofinder.data.service.mapper

interface Mapper<in R, out E> {
    fun mapToEntity(response: R): E
}
