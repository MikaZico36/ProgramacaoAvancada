package framework

import classes.interfaces.JsonValue
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.net.URI
import java.net.URLDecoder
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

class GetJSon(vararg controllers: KClass<*>) {

    //NAO SEI SE É MESMO NECESSARIO, ACHAVA QUE BASTAVA O MAP COM STRINGS E COM O KFUNCTION
    data class Route(
        val path: String,
        val controller: Any,
        val method: KFunction<*>
    )

    private val routes = mutableMapOf<String, Route>()

    init {
        for (clazz in controllers) {
            val basePath = clazz.findAnnotation<Mapping>()?.name ?: String()
            val controllerInstance = clazz.createInstance()

            for (function in clazz.memberFunctions) {
                val subPath = function.findAnnotation<Mapping>()?.name ?: String()
                val fullPath = "/$basePath/$subPath".replace("//", "/")

                routes[fullPath] = Route(fullPath, controllerInstance, function)
            }
            for(pair in routes){
                println("Route: ${pair.key} -> ${pair.value.controller::class.simpleName}.${pair.value.method.name}")
            }
        }
    }

    fun start(port: Int) {
        val server = HttpServer.create(InetSocketAddress(port), 0)

        for ((path, route) in routes) {
            server.createContext(path, Handler(route))
        }

        server.executor = null
        server.start()
        println("Server started on port $port")
    }

    private class Handler(val route: Route) : HttpHandler {
        override fun handle(exchange: HttpExchange) {
            try {
                val queryParams = parseQueryParams(exchange.requestURI)

                val args = route.method.parameters.map { param ->
                    when {
                        param.kind == KParameter.Kind.INSTANCE -> route.controller
                        param.findAnnotation<Param>() != null -> convertParam(queryParams[param.name], param.type)
                        else -> null
                    }
                }

                route.method.isAccessible = true
                val result = route.method.call(*args.toTypedArray())

                val response = when (result) {
                    is JsonValue -> result.toJsonString()
                    else -> "\"${result.toString()}\""
                }

                exchange.sendResponseHeaders(200, response.toByteArray().size.toLong())
                exchange.responseBody.use { it.write(response.toByteArray()) }

            } catch (e: Exception) {
                val error = "Error: ${e.message}"
                exchange.sendResponseHeaders(500, error.toByteArray().size.toLong())
                exchange.responseBody.use { it.write(error.toByteArray()) }
            }
        }

        private fun parseQueryParams(uri: URI): Map<String, String> {
            val query = uri.rawQuery ?: return emptyMap()
            return query.split("&").mapNotNull {
                val parts = it.split("=")
                if (parts.size == 2) parts[0] to URLDecoder.decode(parts[1], "UTF-8") else null
            }.toMap()
        }

        private fun convertParam(value: String?, type: kotlin.reflect.KType): Any? {
            if (value == null) return null
            return when (type) {
                String::class.createType() -> value
                Int::class.createType() -> value.toIntOrNull()
                Double::class.createType() -> value.toDoubleOrNull()
                Boolean::class.createType() -> value.toBooleanStrictOrNull()
                else -> throw IllegalArgumentException("Unsupported parameter type: $type")
            }
        }
    }
}

fun main(){
    val app = GetJSon(Test::class)
    app.start(8080)
}