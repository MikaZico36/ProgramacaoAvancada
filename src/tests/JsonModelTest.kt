package tests

import classes.JsonArray
import classes.JsonModel
import classes.JsonObject
import classes.primitive.JsonNumber
import classes.primitive.JsonString
import org.junit.jupiter.api.Test

class JsonModelTest{



    @Test
    fun toJsonModelTest() {

    val c = Course("Programação", 6)

    val convert = JsonModel().toJsonModel(c)
        println(convert.toJsonString())

    }




}

data class Course(
    val name: String,
    val credits: Int,
    //val evaluation: List<EvalItem>
)


data class EvalItem(
    val name: String,
    val percentage: Double,
    val mandatory: Boolean,
    //val type: EvalType?
)


enum class EvalType {
    TEST, PROJECT, EXAM
}
