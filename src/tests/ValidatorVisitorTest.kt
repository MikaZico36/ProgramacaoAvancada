package tests

import classes.JsonArray
import classes.JsonObject
import classes.ValidatorVisitor
import classes.primitive.JsonBoolean
import classes.primitive.JsonNumber
import classes.primitive.JsonString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Classe de testes para [ValidatorVisitor], que implementa a interface [JsonVisitor]
 * com o objetivo de validar certas propriedades de estruturas JSON.
 *
 * Os testes verificam:
 * - Se todos os elementos de um [JsonArray] possuem o mesmo tipo.
 * - Se um [JsonObject] contém apenas chaves únicas.
 */
class ValidatorVisitorTest {

    /**
     * Testa o método [ValidatorVisitor.visitArray], que valida se todos os elementos
     * de um [JsonArray] são do mesmo tipo.
     *
     * - O primeiro array (`jArray1`) contém apenas valores booleanos, então deve ser válido.
     * - O segundo array (`jArray2`) mistura um número e um booleano, então deve ser inválido.
     */
    @Test
    fun visitArrayTest(){

        val bool1 = JsonBoolean(false)
        val bool2 = JsonBoolean(true)
        val int1 = JsonNumber(1)
        val list1 = listOf(bool1, bool2)
        val list2 = listOf(int1, bool2)
        val jArray1 = JsonArray(list1)
        val jArray2 = JsonArray(list2)

        val visitor1 = ValidatorVisitor()
        val visitor2 = ValidatorVisitor()

        jArray1.accept(visitor1)
        jArray2.accept(visitor2)

        assertEquals(true, visitor1.getValidator())
        assertEquals(false, visitor2.getValidator())

    }

    /**
     * Testa o método [ValidatorVisitor.visitObject], que valida se todas as chaves de um
     * [JsonObject] são únicas.
     *
     * - `jObject1` contém chaves duplicadas ("one"), portanto é inválido.
     * - `jObject2` contém chaves únicas, então é válido.
     */
    @Test
    fun visitObjectTest(){
        val map1 = listOf("one" to JsonString("one"), "one" to JsonNumber(2), "three" to JsonString("three"))
        val map2 = listOf("one" to JsonNumber(1), "two" to JsonNumber(2), "three" to JsonNumber(3))

        val jObject1 = JsonObject(map1)
        val jObject2 = JsonObject(map2)

        val visitor1 = ValidatorVisitor()
        val visitor2 = ValidatorVisitor()

        jObject1.accept(visitor1)
        jObject2.accept(visitor2)

        assertEquals(false, visitor1.getValidator())
        assertEquals(true, visitor2.getValidator())

    }


}