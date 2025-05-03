package classes.interfaces

import classes.JsonArray
import classes.JsonObject
import classes.primitive.JsonBoolean
import classes.primitive.JsonDouble
import classes.primitive.JsonInteger
import classes.primitive.JsonNull
import classes.primitive.JsonString

interface JsonVisitor {
    fun visitObject(jsonObject: JsonObject)
    fun visitArray(jsonArray: JsonArray)
    fun visitString(jsonString: JsonString)
    fun visitInteger(jsonInteger: JsonInteger)
    fun visitDouble(jsonDouble: JsonDouble)
    fun visitBoolean(jsonBoolean: JsonBoolean)
    fun visitNull(jsonNull: JsonNull<*>)
}