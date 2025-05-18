package classes

import classes.interfaces.JsonValue
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

/**
 * Classe responsável por converter instâncias de objetos em representações compatíveis com Json
 *
 * Esta conversão é feita de forma dinâmica, com suporte para
 * -Tipos primitivos
 * -Coleções
 * -Enumerados
 * -Data classes
 *
 * Qualquer outro tipo não suportado resulta numa exceção
 *
 * Implementa a interface [JsonValue],permitindo assim ser usada em estruturas Json
 *
 */
class JsonModel {

    /**
     * Converte um value genérico para uma representação [JsonValue], que pode ser manipulada e serialize para Json
     *
     * Tem ainda uma verificação de tipo de dados na String para tratar principalmente quando os dados estavam disponíveis num URL.
     *
     * @param value O valor a ser convertido
     * @return Um objeto que implementa [JsonValue] que representa o valor no formato Json
     *
     * @throws IllegalArgumentException se o tipo fornecido não for suportado
     */
    fun toJsonModel(value:Any?): JsonValue {
        return when (value){
            null -> JsonNull()
            is String -> {
                when {
                    value.equals("true", true) -> JsonBoolean(true)
                    value.equals("false", true) -> JsonBoolean(false)
                    value.toIntOrNull() != null -> JsonNumber(value.toInt())
                    value.toDoubleOrNull() != null -> JsonNumber(value.toDouble())
                    else -> JsonString(value)
                }
            }
            is Number -> JsonNumber(value)
            is Boolean -> JsonBoolean(value)
            is List<*> -> JsonArray(value.map { toJsonModel(it) })
            is Enum<*> -> JsonString(value.name)
            is Map<*, *> -> {
                if (value.keys.all { it is String }) {
                    val map = value.entries.associate { (key, value) -> key as String to toJsonModel(value) }
                    JsonObject(map)
                } else {
                    throw IllegalArgumentException("Only Map<String, *> supported!")
                }
            }


            else -> {
                val kClass = value::class
                if (kClass.isData) {
                    val params = kClass.primaryConstructor?.parameters?.map { it.name } ?: emptyList()

                    val propertiesMap = kClass.memberProperties.associateBy { it.name }

                    val orderedProperties = params.mapNotNull { name ->
                        val prop = propertiesMap[name]
                        prop?.isAccessible = true
                        val propValue = prop?.getter?.call(value)
                        name?.let { it to toJsonModel(propValue) }
                    }

                    JsonObject(orderedProperties.toMap())

                } else {
                    throw IllegalArgumentException("Type not Supported!")
                }
            }


        }



    }



}