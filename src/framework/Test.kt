package framework

import classes.JsonBoolean

@Mapping("Json")
class Test {

    @Mapping("JBoolean")
    fun testBoolean(): JsonBoolean {
        return JsonBoolean(true)
    }
}