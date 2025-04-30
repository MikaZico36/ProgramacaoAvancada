package classes

class JsonNull<T>(val v: T): JsonValue {

    init {
        require(this.v == null)
    }

    override fun toJsonString(): String {
        return "null"
    }

}