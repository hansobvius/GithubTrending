package com.thiago.mobileui.mapper

interface ViewMapper<in P, out V> {

    fun mapFromView(presentation: P): V
}