package classes

interface JsonValue {
    fun toJsonString(): String
    fun filter(predicate: (JsonValue) -> Boolean): JsonValue{
        return this
    }
}