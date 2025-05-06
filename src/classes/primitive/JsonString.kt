package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonString(private val value: String): JsonValue {
    override fun toJsonString(): String{
        return "\"$value\""
    }

    override fun accept(visitor: JsonVisitor) {
        return visitor.visitString(this)
    }
    fun getValue(): String = value


}