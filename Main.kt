package machine

fun main() {
    val myMachine = CoffeeMachine()
    while(myMachine.running()) {
        println(myMachine.info())
        myMachine.run(readln())
    }
}
