package cinema

const val NORMAL_PRICE = 10
const val PRICE_FRONT_HALF = 10
const val PRICE_BACK_HALF = 8

val cinema = Cinema()
fun main() {
    chooseAction()
}

fun chooseAction() {
    println()
    println(
        """
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    """.trimIndent()
    )
    when (readln().toInt()) {
        1 -> cinema.printCinema()
        2 -> cinema.buyTicket()
        3 -> cinema.printStatistics()
    }
}

class Cinema {
    private var rows: Int
    private var seats: Int

    init {
        println("Enter the number of rows:")
        rows = readln().toInt()
        println("Enter the number of seats in each row:")
        seats = readln().toInt()
    }

    private val totalSeats = rows * seats
    private var purchasedTickets = 0
    private var currentIncome = 0
    private val totalIncome = calculateTotalIncome()

    private val cinema = MutableList(rows) { MutableList(seats) { 'S' } }
    fun printCinema() {
        print("\nCinema:\n ")
        for (i in 1..seats) print(" $i")
        for (i in 1..rows) print("\n$i ${cinema[i - 1].joinToString(" ")}")
        println()
        main()
    }

    fun buyTicket() {
        println("\nEnter a row number:")
        val rowNumber = readln().toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readln().toInt()

        try {
            when {
                (rowNumber !in 1..rows || seatNumber !in 1..seats) -> throw IndexOutOfBoundsException()
                cinema[rowNumber - 1][seatNumber - 1] == 'B' -> throw Exception()
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
            buyTicket()
        } catch (e: Exception) {
            println("That ticket has already been purchased!")
            buyTicket()
        }

        cinema[rowNumber - 1][seatNumber - 1] = 'B'
        purchasedTickets++
        calculatePrice(rowNumber)
    }

    private fun calculatePrice(rowNumber: Int) {
        val ticketPrice = when {
            totalSeats < 60 -> {
                currentIncome += NORMAL_PRICE
                NORMAL_PRICE
            }
            rowNumber > rows / 2 -> {
                currentIncome += PRICE_BACK_HALF
                PRICE_BACK_HALF
            }
            else -> {
                currentIncome += PRICE_FRONT_HALF
                PRICE_FRONT_HALF
            }
        }
        println("\nTicket price: $$ticketPrice")
        main()
    }

    fun printStatistics() {
        println(
            """
            Number of purchased tickets: $purchasedTickets
            Percentage: ${calculatePercentage()}%
            Current income: $$currentIncome
            Total income: $$totalIncome
        """.trimIndent()
        )
        chooseAction()
    }

    private fun calculateTotalIncome(): Int {
        val halfSeats = rows / 2 * seats
        val halfSeatsSecond = (rows - rows / 2) * seats
        return if (totalSeats <= 60) {
            rows * seats * 10
        } else {
            (halfSeats * PRICE_FRONT_HALF) + (halfSeatsSecond * PRICE_BACK_HALF)
        }
    }

    private fun calculatePercentage(): String {
        val percentage = purchasedTickets.toDouble() / totalSeats.toDouble() * 100.0
        return "%.2f".format(percentage)
    }
}