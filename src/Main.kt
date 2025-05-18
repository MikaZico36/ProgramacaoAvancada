import framework.Controller
import framework.GetJSon

/**
 * Função que inicia o servidor HTTP na porta 8080
 *
 * Esta função cria uma instância do framework [GetJson], registando a classe [Controller]
 * como responsável pelos endpoints. O servidor fica à escuta de pedidos HTTP na porta especifica
 *
 */
fun main(){
    val app = GetJSon(Controller::class)
    app.start(8080)
}