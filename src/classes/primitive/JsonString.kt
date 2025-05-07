package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

/**
 * Representa um valor string em estruturas Json
 * Esta classe formata uma string com aspas, conforme o padrão Json
 *
 * Implementa a interface [JsonValue],permitindo assim ser usada em estruturas Json
 *
 * @property value o valor a ser representado
 *
 */
class JsonString(private val value: String): JsonValue {


    /**
     * Retorna a representação em string do value de acordo com o padrão Json
     *
     * @return o valor de value em formato padrão Json
     */
    override fun toJsonString(): String{
        return "\"$value\""
    }


    /**
     * Aceita um visitor que implemente a interface [JsonVisitor]
     *
     *Este método permite que a lógica de processamento seja separada da estrutura de dados ao seguir o padrão de Visitor
     *
     * @param visitor O visitor que irá processar este objeto
     */
    override fun accept(visitor: JsonVisitor) {
        return visitor.visitString(this)
    }

    /**
     * Retorna o value String
     *
     * @return valor string
     */
    fun getValue(): String = value


}