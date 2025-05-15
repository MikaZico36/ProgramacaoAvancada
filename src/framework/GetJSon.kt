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

    data class Route(
        val pathPattern: Regex,
        val pathVariableNames: List<String>,
        val controller: Any,
        val method: KFunction<*>
    )

    private val routes = mutableListOf<Route>()

    init {
        for (clazz in controllers) {
            val basePath = clazz.findAnnotation<Mapping>()?.name ?: ""
            val controllerInstance = clazz.createInstance()

            for (function in clazz.memberFunctions) {
                val mapping = function.findAnnotation<Mapping>() ?: continue  // ← Adiciona isto
                val subPath = mapping.name
                val fullPath = "/$basePath/$subPath".replace("//", "/")

                val (regex, variables) = buildPathRegex(fullPath)

                routes.add(Route(regex, variables, controllerInstance, function))
            }
        }

        for (route in routes) {
            println("Route: ${route.pathPattern} -> ${route.controller::class.simpleName}.${route.method.name}")
        }
    }

    fun start(port: Int) {
        val server = HttpServer.create(InetSocketAddress(port), 0)

        server.createContext("/") { exchange ->
            val requestPath = exchange.requestURI.path
            val route = routes.firstOrNull { it.pathPattern.matches(requestPath) }

            if (route == null) {
                exchange.sendResponseHeaders(404, 0)
                exchange.responseBody.use { it.write("Not found".toByteArray()) }
                return@createContext
            }

            Handler(route).handle(exchange)
        }

        server.executor = null
        server.start()
    }

    private class Handler(val route: Route) : HttpHandler {
        override fun handle(exchange: HttpExchange) {
            try {
                val queryParams = parseQueryParams(exchange.requestURI)

                val match = route.pathPattern.matchEntire(exchange.requestURI.path)
                val pathParams = route.pathVariableNames.zip(match!!.groupValues.drop(1)).toMap()

                val args = route.method.parameters.map { param ->
                    when {
                        param.kind == KParameter.Kind.INSTANCE -> route.controller
                        param.findAnnotation<Param>() != null && param.type.classifier == Map::class -> queryParams
                        param.findAnnotation<Param>() != null -> convertParam(queryParams[param.name], param.type)
                        param.findAnnotation<Path>() != null -> convertParam(pathParams[param.name], param.type)
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
                val error = """{ "error": "${e.message}" }"""
                println("Exception: ${e.message}")
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
                Boolean::class.createType() -> value.toBooleanStrictOrNull()
                Number::class.createType() -> value.toDoubleOrNull() // converte para Double como padrão
                else -> throw IllegalArgumentException("Unsupported parameter type: $type")
            }
        }
    }


    private fun buildPathRegex(path: String): Pair<Regex, List<String>> {
        val variableRegex = "\\{([^/]+?)\\}".toRegex()
        val variableNames = variableRegex.findAll(path).map { it.groupValues[1] }.toList()
        val regexPattern = "^" + path.replace(variableRegex, "([^/]+)") + "$"
        return Regex(regexPattern) to variableNames
    }
}