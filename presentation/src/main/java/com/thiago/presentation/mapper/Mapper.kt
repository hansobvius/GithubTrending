package com.thiago.presentation.mapper

interface Mapper<out V, in D> {

    fun mapToview(type: D): V
}