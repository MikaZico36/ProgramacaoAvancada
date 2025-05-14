package framework



import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

class GetJSon(vararg c: KClass<*>){

    val jMethods = mutableMapOf<String, KFunction<*>>()

    init {
        val path = c.mapNotNull { it.findAnnotation<Mapping>() }
        for (clazz in c) {
            val methods = clazz.members.filterIsInstance<KFunction<*>>()
            jMethods.putAll(
                methods.filter { it.findAnnotation<Mapping>() != null }
                    .associateBy { it.findAnnotation<Mapping>()!!.name }
            )
        }
        for(jMethod in jMethods){
            println("Method: ${jMethod.key} -> ${jMethod.value}")
        }
    }




    fun start(port: Int){
        val server = HttpServer.create(InetSocketAddress(port), 0)
        server.executor = null
        server.start()
    }

}

fun main(){
    val app = GetJSon(Test::class)
    app.start(8080)
}