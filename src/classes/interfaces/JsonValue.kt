package classes.interfaces

interface JsonValue {
    fun toJsonString(): String
    fun accept(visitor: JsonVisitor)
}