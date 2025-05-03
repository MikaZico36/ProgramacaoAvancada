package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonDouble(private val value: Double): JsonValue {

    override fun toJsonString(): String {
        return "$value"
    }

    override fun accept(visitor: JsonVisitor) {
        visitor.visitDouble(this)
    }
}