package classes

import classes.interfaces.JsonValue
import classes.primitive.JsonBoolean
import classes.primitive.JsonNull
import classes.primitive.JsonNumber
import classes.primitive.JsonString
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class JsonModel {

    fun toJsonModel(value:Any?): JsonValue {

        return when (value){
            null -> JsonNull(value)
            is String -> JsonString(value)
            is Number -> JsonNumber(value)
            is Boolean -> JsonBoolean(value)
            is List<*> -> JsonArray(value.map { toJsonModel(it) })
            is Map<*,*> -> {
                if (value.keys.all {it is String}) {
                    val pairs = value.entries.map { (key,value) -> key as String to toJsonModel(value) }
                    JsonObject(pairs)
                } else {
                    throw IllegalArgumentException("Only Map<String, *> supported!")
                }
            }

            else -> {
                val kClass = value::class
                if (kClass.isData){
                    val props = kClass.memberProperties.map{ prop ->
                        prop.isAccessible = true
                        val name = prop.name
                        val value = prop.getter.call(value)
                        name to toJsonModel(value)
                    }
                    JsonObject(props)

                }else {
                    throw IllegalArgumentException("Type not Supported!")
                }
            }

        }



    }



}