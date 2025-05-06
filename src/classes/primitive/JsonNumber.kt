package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonNumber(private val value: Number) : JsonValue {

    override fun toJsonString(): String {
        return "$value"
    }

    override fun accept(visitor: JsonVisitor) {
        visitor.visitNumber(this)
    }

    fun isInteger(): Boolean {
        return value is Int || value is Long
    }

    fun isDouble(): Boolean {
        return value is Double || value is Float
    }

    fun isExponential(): Boolean {
        return value is Double && value.toString().contains("E")
    }

    fun isNegative(): Boolean {
        return value is Int && value < 0 || value is Long && value < 0L || value is Double && value < 0.0 || value is Float && value < 0.0f
    }

    fun getValue(): Number = value

}