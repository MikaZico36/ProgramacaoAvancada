package tests
import classes.JsonArray
import classes.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonArrayTest {

    @Test
    fun toJsonStringTest() {
        val i = JsonArray(arrayOf(JsonString("a"), JsonString("b"), JsonString("c")).toList())
        assertEquals("\'[\"a\", \"b\", \"c\"]\'",i.toJsonString())
    }

    @Test
    fun filterJsonArrayTest() {
        val i = JsonArray(arrayOf(JsonString("a"), JsonString("b"), JsonString("c")).toList())
        val filteredI = i.filter { it -> it.toJsonString() != "\"a\""}
        assertEquals("\'[\"b\", \"c\"]\'",filteredI.toJsonString())
    }
}