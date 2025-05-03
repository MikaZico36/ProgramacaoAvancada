package tests

import classes.primitive.JsonNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonNullTest {

    @Test
    fun toJsonStringTest(){
        val n = null

        assertEquals(n.toString(), JsonNull(n).toJsonString())
    }
}