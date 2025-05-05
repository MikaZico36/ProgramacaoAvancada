package tests
import classes.primitive.JsonNumber
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonNumberTest {

    val jsonNumberInt = JsonNumber(123)
    val jsonNumberLong = JsonNumber(123L)
    val jsonNumberDouble = JsonNumber(123.0)
    val jsonNumberFloat = JsonNumber(123.0f)
    val jsonNumberExponential = JsonNumber(123.0E10)
    val jsonNumberNegative = JsonNumber(-123.0)

    @Test
    fun testToJsonString() {
        assertEquals("123", jsonNumberInt.toJsonString())
    }

    @Test
    fun testIsInteger() {
        assertTrue(jsonNumberInt.isInteger())
        assertTrue(jsonNumberLong.isInteger())
        assertFalse(jsonNumberDouble.isInteger())
    }

    @Test
    fun testIsDouble() {
        assertTrue(jsonNumberDouble.isDouble())
        assertTrue(jsonNumberFloat.isDouble())
        assertFalse(jsonNumberInt.isDouble())
    }

    @Test
    fun testIsExponential() {
        assertTrue(jsonNumberExponential.isExponential())
        assertFalse(jsonNumberInt.isExponential())

    }

    @Test
    fun testIsNegative() {
        assertTrue(jsonNumberNegative.isNegative())
        assertFalse(jsonNumberInt.isNegative())
        assertFalse(jsonNumberLong.isNegative())
        assertFalse(jsonNumberDouble.isNegative())
        assertFalse(jsonNumberFloat.isNegative())
    }
}