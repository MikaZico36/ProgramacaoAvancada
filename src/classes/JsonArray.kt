package classes

class JsonArray(val values: List<JsonValue>): JsonValue {

    override fun toJsonString(): String {
        return "\'[${values.joinToString { it.toJsonString() }}]\'"
    }

    override fun filter(predicate: (JsonValue) -> Boolean): JsonValue {
        val filteredValues = values.filter(predicate)
        return JsonArray(filteredValues)
    }
}