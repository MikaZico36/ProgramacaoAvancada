package tests

import classes.JsonModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Testa a classe [JsonModel] e o seu método [toJsonModel], que converte um objeto
 * complexo em uma estrutura JSON.
 *
 * O teste verifica se um objeto da classe [Course], contendo objetos de [EvalItem]
 * e um enum [EvalType], é convertido corretamente para a representação JSON esperada.
 */
class JsonModelTest{


    /**
     * Testa a conversão de um objeto [Course] para JSON utilizando o método [toJsonModel].
     *
     * O teste cria um objeto de [Course] com uma lista de [EvalItem] e valida se a
     * conversão para JSON resulta em uma string compatível com o formato esperado.
     */
    @Test
    fun toJsonModelTest() {

        val evalItem1 = EvalItem("quizzes",0.2, false, null )
        val evalItem2 = EvalItem("project",0.8, true, EvalType.PROJECT )
        val c = Course("Programação", 6, listOf(evalItem1, evalItem2))

        val convert = JsonModel().toJsonModel(c)

        val expected = """'{"name": "Programação", "credits": 6, "evaluation": ['{"name": "quizzes", "percentage": 0.2, "mandatory": false, "type": null}', '{"name": "project", "percentage": 0.8, "mandatory": true, "type": "PROJECT"}']}'"""
        assertEquals(expected, convert.toJsonString())


    }




}

/**
 * Representa um curso, contendo o nome, créditos e avaliações.
 */
data class Course(
    val name: String,
    val credits: Int,
    val evaluation: List<EvalItem>
)

/**
 * Representa um item de avaliação, contendo o nome, a porcentagem, se é obrigatório,
 * e o tipo de avaliação (se houver).
 */
data class EvalItem(
    val name: String,
    val percentage: Double,
    val mandatory: Boolean,
    val type: EvalType?
)

/**
 * Enum que define os tipos de avaliação possíveis.
 */
enum class EvalType {
    TEST, PROJECT, EXAM
}