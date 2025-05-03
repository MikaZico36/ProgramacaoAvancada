package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonInteger(private val value: Int): JsonValue {

    override fun toJsonString(): String {
        return "$value"
    }

    override fun accept(visitor: JsonVisitor) {
        return visitor.visitInteger(this)
    }
}