import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.File
import java.lang.Exception

object AocClient {

    private val sessionId: String = runCatching {
        File("cookie.txt").readText().trim()
    }.getOrElse {
        throw RuntimeException("Failed to read cookie: ${it.message}", it)
    }

    suspend fun getAocDayInput(day: Int, returnType: InputReturnType = InputReturnType.ARRAY): Any {
        val file = File("inputs/$day.txt")

        if (file.exists()) {
            return when (returnType) {
                InputReturnType.STRING -> getDayInputAsString(day)
                InputReturnType.ARRAY -> getDayInputAsArray(day)
            }
        }

        runCatching {
            HttpClient(CIO).use { client ->
                client.get("https://adventofcode.com/2024/day/$day/input") {
                    header("Cookie", "session=$sessionId")
                }.bodyAsText().also { input ->
                    file.parentFile.mkdirs()
                    file.writeText(input)
                }
            }
        }.onFailure {
            throw RuntimeException("Failed to fetch input for day $day: ${it.message}", it)
        }

        return when (returnType) {
            InputReturnType.STRING -> getDayInputAsString(day)
            InputReturnType.ARRAY -> getDayInputAsArray(day)
        }
    }

    private fun getDayInputAsString(day: Int): String {
        return runCatching {
            File("inputs/$day.txt").readText().trim()
        }.getOrElse {
            throw RuntimeException("Failed to parse input for day $day: ${it.message}", it)
        }
    }

    private fun getDayInputAsArray(day: Int): Array<String> {
        return runCatching {
            File("inputs/$day.txt").readText().trim().split("\n").toTypedArray()
        }.getOrElse {
            throw RuntimeException("Failed to parse input for day $day: ${it.message}", it)
        }
    }

    // Enum to specify the return type
    enum class InputReturnType {
        STRING,
        ARRAY
    }
}
