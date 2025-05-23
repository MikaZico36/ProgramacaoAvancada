package tests
import classes.JsonObject
import classes.JsonNumber
import classes.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Testa a classe [JsonObject], que representa um objeto JSON composto por pares de chave-valor.
 *
 * O objetivo é garantir que a classe [JsonObject] forneça as funcionalidades corretas, como
 * conversão para a string no formato JSON, acesso aos valores através de chaves, e a filtragem
 * de pares chave-valor com base em condições específicas.
 */
class JsonObjectTest {

    val obj = JsonObject(
        listOf(
            "name" to JsonString("Cristiano"),
            "age" to JsonNumber(40),
            "teams" to JsonObject(
                listOf(
                    "Real Madrid" to JsonString("principal"),
                    "Man United" to JsonString("former"),
                    "Juventus" to JsonString("retired")
                ).toMap()
            ),
        ).toMap()
    )

    /**
     * Testa a conversão de um objeto [JsonObject] para o formato de string JSON.
     *
     * O método `toJsonString` deve retornar a string JSON corretamente formatada,
     * incluindo objetos aninhados, com as chaves e valores convertidos de forma adequada.
     */
    @Test
    fun testToJsonString() {
        assertEquals("{\"name\": \"Cristiano\",\"age\": 40,\"teams\": {\"Real Madrid\": \"principal\",\"Man United\": \"former\",\"Juventus\": \"retired\"}}", obj.toJsonString())
    }

    /**
     * Testa o acesso aos valores de um objeto JSON usando chaves.
     *
     * O método `get` deve retornar o valor correto associado a uma chave, caso ela exista.
     * Este teste verifica se o valor retornado é o esperado para as chaves "name" e "age".
     */
    @Test
    fun testGet() {
        val expectedName = JsonString("Cristiano")
        val expectedAge = JsonNumber(40)
        assertEquals(expectedName, obj.get("name"))
        assertEquals(expectedAge, obj.get("age"))
    }
    /**
     * Testa a filtragem de pares chave-valor dentro de um objeto JSON.
     *
     * O método `filter` deve ser capaz de filtrar os valores de um objeto JSON com base
     * em uma condição fornecida. Este teste verifica se a filtragem funciona corretamente
     * ao procurar por valores específicos (no caso, "fluent" para a chave "english").
     */
    @Test
    fun testFilter() {
        val teams = obj.get("teams") as? JsonObject
        val filteredObj = teams?.filter { value ->
            value is JsonString && value.toJsonString() == "\"principal\""
        }
        val expectedTeams = JsonObject(
            listOf(
                "Real Madrid" to JsonString("principal")
            ).toMap()
        )
        assertEquals(expectedTeams, filteredObj)
    }



}