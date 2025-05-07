package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor
import classes.primitive.*

/**
 * Implementação do padrão Visitor para a validação de estruturas Json
 *
 * Esta classe percorre estruturas Json e aplica regras de validação simples:
 *
 * **JsonObject**: não deve conter chaves duplicadas
 * **JsonArray**: todos os elementos devem ser do mesmo tipo
 *
 * A validação é feita de forma recursiva, e o resultado por ser acedido através de [getValidator]
 *
 * Implementa a interface [JsonVisitor], permitindo assim aplicar operações personalizadas sobre elementos
 * de uma estrutura Json sem modificar as suas classes
 *
 *
 */
class ValidatorVisitor: JsonVisitor {

    /**
     * Indica se a estrutura validade é valida ("true") ou não ("false")
     */
    private var validator: Boolean = true


    /**
     * Valida as chaves de um [JsonObject]
     * @param jsonObject O objeto a ser validado
     */
    override fun visitObject(jsonObject: JsonObject) {
        validator = checkJsonObject(jsonObject)
    }

    /**
     * Aplica a validação de tipos homogéneos num [JsonArray]
     *
     * @param jsonArray O array a ser validado
     */
    override fun visitArray(jsonArray: JsonArray) {
        validator = checkJsonArray(jsonArray)
    }

    /**
     * Retorna o resultado da validação
     *
     * @return 'true' se a estrutura for válida ou 'false' caso não seja
     */
    fun getValidator(): Boolean {
        return validator
    }

    /**
     * Valida um [JsonArray] ao verificar se todos os seus elementos são do mesmo tipo.
     * Faz esta ação de forma recursiva.
     *
     * @param jsonArray O array a ser validado
     * @param index Índice atual usado na recursão
     * @return 'true' se todos os elementos forem do mesmo tipo ou array vazio, 'false' caso sejam de tipos diferentes
     */
    private fun checkJsonArray(jsonArray: JsonArray, index: Int = 0 ): Boolean {
        if(index == jsonArray.size()) return true
        val current = jsonArray.get(index)
        val next = jsonArray.get(index+1)
        if (current != null && next !=null) {
            val currentClass = current::class
            val nextClass = next::class
            if (currentClass != nextClass ) {
                return false
            }
        }
        return checkJsonArray(jsonArray, index + 1)

    }

    /**
     * Valida um [JsonObject] ao garantir que não existem chaves duplicadas.
     * Faz esta ação de forma recursiva.
     *
     * @param jsonObject O objeto a ser validado
     * @param index Índice atual usado na recursão
     * @return 'true' se todas as chaves forem únicas ou 'false' caso não sejam
     *
     */
    private fun checkJsonObject(jsonObject: JsonObject, index: Int=0): Boolean {
        val entries = jsonObject.getEntries()
        if(index == entries.size) return true
        if (entries.isEmpty()) return false
        val head = entries[index]
        for (i in (index + 1) ..< entries.size) {
            if ( entries[i] == head) {
                return false
            }
        }
        return checkJsonObject(jsonObject, index +1)
    }



    /**
     * Método não implementado
     *
     * @param jsonString O valor de [JsonString]
     */
    override fun visitString(jsonString: JsonString) {}
    /**
     * Método não implementado
     *
     * @param jsonBoolean O valor de [JsonBoolean]
     */
    override fun visitBoolean(jsonBoolean: JsonBoolean) {}
    /**
     * Método não implementado
     *
     * @param jsonNull O valor de [JsonNull]
     */
    override fun visitNull(jsonNull: JsonNull<*>) {}
    /**
     * Método não implementado
     *
     * @param jsonNumber O valor de [JsonNumber]
     */
    override fun visitNumber(jsonNumber: JsonNumber) {}

}