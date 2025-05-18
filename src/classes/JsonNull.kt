package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

/**
 * Representa um valor null em estrutura Json
 *
 * Esta classe é genérica para poder receber qualquer tipo de objeto e é utilizada para encapsular valores null
 * garantindo assim que apenas 'null' seja aceite
 *
 * Implementa a interface [JsonValue],permitindo assim ser usada em estruturas Json

 */
class JsonNull: JsonValue {
    /**
     * Retorna a representação de null em formato Json
     *
     * @return string "null", segundo o padrão Json
     */
    override fun toJsonString(): String {
        return "null"
    }
    /**
     * Aceita um visitor que implemente a interface [JsonVisitor]
     *
     *Este método permite que a lógica de processamento seja separada da estrutura de dados ao seguir o padrão de Visitor
     *
     * @param visitor O visitor que irá processar este objeto
     */
    override fun accept(visitor: JsonVisitor) {
        return visitor.visitNull(this)
    }

}