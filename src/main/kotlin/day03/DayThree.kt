package day03

object DayThree {

    suspend fun solvePuzzle() {
        val data = AocClient.getAocDayInput(3, AocClient.InputReturnType.STRING) as String
        partOne(data)
        partTwo(data)
    }

    private fun partOne(data: String) {
        val regex = Regex("mul\\((\\d{1,3}|1000),(\\d{1,3}|1000)\\)")
        val matches = regex.findAll(data)
        val result = mutableListOf<Int>()
        matches.forEach { match ->
            val firstNumber = match.groups[1]?.value?.toInt()
            val secondNumber = match.groups[2]?.value?.toInt()
            if (secondNumber != null) {
                firstNumber?.times(secondNumber)?.let { result.add(it) }
            }
        }
        println("Day3 Part1: " + result.sum())
    }

    private fun partTwo(data: String) {
        val regex = Regex("mul\\((\\d{1,3}|1000),(\\d{1,3}|1000)\\)")
        val dontRegex = Regex("don't\\(\\)")
        val doRegex = Regex("do\\(\\)")
        val result = mutableListOf<Int>()

        var doFound = true
        val matches = Regex("${dontRegex.pattern}|${doRegex.pattern}|${regex.pattern}").findAll(data)
        for (match in matches) {
            when {
                dontRegex.matches(match.value) -> {
                    doFound = false
                }
                doRegex.matches(match.value) -> {
                    doFound = true
                }
                regex.matches(match.value) -> {
                    if (doFound) {
                        val firstNumber = match.groups[1]?.value?.toInt()
                        val secondNumber = match.groups[2]?.value?.toInt()
                        if (secondNumber != null) {
                            firstNumber?.times(secondNumber)?.let { result.add(it) }
                        }
                    }
                }
            }
        }
        println("Day3 Part2: " + result.sum())

    }
}