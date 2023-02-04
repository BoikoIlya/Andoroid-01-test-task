package com.example.android01.core


import javax.inject.Inject

interface HandleRequest<T> {

    suspend fun handle(block: suspend()-> List<T>): Result<T>

    class Base<T> @Inject constructor(
        private val handlerError: HandleError,
    ): HandleRequest<T>{
        override suspend fun handle(block: suspend () -> List<T>): Result<T> =
            try {
               val result = block.invoke()
                Result.Success(result)
            } catch (e: Exception) {
               Result.Error(handlerError.handle(e))
            }
        }
    }

