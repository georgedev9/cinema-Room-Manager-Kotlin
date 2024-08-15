package cinema

const val FRONT_PRICE = 10
const val BACK_PRICE = 8
const val TICKET_PRICE = 10

val screenRoomList = mutableListOf<MutableList<Char>>()
var rows = 0
var seats = 0
var purchasedTickets = 0
var currentIncome = 0


fun main() {

    generateScreenRoom()
    showMenu()
}


fun showMenu() {

    while (true){

        println("\n1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")

        val option = readln()

        when (option) {
            "1" -> showScreenRoom()
            "2" -> buyTicket()
            "3" -> showStatistics()
            "0" -> break
            else -> println("\nInvalid Option")
        }
    }

}


fun showScreenRoom(){

    println("\nCinema:")

    for (seat in screenRoomList) {

        println(seat.joinToString(" "))
    }

}


fun buyTicket() {

    var row = 0
    var seat = 0

    while (true) {

        println("\nEnter a row number:")
        row = readln().toInt()
        println("Enter a seat number in that row:")
        seat = readln().toInt()

        try {

            val seatPosition = screenRoomList[row][seat]

            if (seatPosition == 'S') {
                screenRoomList[row][seat] = 'B'
                break
            } else {
                println("\nThat ticket has already been purchased!")
            }

        } catch (e: IndexOutOfBoundsException){
            println("\nWrong input!")

        } catch (e: Exception){
            println(e.message)
        }

    }

    val ticketPrice = calcTicketPrice(row)
    purchasedTickets++
    currentIncome += ticketPrice

    println("\nTicket price: $$ticketPrice")

}



fun calcTicketPrice(row: Int): Int{

    val totalSeats = rows * seats

    if (totalSeats > 60 && row <= rows / 2)
        return FRONT_PRICE
    else if (totalSeats > 60 && row > rows / 2)
        return BACK_PRICE

    return TICKET_PRICE

}



fun showStatistics(){

    val totalSeats = rows * seats
    var firstHalfSeats = 0
    var secondHalfSeats = 0
    var totalIncome = 0

    if (totalSeats > 60) {

        firstHalfSeats = rows / 2
        secondHalfSeats = firstHalfSeats + rows % 2

        totalIncome = (firstHalfSeats * seats * FRONT_PRICE) +
                (secondHalfSeats * seats * BACK_PRICE)

    } else {

        totalIncome = totalSeats * TICKET_PRICE
    }

    val percentage = (purchasedTickets.toDouble() / totalSeats.toDouble()) * 100

    println("\nNumber of purchased tickets: $purchasedTickets")
    println("Percentage: ${"%.2f".format(percentage)}%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")

}



fun generateScreenRoom(){

    println("Enter the number of rows: ")
    rows = readln().toInt()
    println("Enter the number of seats in each row: ")
    seats = readln().toInt()

    //Generate screen room
    for (row in 0..rows) {

        screenRoomList.add(mutableListOf<Char>())

        for (seat in 0..seats) {

            screenRoomList[row].add('S')

            if (seat == 0){
                screenRoomList[row][seat] = row.digitToChar()
            }

        }

    }

    //Update first column
    for (seat in screenRoomList[0].indices){
        screenRoomList[0][seat] = seat.digitToChar()
    }

    //Update zero seat of the first column
    screenRoomList[0][0] = ' '

    showScreenRoom()

}