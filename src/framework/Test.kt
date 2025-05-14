package framework

import classes.JsonBoolean

@Mapping("Json")
class Test {

    @Mapping("JBoolean")
    fun testBoolean(): JsonBoolean {
        val b = JsonBoolean(true)
        println(b.toJsonString())
        return b
    }
}