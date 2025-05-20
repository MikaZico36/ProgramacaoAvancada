package framework

import classes.JsonModel
import classes.interfaces.JsonValue
import java.nio.file.Paths

/**
 * Class Controller que possui endpoints JSON para diferentes tipos de dados.
 *
 * Cada método é mapeado através de um path HTTP que aceita parâmetros através de query string e retorna um JsonValue.
 */
@Mapping("Json")
class Controller {

    /**
     * Retorna uma string com a lista de paths disponíveis e os métodos correspondentes através de reflexão na classe
     *
     * @return String que contém os paths
     */
    @Mapping("paths")
    fun paths(): String {
        val basicPath = this::class.annotations
            .filterIsInstance<Mapping>()
            .firstOrNull()
            ?.name ?: ""

        val pathsList = this::class.members
            .filter { it.annotations.any { a -> a is Mapping } }
            .map { method ->
                val path = method.annotations
                    .filterIsInstance<Mapping>()
                    .first()
                    .name
                "Method Name: ${this::class.simpleName}.${method.name} | Route: ^/$basicPath/$path\$"
            }

        return pathsList.joinToString("\n")
    }

    /**
     * Recebe um mapa de atributos através de query string e converte para JsonValue.
     *
     * Verifica a quantidade de string com o mesmo nome. Caso sejam maior que 1 então considera que é um array,
     *  caso contrário considera que é um atributo isolado
     * @param attributes Mapa de pares key-value que representa os atributos.
     * @return JsonValue representa o objeto JSON criado.
     */
    @Mapping("createobject")
    fun createObject(@Param attributes: Map<String, List<String>>): JsonValue {
        val processedMap = attributes.mapValues { (_, values) ->
            if (values.size == 1) {
                values.first()
            } else {
                values
            }
        }

        return JsonModel().toJsonModel(processedMap)    }

    /**
     * Recebe um número (int, double, etc) através de query string e converte para JsonValue.
     *
     * @param attribute Número recebido como parâmetro.
     * @return JsonValue representa o número.
     */
    @Mapping("number")
    fun number(@Param attribute: String): JsonValue {
        return JsonModel().toJsonModel(attribute)
    }

    /**
     * Recebe uma list através de query string e converte para JsonValue
     *
     * @param attributes Lista recebida como parâmetros
     * @return Jsonvalue representa a list convertida para json
     *
     *
     */
    @Mapping("array")
    fun array(@Param attributes: List<String>): JsonValue {
        return JsonModel().toJsonModel(attributes)
    }
    /**
     * Recebe uma string através query string e converte para JsonValue.
     *
     * @param attribute String recebida como parâmetro.
     * @return JsonValue representando a string.
     */
    @Mapping("string")
    fun string(@Param attribute: String): JsonValue {
        return JsonModel().toJsonModel(attribute)
    }

    /**
     * Recebe um valor boolean através de uma  query string e converte para JsonValue.
     *
     * @param attribute Boolean recebido como parâmetro.
     * @return JsonValue representa o valor boolean.
     */
    @Mapping("bool")
    fun boolean(@Param attribute: Boolean): JsonValue {
        return JsonModel().toJsonModel(attribute)
    }


}