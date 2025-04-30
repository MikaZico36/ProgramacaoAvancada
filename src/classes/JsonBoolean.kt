package classes

class JsonBoolean(private val value: Boolean):JsonValue {
    override fun toJsonString(): String {
        return "$value"
    }
}