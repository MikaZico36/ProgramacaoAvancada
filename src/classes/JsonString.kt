package classes

class JsonString(val value: String): JsonValue {
    override fun toJsonString(): String{
        return "\"$value\""
    }


}