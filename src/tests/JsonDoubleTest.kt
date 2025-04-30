package tests
import classes.JsonDouble
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonDoubleTest {

    @Test
    fun toJsonStringTest() {
        val i = JsonDouble(1.0)
        assertEquals("1.0", i.toJsonString())
    }
}