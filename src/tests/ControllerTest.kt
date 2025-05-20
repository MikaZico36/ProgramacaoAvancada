package tests

import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


/**
 * Testa a classe [Controller] e os seus endpoints.
 *
 * Os testes verificam se, através de um URL é possível criar JSonValues.
 */
class CreateObjectTest {

    private val client = OkHttpClient()

    /**
     * Testa o endpoint /Json/createobject que recebe um mapa de atributos e retorna JSON.
     */
    @Test
    fun testCreateObjectEndpoint() {
        val url = "http://localhost:8080/Json/createobject?name=Cristiano&age=40&active=true"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string()

            assertTrue(response.isSuccessful)
            assertTrue(body!!.contains("\"name\": \"Cristiano\""))
            assertTrue(body.contains("\"age\": 40"))
            assertTrue(body.contains("\"active\": true"))
        }
    }

    /**
     * Testa o endpoint /Json/number que recebe um número e retorna JSON.
     */
    @Test
    fun testNumberEndpoint() {
        val url = "http://localhost:8080/Json/number?attribute=123"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string()
            assertTrue(response.isSuccessful)
            assertTrue(body!!.contains("123"))
        }
    }

    /**
     * Testa o endpoint /Json/string que recebe uma string e retorna JSON.
     */
    @Test
    fun testStringEndpoint() {
        val url = "http://localhost:8080/Json/string?attribute=HelloWorld"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string()

            assertTrue(response.isSuccessful)
            assertTrue(body!!.contains("HelloWorld"))
        }
    }

    /**
     * Testa o endpoint /Json/bool que recebe um booleano e retorna JSON.
     */
    @Test
    fun testBooleanEndpoint() {
        val url = "http://localhost:8080/Json/bool?attribute=true"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string()

            assertTrue(response.isSuccessful)
            assertTrue(body!!.contains("true"))
        }
    }

    /**
     * Testa o endpoint /Json/paths que retorna a lista de rotas como string.
     */
    @Test
    fun testPathsEndpoint() {
        val url = "http://localhost:8080/Json/paths"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string()

            assertTrue(response.isSuccessful)
            assertTrue(body!!.contains("Route:"))
            assertTrue(body.contains("Controller.array"))
            assertTrue(body.contains("Controller.boolean"))
            assertTrue(body.contains("Controller.createObject"))
        }
    }

    /**
     * Testa o endpoint /Json/array que retorna uma lista de elementos dados no URL.
     */
    @Test
    fun testArrayEndpoint() {
        val url = "http://localhost:8080/Json/array?attributes=a&attributes=b&attributes=c"
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response ->
            val body = response.body?.string()
            assertTrue(response.isSuccessful)
            assertTrue(body!!.contains("\"a\""))
            assertTrue(body.contains("\"b\""))
            assertTrue(body.contains("\"c\""))
            assertTrue(body.startsWith("["))
            assertTrue(body.endsWith("]"))
        }
    }


}
