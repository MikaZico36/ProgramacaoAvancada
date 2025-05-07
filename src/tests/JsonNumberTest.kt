package tests
import classes.JsonNumber
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Testa a classe [JsonNumber] e os seus métodos de conversão e verificação, que lidam com
 * diferentes tipos numéricos (inteiro, flutuante, exponencial, negativo).
 *
 * O objetivo é garantir que a classe [JsonNumber] forneça as representações corretas para
 * diferentes tipos de número, além de verificar se as funções auxiliares como `isInteger`,
 * `isDouble`, `isExponential` e `isNegative` funcionam corretamente para diferentes valores.
 */
class JsonNumberTest {

    val jsonNumberInt = JsonNumber(123)
    val jsonNumberLong = JsonNumber(123L)
    val jsonNumberDouble = JsonNumber(123.0)
    val jsonNumberFloat = JsonNumber(123.0f)
    val jsonNumberExponential = JsonNumber(123.0E10)
    val jsonNumberNegative = JsonNumber(-123.0)

    /**
     * Testa a conversão de um número para uma string no formato JSON.
     *
     * O método `toJsonString` deve retornar a representação do número em formato de string
     * de acordo com o tipo numérico. Este teste verifica o comportamento para um número inteiro.
     */
    @Test
    fun testToJsonString() {
        assertEquals("123", jsonNumberInt.toJsonString())
    }

    /**
     * Testa a verificação se um número é do tipo inteiro.
     *
     * O método `isInteger` deve retornar `true` para números inteiros (como [Int] ou [Long])
     * e `false` para outros tipos numéricos. Este teste valida o comportamento para números
     * inteiros e não inteiros.
     */
    @Test
    fun testIsInteger() {
        assertTrue(jsonNumberInt.isInteger())
        assertTrue(jsonNumberLong.isInteger())
        assertFalse(jsonNumberDouble.isInteger())
    }

    /**
     * Testa a verificação se um número é do tipo flutuante (Double ou Float).
     *
     * O método `isDouble` deve retornar `true` para números do tipo `Double` ou `Float`
     * e `false` para outros tipos. Este teste valida o comportamento para números do tipo
     * flutuante e não flutuante.
     */
    @Test
    fun testIsDouble() {
        assertTrue(jsonNumberDouble.isDouble())
        assertTrue(jsonNumberFloat.isDouble())
        assertFalse(jsonNumberInt.isDouble())
    }

    /**
     * Testa a verificação se um número é representado em notação exponencial.
     *
     * O método `isExponential` deve retornar `true` se o número for representado em
     * notação exponencial e `false` caso contrário. Este teste verifica se a notação
     * exponencial é corretamente identificada.
     */
    @Test
    fun testIsExponential() {
        assertTrue(jsonNumberExponential.isExponential())
        assertFalse(jsonNumberInt.isExponential())
    }

    /**
     * Testa a verificação se um número é negativo.
     *
     * O método `isNegative` deve retornar `true` para números negativos e `false`
     * para números positivos ou zero. Este teste valida o comportamento para números
     * negativos, positivos e zero.
     */
    @Test
    fun testIsNegative() {
        assertTrue(jsonNumberNegative.isNegative())
        assertFalse(jsonNumberInt.isNegative())
        assertFalse(jsonNumberLong.isNegative())
        assertFalse(jsonNumberDouble.isNegative())
        assertFalse(jsonNumberFloat.isNegative())
    }
}