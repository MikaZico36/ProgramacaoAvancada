package framework

import classes.JsonModel
import classes.JsonObject
import classes.interfaces.JsonValue

/**
 * Class Controller que possui endpoints JSON para diferentes tipos de dados.
 *
 * Cada método é mapeado através de um path HTTP que aceita parâmetros através de query string e retorna um JsonValue.
 */
@Mapping("Json")
class Controller {

    /**
     * Retorna uma string com a lista de paths disponíveis e os métodos correspondentes
     *
     * @return String que contém os paths
     */
    @Mapping("paths")
    fun paths(): String {
        return "Route: ^/Json/array$ -> Controller.array\n" +
                "Route: ^/Json/bool$ -> Controller.boolean\n" +
                "Route: ^/Json/createobject$ -> Controller.createObject\n" +
                "Route: ^/Json/number$ -> Controller.number\n" +
                "Route: ^/Json/paths$ -> Controller.paths\n" +
                "Route: ^/Json/string$ -> Controller.string"
    }

    /**
     * Recebe um mapa de atributos através de query string e converte para JsonValue.
     *
     * @param attributes Mapa de pares key-value que representa os atributos.
     * @return JsonValue representa o objeto JSON criado.
     */
    @Mapping("createobject")
    fun createObject(@Param attributes: Map<String, String>): JsonValue {
        return JsonModel().toJsonModel(attributes)
    }

    /**
     * Recebe um número (int, double, etc) através de query string e converte para JsonValue.
     *
     * @param attribute Número recebido como parâmetro.
     * @return JsonValue representa o número.
     */
    @Mapping("number")
    fun number(@Param attribute: Number): JsonValue {
        return JsonModel().toJsonModel(attribute)
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