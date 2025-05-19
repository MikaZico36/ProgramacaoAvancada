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

/**
 * Framework para criar servidores HTTP que respondem a pedidos GET para métodos de controladores Kotlin.
 *
 * A classe `GetJSon` regista métodos anotados com `@Mapping` nas classes, com o objetivo de criar  rotas dinâmicas
 * com parâmetros de path e de query string. As respostas dos métodos são serializadas para JSON se
 * implementarem a interface `JsonValue`.
 *
 * @constructor Recebe uma ou mais classes Kotlin que atuam como controladores. Cada método público anotado com
 * `@Mapping` será exposto como uma path HTTP GET.
 *
 * @param controllers Lista vararg de classes que representam controladores. Essas classes devem ter métodos anotados
 * com `@Mapping` e devem possuir um construtor sem parâmetros.
 *
 * */



class GetJSon(vararg controllers: KClass<*>) {

    /**
     * Representa um path HTTP mapeada para um método de controlador.
     *
     * @property pathPattern Regex que representa o padrão de path.
     * @property pathVariableNames Lista dos nomes das variáveis extraídas do path.
     * @property controller Instância da classe de controlo.
     * @property method Método usado quando o path for acionado.
     */

    data class Route(
        val pathPattern: Regex,
        val pathVariableNames: List<String>,
        val controller: Any,
        val method: KFunction<*>
    )

    private val routes = mutableListOf<Route>()

    /**
     * Gera um par de regex e variáveis extraídas de um caminho anotado.
     *
     * @param path Caminho da rota.
     *
     * @return Um [Pair] contendo:
     * - um [Regex] que pode ser usado para a URL pedida e extrair os valores das variáveis;
     * - uma [List] de [String] com os nomes das variáveis encontradas no path.
     *
     */
    private fun buildPathRegex(path: String): Pair<Regex, List<String>> {
        val variableRegex = "\\{([^/]+?)\\}".toRegex()
        val variableNames = variableRegex.findAll(path).map { it.groupValues[1] }.toList()
        val regexPattern = "^" + path.replace(variableRegex, "([^/]+)") + "$"
        return Regex(regexPattern) to variableNames
    }

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

    /**
     * Inicia o servidor HTTP na porta especificada.
     *
     * @param port Número da porta onde o servidor HTTP será iniciado.
     */
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

    /**
     * Manipulador HTTP responsável por executar o método correspondente à rota.
     *
     * @property route A rota que corresponde à requisição.
     */
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
                        param.findAnnotation<Param>() != null -> convertParam(
                            queryParams[param.name]?.firstOrNull(),
                            queryParams[param.name],
                            param.type
                        )
                        param.findAnnotation<Path>() != null -> convertParam(
                            pathParams[param.name],
                            listOfNotNull(pathParams[param.name]),
                            param.type
                        )
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

        /**
         * Extrai parâmetros da query string da URI.
         * @param URI que representa o URI que recebe na query
         */
        private fun parseQueryParams(uri: URI): Map<String, List<String>> {
            val query = uri.rawQuery ?: return emptyMap()
            return query.split("&").mapNotNull {
                val parts = it.split("=")
                if (parts.size == 2) parts[0] to URLDecoder.decode(parts[1], "UTF-8") else null
            }.groupBy({ it.first }, { it.second })
        }

        /**
         * Converte um valor de string para o tipo apropriado esperado pelo método do controlador.
         *
         * @param value que é uma string que pode ser null e que diz respeito ao dados
         * @param type que é o tipo do value da string que é conseguido através de reflexão
         *
         * @return Any? que é o parâmetro convertido para o tipo correto. Pode ser null e por isso para além dos tipos
         * normais colocamos a possibilidade de ser null.
         *
         */
        private fun convertParam(single: String?, multiple: List<String>?, type: kotlin.reflect.KType): Any? {
            val classifier = type.classifier
            return when {
                classifier == String::class -> single
                classifier == Boolean::class -> single?.toBooleanStrictOrNull()
                classifier == Int::class -> single?.toIntOrNull()
                classifier == Double::class -> single?.toDoubleOrNull()
                classifier == List::class -> {
                    val argType = type.arguments.firstOrNull()?.type?.classifier
                    multiple?.mapNotNull {
                        when (argType) {
                            String::class -> it
                            Int::class -> it.toIntOrNull()
                            Double::class -> it.toDoubleOrNull()
                            Boolean::class -> it.toBooleanStrictOrNull()
                            else -> throw IllegalArgumentException("Unsupported list element type: $argType")
                        }
                    }
                }
                else -> throw IllegalArgumentException("Unsupported parameter type: $type")
            }
        }
    }
}