package tests
import classes.primitive.JsonInteger
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonIntegerTest {

    @Test
    fun toJsonStringTest()
    {
        val i = JsonInteger(1)
        assertEquals("1", i.toJsonString())
    }

}