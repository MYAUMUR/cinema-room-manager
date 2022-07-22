package cinema

const val NORMAL_PRICE = 10
const val PRICE_FRONT_HALF = 10
const val PRICE_BACK_HALF = 8

class Cinema(private var rows: Int, private var seats: Int) {
    private val cinema = MutableList(rows) { MutableList(seats) { 'S' } }

    fun printCinema() {
        print("\nCinema:\n ")
        for (i in 1..seats) print(" $i")
        for (i in 1..rows) print("\n$i ${cinema[i - 1].joinToString(" ")}")
        println()
    }
    fun useSeat() {
        println("\nEnter a row number:")
        val rowNumber = readln().toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readln().toInt()
        cinema[rowNumber - 1][seatNumber - 1] = 'B'
        calculatePrice(rowNumber)
    }
    private fun calculatePrice(rowNumber: Int) {
        val totalSeats = rows * seats
        val ticketPrice = when {
            totalSeats < 60 -> NORMAL_PRICE
            rowNumber > rows / 2 -> PRICE_BACK_HALF
            else -> PRICE_FRONT_HALF
        }
        println("\nTicket price: $$ticketPrice")
    }
}
fun settingRoom() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    val cinema = Cinema(rows, seats)
    cinema.printCinema()
    cinema.useSeat()
    cinema.printCinema()
}

fun main() {
    settingRoom()
}