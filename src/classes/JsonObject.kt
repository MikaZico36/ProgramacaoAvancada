package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor

/**
 * Representa um objeto Json composto por um Map, onde a chave é uma [String] e o valor implementa a interface [JsonValue]
 *
 * Implementa a interface [JsonValue], permitindo assim ser usada em estruturas Json
 *
 * @property values Mapa que representa as propriedades do objeto
 */
data class JsonObject(private val values: Map<String, JsonValue>) : JsonValue {

    /**
     * Recupera o valor associado a uma key específica
     *
     * @param key A chave que se deseja procurar no objeto
     * @return O valor correspondente à chave, ou 'null' se não for encontrado
     */
    fun get(key: String): JsonValue? = values[key]

    /**
     * Retorna a lista de todas as chaves presentes neste objeto
     *
     * @return Lista de strings que contém os nomes das chaves
     */
    fun getEntries(): List<String> = values.keys.toList()

    /**
     * Converte o objeto para a sua representação textual em Json
     *
     * @return Uma string no formato Json, com todas as chaves e valores formatados
     */
    override fun toJsonString(): String {
        val content = values.entries.joinToString(",") { (key, value) ->
            "\"$key\": ${value.toJsonString()}"
        }
        return "{$content}"
    }

    /**
     * Aceita um visitor que implemente a interface [JsonVisitor]
     *
     * Este método permite que a lógica de processamento seja separada da estrutura de dados ao seguir o padrão de Visitor
     *
     * @param visitor O visitor que irá processar este objeto
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visitObject(this)
        values.values.forEach { value -> value.accept(visitor) }
    }

    /**
     * Filtra os pares chave-valor com base num predicado aplicado ao valor
     *
     * @param predicate Função que define quais valores devem ser mantidos
     * @return Um novo [JsonObject] que contém apenas os pares cujos valores satisfazem o predicado
     */
    fun filter(predicate: (JsonValue) -> Boolean): JsonObject {
        val filteredValues = values.filterValues(predicate)
        return JsonObject(filteredValues)
    }
}
