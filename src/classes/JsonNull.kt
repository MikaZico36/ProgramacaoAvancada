package classes

class JsonNull<T>(val value: T): JsonValue {

    init {
        require(this.value == null)
    }

    override fun toJsonString(): String {
        return "null"
    }

}