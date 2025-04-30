package tests
import classes.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonStringTest {

    @Test
    fun toJsonString() {
        val s = "Olá Mundo!"
        assertEquals("\"Olá Mundo!\"", JsonString(s).toJsonString())
    }


}