package otus.homework.flowcats

sealed class Result {
    data class Success<T>(val result: T) : Result()
    data class Error(val msg: String) : Result()
}