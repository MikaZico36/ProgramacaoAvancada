package tests
import classes.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Testa a classe [JsonString], que representa um valor do tipo String em um formato JSON.
 *
 * O objetivo deste teste é garantir que a classe [JsonString] funcione corretamente em termos
 * de conversão para uma string no formato JSON, especialmente tratando-se de escapar corretamente
 * as strings dentro de aspas duplas.
 */
class JsonStringTest {

    /**
     * Testa a conversão de uma string para o formato JSON.
     *
     * O método `toJsonString` da classe [JsonString] deve retornar o valor da string envolto
     * em aspas duplas (como especificado no formato JSON), garantindo que a string seja
     * corretamente representada no formato esperado.
     */
    @Test
    fun toJsonString() {
        val s = "Olá Mundo!"
        assertEquals("\"Olá Mundo!\"", JsonString(s).toJsonString())
    }
}