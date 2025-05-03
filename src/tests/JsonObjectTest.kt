package tests
import classes.JsonObject
import classes.primitive.JsonInteger
import classes.primitive.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonObjectTest {

    val obj = JsonObject(mapOf(
        "name" to JsonString("John"),
        "age" to JsonInteger(30),
        "languages" to JsonObject(mapOf(
            "english" to JsonString("fluent"),
            "french" to JsonString("basic"),
            "german" to JsonString("basic")
        )),
    ))

    @Test
    fun testToJsonString() {
        assertEquals("\'{\"name\": \"John\", \"age\": 30, \"languages\": \'{\"english\": \"fluent\", \"french\": \"basic\", \"german\": \"basic\"}\'}\'", obj.toJsonString())
    }

    @Test
    fun testGet() {
        assertEquals(obj.get("name")?.toJsonString(), "\"John\"")
        assertEquals(obj.get("age")?.toJsonString(), "30")
    }

    @Test
    fun testFilter() {
        val languages = obj.get("languages") as? JsonObject
        val filteredObj = languages?.filter {value -> value is JsonString && value.toJsonString() == "\"fluent\"" }
        assertEquals("\'{\"english\": \"fluent\"}\'", filteredObj?.toJsonString())
    }

    @Test
    fun testMapping() {
        val languages = obj.get("languages") as? JsonObject
        val mappedObj = languages?.mapping { value -> if (value is JsonString) JsonString(value.toJsonString().replace("\"", "").uppercase()) else value }
        assertEquals("\'{\"english\": \"FLUENT\", \"french\": \"BASIC\", \"german\": \"BASIC\"}\'", mappedObj?.toJsonString())
    }
}