package com.vahabgh.core.data

class ResponseData<T> private constructor(
    val data: T? = null,
    val error: Throwable? = null,
    val state: State
) {

    fun fold(success: (T) -> Unit, failure: (Throwable) -> Unit) {
        when {
            data != null -> success(data)
            error != null -> failure(error)
            else -> return
        }
    }

    companion object {
        fun <T> success(data: T): ResponseData<T> {
            return ResponseData(
                data,
                state = State.SUCCESS
            )
        }

        fun <T> error(error: Throwable): ResponseData<T> {
            return ResponseData(
                null,
                error,
                State.ERROR
            )
        }
    }

    enum class State {
        SUCCESS,
        ERROR
    }
}