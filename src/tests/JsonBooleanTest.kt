package tests
import classes.primitive.JsonBoolean
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonBooleanTest {

    @Test
    fun toJsonStringTest() {
        val bool = true
        assertEquals("true", JsonBoolean(bool).toJsonString())
    }
}
