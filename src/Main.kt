import framework.Controller
import framework.GetJSon

fun main(){
    val app = GetJSon(Controller::class)
    app.start(8080)
}