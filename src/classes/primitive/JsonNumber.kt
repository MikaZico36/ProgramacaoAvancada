package classes.primitive

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

/**
 * Representa um valor numérico no formato padrão Json
 *
 * Esta classe representada qualquer tipo de número e fornece meios para identificar características do valor.
 *
 * Implementa a interface [JsonValue],permitindo assim ser usada em estruturas Json
 *
 * @property value o valor numérico representado
 */
class JsonNumber(private val value: Number) : JsonValue {

    /**
     * Retorna a representação em string do value de acordo com o padrão Json
     *
     * @return o valor numérico convertido para string
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
        visitor.visitNumber(this)
    }

    /**
     *Verifica se o número é um valor inteiro ou long
     *
     * @return 'true' se o value for inteiro ou long, caso contrário retorna 'false'
     */
    fun isInteger(): Boolean {
        return value is Int || value is Long
    }

    /**
     *Verifica se o número é um valor decimal (Double ou Float)
     *
     * @return 'true' se o value for double ou float, caso contrário retorna 'false'
     */
    fun isDouble(): Boolean {
        return value is Double || value is Float
    }

    /**
     *Verifica se o número está representado em notação exponecial
     *
     * @return 'true' se o value for double e o a sua conversão em string contenha "E", caso contrário retorna 'false'
     */
    fun isExponential(): Boolean {
        return value is Double && value.toString().contains("E")
    }

    /**
     * Verifica se o número é negativo
     *
     * @return 'true' se o valor for menor que zero, caso contrário retorna 'false'
     */
    fun isNegative(): Boolean {
        return value is Int && value < 0 || value is Long && value < 0L || value is Double && value < 0.0 || value is Float && value < 0.0f
    }

    /**
     * Retorna o value
     *
     * @return o value como instância de [Number]
     */
    fun getValue(): Number = value

}