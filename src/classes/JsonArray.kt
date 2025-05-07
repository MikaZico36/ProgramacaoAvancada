package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

/**
 * Representa um array Json, composto por uma lista de elementos [JsonValue]
 *
 *
 * Implementa a interface [JsonValue],permitindo assim ser usada em estruturas Json
 * Contém métodos para a manipulação funcionald da lista
 *
 * @property values Lista de elementos Json pertencentes a este array
 */
data class JsonArray(private val values: List<JsonValue>): JsonValue {

    /**
     * Retorna a representação em string do array, conforme o padrão Json
     *
     * @return Uma string com os elementos formatados como um array Json
     */
    override fun toJsonString(): String {
        return "[${values.joinToString { it.toJsonString() }}]"
    }


    /**
     * Filtra os elementos do array com base num predicado fornecido
     *
     * @param predicate Função que retorna 'true' para os elementos que devem ser mantidos
     * @return um novo [JsonArray] contendo apenas os elementos que satisfazem o predicado
     */
    fun filter(predicate: (JsonValue) -> Boolean): JsonValue {
        val filteredValues = values.filter(predicate)
        return JsonArray(filteredValues)
    }

    /**
     * Aplica uma tranformação a cada elemento do array
     *
     * @param tranform Função que transforma cada elemento [JsonValue]
     * @return um novo [JsonArray] com os valores transformados
     *
     */
    fun mapping(transform: (JsonValue) -> JsonValue): JsonArray {
        val mappedValues = values.map(transform)
        return JsonArray(mappedValues)
    }

    /**
     * Aceita um visitor que implemente a interface [JsonVisitor]
     *
     *Este método permite que a lógica de processamento seja separada da estrutura de dados ao seguir o padrão de Visitor
     *
     * @param visitor O visitor que irá processar este objeto
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitArray(this)
        values.forEach { it.accept(visitor) }
    }

    /**
     * Retorna o elemento na posição especifica do array
     *
     * @param index Posição do elemento a ser retornado
     * @return o elemento [JsonValue] na posição indicada, ou 'null' se for fora dos limites
     */
    fun get(index: Int): JsonValue? {
        return if (index in values.indices) {
            values[index]
        } else {
            null
        }
    }

    /**
     * Retorna a quantidade de elementos contidos no value
     *
     * @return o tamanho do value
     */
    fun size(): Int {
        return values.size
    }

    /**
     * Verifica se o value est+a vazio
     *
     * @return 'true' se não houver elementos ou 'false' caso contenha elementos
     */
    fun isEmpty(): Boolean {
        return values.isEmpty()
    }
}