package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonObject(private val values: List<Pair<String, JsonValue>>) : JsonValue {

    fun get(key: String): JsonValue? = values.find { it.first == key }?.second

    fun getEntries(): List<String> {
        return values.map { it.first }
    }


    override fun toJsonString() = "{${values.joinToString { "\"${it.first}\": ${it.second.toJsonString()}" }}}"

    override fun accept(visitor: JsonVisitor) {
        visitor.visitObject(this)
        values.forEach { (_, value) -> value.accept(visitor) }
    }

    fun filter(predicate: (JsonValue) -> Boolean): JsonObject {
        val filteredValues = values.filter { predicate(it.second) }
        return JsonObject(filteredValues)
    }



}