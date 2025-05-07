package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

/**
 * Representa um valor boolean numa estrutura Json
 *
 * Esta classe implementa a interface [JsonValue], levando assim o value Boolean seja tratado
 * como um elemento Json
 *
 * @property value valor boolean desta classe
 */
data class JsonBoolean(private val value: Boolean): JsonValue {

    /**
     * Retorna a representação em string do value de acordo com o padrão Json
     *
     * @return true ou false, dependendo do value
     */
    override fun toJsonString(): String {
        return "$value"
    }

    /**
     * Aceita um visitor que implemente a interface [JsonVisitor]
     *
     *Este método permite que a lógica de processamento seja separada da estrutura de dados ao seguir o padrão de Visitor
     *
     * @param visitor O visitor que irá processar este objeto
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitBoolean(this)
    }

    /**
     * Retorna o valor boolean
     *
     * @return valor boolean
     */
    fun getValue(): Boolean = value

}