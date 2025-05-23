package classes.interfaces

import classes.JsonArray
import classes.JsonBoolean
import classes.JsonNull
import classes.JsonNumber
import classes.JsonObject
import classes.JsonString


/**
 * Interface que representa os posiveis visitors do JsonValues
 *
 * Implementações desta interface:
 * - [ValidatorVisitor]
 *
 * @see JsonValue
 */
interface JsonVisitor {

    /**
    * Visita um JsonObject ({ ... }) e executa uma operação sobre ele.
    *
    * @param jsonObject o objeto JSON a ser visitado.
    */
    fun visitObject(jsonObject: JsonObject)

    /**
    * Visita um JsonArray  ([ ... ]) e executa uma operação sobre ele.
    *
    * @param jsonArray o array JSON a ser visitado.
    */
    fun visitArray(jsonArray: JsonArray)

    /**
    * Visita uma JsonString e executa uma operação sobre ela.
    *
    *  @param jsonString o valor do tipo string a ser visitado.
    */
    fun visitString(jsonString: JsonString)

    /**
    * Visita um JsonValue boolean (true ou false) e executa uma operação sobre ele.
    *
    * @param jsonBoolean o valor boolean a ser visitado.
    */
    fun visitBoolean(jsonBoolean: JsonBoolean)

    /**
    * Visita um valor JsonValue nulo (null) e executa uma operação sobre ele.
    *
    * @param jsonNull o valor nulo a ser visitado.
    */
    fun visitNull(jsonNull: JsonNull)

    /**
    * Visita um JsonNumber (inteiro, decimal, potência,...) e executa uma operação sobre ele.
    *
    * @param jsonNumber o valor numérico a ser visitado.
    */
    fun visitNumber(jsonNumber: JsonNumber)

}