package tests

import classes.JsonBoolean
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Testa a classe [JsonBoolean] e o seu método [toJsonString], que converte o valor boolean
 * para a representação JSON adequada.
 *
 * O teste garante que os valores booleans sejam convertidos corretamente para suas
 * representações em JSON: `"true"` e `"false"`.
 */
class JsonBooleanTest {

    /**
     * Testa a conversão do valor booleano para a representação JSON correspondente.
     *
     * O método `toJsonString` deve retornar a string `"true"` quando o valor booleano for `true`
     * e `"false"` quando o valor booleano for `false`. Este teste verifica o comportamento para o valor `true`.
     */
    @Test
    fun toJsonStringTest() {
        val bool = true
        assertEquals("true", JsonBoolean(bool).toJsonString())
    }
}
