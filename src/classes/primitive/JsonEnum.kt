package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonEnum(private val value: Enum<*>): JsonValue {
    override fun toJsonString(): String {
        TODO("Not yet implemented")
    }

    override fun accept(visitor: JsonVisitor) {
        return visitor.visitEnum(this)
    }
    fun getValue(): Enum<*> {return value}
}