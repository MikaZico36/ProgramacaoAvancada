package tests

import classes.primitive.JsonNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Testa a classe [JsonNull] e o seu método [toJsonString], que converte o valor `null`
 * para a representação JSON correspondente.
 *
 * O teste garante que o valor `null` seja convertido corretamente para a string "null" no formato JSON.
 */
class JsonNullTest {

    /**
     * Testa a conversão do valor `null` para a representação JSON correspondente.
     *
     * O método `toJsonString` deve retornar a string `"null"` quando o valor passado para o
     * [JsonNull] for `null`. Este teste valida se a classe `JsonNull` está funcionando corretamente.
     */
    @Test
    fun toJsonStringTest(){
        val n = null

        assertEquals(n.toString(), JsonNull(n).toJsonString())
    }
}