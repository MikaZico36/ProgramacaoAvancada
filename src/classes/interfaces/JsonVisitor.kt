package classes.interfaces

import classes.JsonArray
import classes.JsonObject
import classes.primitive.JsonBoolean
import classes.primitive.JsonNull
import classes.primitive.JsonNumber
import classes.primitive.JsonString

interface JsonVisitor {
    fun visitObject(jsonObject: JsonObject)
    fun visitArray(jsonArray: JsonArray)
    fun visitString(jsonString: JsonString)
    fun visitBoolean(jsonBoolean: JsonBoolean)
    fun visitNull(jsonNull: JsonNull<*>)
    fun visitNumber(jsonNumber: JsonNumber)
}