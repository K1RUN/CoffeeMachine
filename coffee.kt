package machine

class CoffeeMachine {
    enum class State {
        MENU,
        ORDERING,
        FILL_WATER,
        FILL_MILK,
        FILL_COFFEE,
        FILL_CUPS,
        EXITING;
    }
    data class Contents(var water: Int, var milk: Int, var coffee: Int, var cups: Int, var money: Int)
    private var current = Contents(400, 540, 120, 9, 550)
    enum class CoffeeTypes(
        private val water: Int,
        private val milk: Int,
        private val coffee: Int,
        private val cups: Int,
        private val money: Int
        ) {
        ESPRESSO(250, 0, 16, 1, 4 ),
        LATTE(350, 75, 20, 1, 7),
        CAPPUCCINO(200, 100,12, 1, 6);

        fun pick(contents: Contents) {
            when {
                this.water > contents.water -> println("Sorry, not enough water!")
                this.milk > contents.milk -> println("Sorry, not enough milk!")
                this.coffee > contents.coffee -> println("Sorry, not enough coffee beans!")
                else -> {
                    println("I have enough resources, making you a coffee!")
                    contents.water -= this.water
                    contents.milk -= this.milk
                    contents.coffee -= this.coffee
                    contents.cups -= this.cups
                    contents.money += this.money
                }
            }
        }
    }
    private var state = State.MENU
    fun running() = state != State.EXITING
    fun info(): String {
        when(state) {
            State.MENU -> return "Write action (buy, fill, take, remaining, exit): "
            State.ORDERING -> {
                return "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: "
            }
            State.FILL_WATER -> {
                return "Write how many ml of water do you want to add: "
            }
            State.FILL_MILK -> {
                return "Write how many ml of milk do you want to add: "
            }
            State.FILL_COFFEE -> {
                return "Write how many grams of coffee beans do you want to add: "
            }
            State.FILL_CUPS -> {
                return "Write how many disposable cups of coffee do you want to add: "
            }
            else -> {return ""}
        }
    }
    fun run(input: String) {
        when(state) {
            State.MENU -> {
                when(input) {
                    "buy" -> state = State.ORDERING
                    "fill" -> state = State.FILL_WATER
                    "take" -> {
                        println("I gave you $${current.money}")
                        current.money = 0
                        state = State.MENU
                    }
                    "remaining" -> {
                        println("""
                            The coffee machine has:
                            ${current.water} of water
                            ${current.milk} of milk
                            ${current.coffee} of coffee beans
                            ${current.cups} of cups
                            $${current.money} of money
                            """.trimIndent())
                        state = State.MENU
                    }
                    "exit" -> state = State.EXITING
                }
            }
            State.ORDERING -> {
                when(input) {
                    "1" -> CoffeeTypes.ESPRESSO.pick(current)
                    "2" -> CoffeeTypes.LATTE.pick(current)
                    "3" -> CoffeeTypes.CAPPUCCINO.pick(current)
                }
                state = State.MENU
            }
            State.FILL_WATER -> {
                current.water += input.toInt()
                state = State.FILL_MILK
            }
            State.FILL_MILK -> {
                current.milk += input.toInt()
                state = State.FILL_COFFEE
            }
            State.FILL_COFFEE -> {
                current.coffee += input.toInt()
                state = State.FILL_CUPS
            }
            State.FILL_CUPS -> {
                current.cups += input.toInt()
                state = State.MENU
            }
            else -> state = State.EXITING
        }
    }
}