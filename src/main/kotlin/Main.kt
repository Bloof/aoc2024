import day01.DayOne
import day02.DayTwo

suspend fun main(args: Array<String>) {


    println("Enter the day to solve as number (e.g '1')")

    val input = readLine() ?: run {
        println("No input received. Exiting")
        return
    }

    when(input) {
        "1" -> DayOne.solvePuzzle()
        "2" -> DayTwo.solvePuzzle()

        else -> println("Day not implemented")
    }
}