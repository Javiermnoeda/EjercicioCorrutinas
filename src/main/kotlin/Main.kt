import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.Thread.sleep

var cubosActuales = 0
var lenaActual = 0
var ramasActuales = 0
var comidaActual = 0

const val CUBOS_NECESARIOS = 4
const val LENA_NECESARIA  = 2
const val RAMAS_NECESARIAS  = 1
const val COMIDA_NECESARIA  = 1

var hamaca = Mutex()
var hacha = Mutex()

suspend fun main() {
    empezar()
    Thread.sleep(80000)
}

suspend fun empezar(){
    runBlocking {
        amigoA()
        amigoB()
        amigoC()

        if (cubosActuales == CUBOS_NECESARIOS && lenaActual == LENA_NECESARIA && ramasActuales == RAMAS_NECESARIAS && comidaActual == COMIDA_NECESARIA){
            println("YA SE HA CONSTRUIDO LA BARCA!!!, ahora podemos escapar")
        }

    }
}

fun amigoA (){
    GlobalScope.async {
        repeat(4){
            println("Amigo A se dirige a por algunos cubos de agua")
            delay(3000)
            println("Amigo A ha llenado los cubos de agua")
            descansar(1000,"Amigo A")
            cubosActuales++
        }
    }
}
fun amigoB(){
    GlobalScope.async {
        repeat(2){
            println("Amigo B se dirige a por algo de leña")
            hacha(5000,"El amigo B")
            lenaActual++
            descansar(3000,"Amigo B")
        }
    }
}

fun amigoC(){
    GlobalScope.async {
        println("El amigo C va a por ramas")
        println("El amigo C encuentra las ramas y regresa con ellas")
        delay(3000)
        ramasActuales++
        hacha(4000,"Amigo C")
        comidaActual++
    }
}
suspend fun descansar (tiempo : Long, nombre :String){
    println("$nombre va a descansar")
    hamaca.withLock {
        println("$nombre se tumba")
        sleep(tiempo)
        println("$nombre, se levanta")
    }
}

suspend fun hacha (tiempo: Long, nombre: String){
    println("$nombre va a cortar")
    hacha.withLock {
        println("$nombre coge el hacha")
        delay(tiempo)
        println("$nombre deja el hacha")
        println("$nombre regresa con leña")
    }
}

