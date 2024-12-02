package day02

import kotlin.math.abs

object DayTwo {


    suspend fun solvePuzzle() {
        val data = AocClient.getAocDayInput(2)
        val safeAndUnsafeRecords = partOne(data)
        partTwo(safeAndUnsafeRecords)
    }

    private fun partOne(data: Array<String>): Pair<MutableList<List<Int>>, MutableList<List<Int>>> {
        val safe = mutableListOf<List<Int>>()
        val unsafe = mutableListOf<List<Int>>()

        val values = data
            .map { it.split(" ") }
            .map { list -> list.map { num -> num.toInt() } }
            .toMutableList()

        while (values.isNotEmpty()) {
            val record = values.first()
            values.remove(record)

            val isIncreasing = record.zipWithNext { a, b ->
                (a < b) && abs(a - b) in 1..3
            }.all { it }

            val isDecreasing = record.zipWithNext { a, b ->
                (a > b) && abs(a - b) in 1..3
            }.all { it }

            if ((isIncreasing || isDecreasing) && !(isIncreasing && isDecreasing)) {
                safe.add(record)
            } else {
                unsafe.add(record)
            }
        }
        println("Day2 Part1: " + safe.count())
        return Pair(safe, unsafe)
    }

    private fun partTwo(safeAndUnsafeRecords: Pair<MutableList<List<Int>>, MutableList<List<Int>>>) {
        val unsafeRecords = safeAndUnsafeRecords.second
        val safe = safeAndUnsafeRecords.first

        val iterator = unsafeRecords.iterator()
        while (iterator.hasNext()) {
            val record = iterator.next()
            var addedToSafe = false

            for (index in record.indices) {
                val modifiedRecord = record.filterIndexed { i, _ -> i != index }

                val isDecreasing = modifiedRecord.zipWithNext { a, b ->
                    (a > b) && abs(a - b) in 1..3
                }.all { it }

                if (isDecreasing) {
                    safe.add(record)
                    addedToSafe = true
                    break
                }

                val isIncreasing = modifiedRecord.zipWithNext { a, b ->
                    (a < b) && abs(a - b) in 1..3
                }.all { it }

                if (isIncreasing) {
                    safe.add(record)
                    addedToSafe = true
                    break
                }
            }
            if (addedToSafe) {
                iterator.remove()
            }
        }
        println("Day2 Part2:" + safe.count())
    }
}