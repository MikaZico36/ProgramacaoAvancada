package tests

import classes.JsonArray
import classes.primitive.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Testa a classe [JsonArray] e os seus métodos principais, garantindo assim que a implementação
 * esteja correta para as operações realizadas em arrays JSON.
 *
 * Os testes incluem:
 * - Serialização correta para JSON string
 * - Filtragem de elementos
 * - Mapeamento de elementos para novos valores
 * - Acesso aos elementos através do índice
 * - Verificação de tamanho e se o array está vazio
 */
class JsonArrayTest {

    // Criando um array JSON de três strings
    val array = JsonArray(arrayOf(JsonString("a"), JsonString("b"), JsonString("c")).toList())

    /**
     * Testa a conversão do [JsonArray] para uma string JSON válida.
     *
     * O teste verifica se o método `toJsonString` retorna a string no formato correto
     * de um array JSON, com os valores entre aspas e separados por vírgulas.
     */
    @Test
    fun toJsonStringTest() {
        assertEquals("[\"a\", \"b\", \"c\"]", array.toJsonString())
    }

    /**
     * Testa a função de filtragem no [JsonArray].
     *
     * O método `filter` deve remover os elementos do array que não atendem à condição especificada.
     * O teste verifica se a filtragem remove corretamente o valor `"a"`.
     */
    @Test
    fun filterJsonArrayTest() {
        val filteredArray = array.filter { it.toJsonString() != "\"a\"" }
        assertEquals("[\"b\", \"c\"]", filteredArray.toJsonString())
    }

    /**
     * Testa a função de mapeamento no [JsonArray].
     *
     * O método `mapping` deve aplicar uma transformação a cada elemento do array.
     * Neste teste, a transformação converte o valor de cada string para maiúsculas.
     */
    @Test
    fun mapJsonArrayTest() {
        val mappedI = array.mapping { it -> JsonString(it.toJsonString().replace("\"", "").uppercase()) }
        assertEquals("[\"A\", \"B\", \"C\"]", mappedI.toJsonString())
    }

    /**
     * Testa o método de acesso por índice no [JsonArray].
     *
     * O método `get` deve retornar o valor correto dado um índice válido.
     * Este teste verifica se o valor na posição 1 é `"b"`.
     */
    @Test
    fun getJsonArrayTest() {
        val jsonValue = array.get(1)
        assertEquals("\"b\"", jsonValue?.toJsonString())
    }

    /**
     * Testa o método `size` do [JsonArray], que retorna o número de elementos.
     *
     * O teste verifica se o método retorna corretamente o tamanho do array, que é 3 neste caso.
     */
    @Test
    fun sizeJsonArrayTest() {
        val size = array.size()
        assertEquals(3, size)
    }

    /**
     * Testa o método `isEmpty` do [JsonArray], que verifica se o array está vazio.
     *
     * O teste cria um array vazio e verifica se o método `isEmpty` retorna `true`,
     * enquanto um array não vazio retorna `false`.
     */
    @Test
    fun isEmptyJsonArrayTest() {
        val emptyArray = JsonArray(emptyList())
        assertTrue(emptyArray.isEmpty())
        assertFalse(array.isEmpty())
    }
}
