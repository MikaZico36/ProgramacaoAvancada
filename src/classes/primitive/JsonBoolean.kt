package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonBoolean(private val value: Boolean): JsonValue {
    override fun toJsonString(): String {
        return "$value"
    }

    override fun accept(visitor: JsonVisitor) {
        visitor.visitBoolean(this)
    }

}