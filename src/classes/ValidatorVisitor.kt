package classes

import classes.interfaces.JsonValue
import classes.interfaces.JsonVisitor
import classes.primitive.JsonBoolean
import classes.primitive.JsonNull
import classes.primitive.JsonNumber
import classes.primitive.JsonString

class ValidatorVisitor: JsonVisitor {
    private var validator: Boolean = true
    
    
    
    override fun visitObject(jsonObject: JsonObject) {
        validator = checkJsonObject(jsonObject)
    }

    override fun visitArray(jsonArray: JsonArray) {
        validator = checkJsonArray(jsonArray)
    }

    override fun visitString(jsonString: JsonString) {}
    override fun visitBoolean(jsonBoolean: JsonBoolean) {}

    override fun visitNull(jsonNull: JsonNull<*>) {}

    override fun visitNumber(jsonNumber: JsonNumber) {}
    
    fun getValidator(): Boolean {
        return validator
    }

    private fun checkJsonArray(jsonArray: JsonArray, index: Int = 0 ): Boolean {
        if(index == jsonArray.size()) return true
        val current = jsonArray.get(index)
        val next = jsonArray.get(index+1)
        if (current != null && next !=null) {
            val currentClass = current::class
            val nextClass = next::class
            if (currentClass != nextClass ) {
                return false
            }
        }
        return checkJsonArray(jsonArray, index + 1)

    }
    
    private fun checkJsonObject(jsonObject: JsonObject, index: Int=0): Boolean {
        val entries = jsonObject.getEntries()
        if(index == entries.size) return true
        if (entries.isEmpty()) return false
        val head = entries[index]
        for (i in (index + 1) until entries.size) {
            if ( entries[i] == head) {
                return false
            }
        }
        return checkJsonObject(jsonObject, index +1)
    }

    
}