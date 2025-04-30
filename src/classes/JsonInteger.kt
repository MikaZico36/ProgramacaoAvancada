package classes

class JsonInteger(val value: Int): JsonValue {

    override fun toJsonString(): String {
        return "$value"
    }
}