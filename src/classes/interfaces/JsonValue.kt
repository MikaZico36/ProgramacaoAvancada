package classes.interfaces

/**
 * Reflete uma JsonValue genérico. Esse JsonValue pode ter qualquer valor (string,inteiro, decimal, boolean,...)
 *
 * Interface contém dois métodos a serem implementados:
 * toJsonString(): String -> retorna a string que representa um JsonValue
 * accept(visitor: JsonVisitor) -> permite que um visitor realize ações num JsonValue
 *
 * Implementações desta interface
 * - [JsonBoolean] representa um Boolean em Json
 * - [JsonNull] representa um null em Json
 * - [JsonNumber] representa um inteiro, decimal, potência, ... em Json
 * - [JsonString] representa uma string em Json
 * 
 *
 */
interface JsonValue {
    /**
     * Converte um JsonValue para uma string
     * @return a string que representa o Json
     */
    fun toJsonString(): String

    /**
     * Aceita um visitor que fará uma ação sobre este valor Json
     */
    fun accept(visitor: JsonVisitor)
}