package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonObject(private val values: Map<String, JsonValue>) : JsonValue {

    fun get(key: String): JsonValue? = values[key]

    override fun toJsonString() = "\'{${values.entries.joinToString { "\"${it.key}\": ${it.value.toJsonString()}" }}}\'"

    override fun accept(visitor: JsonVisitor) {
        visitor.visitObject(this)
        values.forEach { (_, value) -> value.accept(visitor) }
    }

    fun filter(predicate: (JsonValue) -> Boolean): JsonObject {
        val filteredValues = values.filter { predicate(it.value) }
        return JsonObject(filteredValues)
    }



}