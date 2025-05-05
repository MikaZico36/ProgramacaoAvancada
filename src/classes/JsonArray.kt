package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonArray(private val values: List<JsonValue>): JsonValue {

    override fun toJsonString(): String {
        return "\'[${values.joinToString { it.toJsonString() }}]\'"
    }

    fun filter(predicate: (JsonValue) -> Boolean): JsonValue {
        val filteredValues = values.filter(predicate)
        return JsonArray(filteredValues)
    }

    fun mapping(transform: (JsonValue) -> JsonValue): JsonArray {
        val mappedValues = values.map(transform)
        return JsonArray(mappedValues)
    }

    override fun accept(visitor: JsonVisitor) {
        visitor.visitArray(this)
        values.forEach { it.accept(visitor) }
    }

    fun get(index: Int): JsonValue? {
        return if (index in values.indices) {
            values[index]
        } else {
            null
        }
    }

    fun size(): Int {
        return values.size
    }

    fun isEmpty(): Boolean {
        return values.isEmpty()
    }
}