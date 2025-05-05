package tests
import classes.JsonArray
import classes.primitive.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonArrayTest {

    val array = JsonArray(arrayOf(JsonString("a"), JsonString("b"), JsonString("c")).toList())

    @Test
    fun toJsonStringTest() {

        assertEquals("\'[\"a\", \"b\", \"c\"]\'",array.toJsonString())
    }

    @Test
    fun filterJsonArrayTest() {

        val filteredArray = array.filter { it -> it.toJsonString() != "\"a\""}
        assertEquals("\'[\"b\", \"c\"]\'",filteredArray.toJsonString())
    }

    @Test
    fun mapJsonArrayTest() {
        val mappedI = array.mapping { it -> JsonString(it.toJsonString().replace("\"", "").uppercase()) }
        assertEquals("\'[\"A\", \"B\", \"C\"]\'",mappedI.toJsonString())
    }

    @Test
    fun getJsonArrayTest() {
        val jsonValue = array.get(1)
        assertEquals("\"b\"", jsonValue?.toJsonString())
    }

    @Test
    fun sizeJsonArrayTest() {
        val size = array.size()
        assertEquals(3, size)
    }

    @Test
    fun isEmptyJsonArrayTest() {
        val emptyArray = JsonArray(emptyList())
        assertTrue(emptyArray.isEmpty())
        assertFalse(array.isEmpty())
    }
}