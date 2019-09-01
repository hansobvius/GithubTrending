package com.thiago.presentation.state

class Resource<out T> constructor(val status: ResourceState,
                                  val data: T?,
                                  val message: String?)