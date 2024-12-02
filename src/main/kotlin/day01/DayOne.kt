package day01

import kotlin.math.abs


object DayOne {

    suspend fun solvePuzzle() {
        val data = AocClient.getAocDayInput(1)
        partOne(data)
        partTwo(data)
    }

    private fun partOne(data: Array<String>) {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        val resultList = mutableListOf<Int>()

        val values = data.map { it.split(" ") }.map { list -> list.filter { it.isNotBlank() } }
        values.map {
            leftList.add(it.first().toInt())
            rightList.add(it.last().toInt())
        }

        while (leftList.isNotEmpty() && rightList.isNotEmpty()) {
            val minLeft = leftList.minOrNull() ?: break
            leftList.remove(minLeft)

            val minRight = rightList.minOrNull() ?: break
            rightList.remove(minRight)
            resultList.add(abs(minLeft - minRight))
        }
        println("Day1 Part1: " + resultList.sum())
    }

    private fun partTwo(data: Array<String>) {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        val resultList = mutableListOf<Int>()

        val values = data.map { it.split(" ") }.map { list -> list.filter { it.isNotBlank() } }
        values.map {
            leftList.add(it.first().toInt())
            rightList.add(it.last().toInt())
        }

        while (leftList.isNotEmpty()) {
            val leftValue = leftList.minOrNull() ?: break
            leftList.remove(leftValue)

            val occuranceCount = rightList.count { leftValue == it }
            resultList.add(leftValue * occuranceCount)

        }

        println("Day1 Part2: " + resultList.sum())
    }

}