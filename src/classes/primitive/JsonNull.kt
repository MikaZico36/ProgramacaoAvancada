package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

class JsonNull<T>(private val value: T): JsonValue {

    init {
        require(this.value == null)
    }

    override fun toJsonString(): String {
        return "null"
    }

    override fun accept(visitor: JsonVisitor) {
        return visitor.visitNull(this)
    }

}