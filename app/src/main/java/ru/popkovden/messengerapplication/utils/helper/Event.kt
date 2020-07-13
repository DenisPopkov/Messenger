package ru.popkovden.messengerapplication.utils.helper

data class Event<out T>(val data: T?, val status: Status, val message: String?) {

    companion object {
        fun <T> success(data: T?): Event<T> =
            Event(data = data, status = Status.SUCCESS, message = null)

        fun <T> loading(data: T?): Event<T> =
            Event(data = data, status = Status.LOADING, message = null)

        fun <T> error(data: T?, message: String?) =
            Event(data = data, status = Status.ERROR, message = message)
    }
}