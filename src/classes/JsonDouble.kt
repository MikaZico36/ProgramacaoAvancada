package classes

class JsonDouble(val value: Double): JsonValue {

    override fun toJsonString(): String {
        return "$value"
    }
}