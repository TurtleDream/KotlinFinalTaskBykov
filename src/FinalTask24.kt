import java.util.ArrayList

// Задание 24: Итоговое задание
// 1. Создайте в программе для авиакомпании еще пару классов, описывающих различные модели самолетов.
//    Сделайте один из них грузовым, для этого создайте соответствующий интерфейс со свойством грузоподъёмность.
// 2. В классе main используйте какую либо коллекцию для хранения информации об имеющихся самолетах у авиакомпании.
//    Добавьте в эту коллекцию несколько самолетов используя имеющиеся классы моделей самолетов.
// 3. Напишите интерфейс для пользователя, где пользователь может в консоли запрашивать информацию о самолетах авиакомпании.
//    Интерфейс должен отображать список команд, а пользователь в свою очередь выбирает номер команды.
fun main(){
    val aircraftList: ArrayList<Aircraft> = arrayListOf(
        Boeing747("Боинг 747", 50000f, 200, 100),
        Ty330("Ту 330", 11000f, 500, 35),
        An140("Ан 140", 2000f, 300, 20, 100))

    loop@ while(true){
        println("""
            |Выберите действие:
            |1. Посмотреть список самолетов.
            |2. Посмотреть информацию о конкретном самолете.
            |3. Выход из программы.
        """.trimMargin())

        when(readLine().toString()){
            "1" -> {
                println("Список самолетов:")

                for(i in 0 until aircraftList.count()){
                    println("№${i + 1}. ${aircraftList[i].getAircraftNumber()}")
                }

                println("\n")
            }
            "2" -> {
                println("Введите номер самолета:")

                val aircraftNumber = readLine().toString()

                if(aircraftList.find { aircraft -> aircraft.getAircraftNumber() == aircraftNumber } != null)
                {
                    aircraftList.find { aircraft -> aircraft.getAircraftNumber() == aircraftNumber }!!.getInfo()
                }
                else println("Не удалось найти самолет - '$aircraftNumber'")

                println("\n")
            }
            "3" -> {
                println("Завершение программы.")
                break@loop
            }
            else -> {
                println("Действия не существует.\n")
            }
        }
    }
}


class Boeing747 (number: String, maxFlightDistance: Float, tankCapacity: Int, override val passengerCapacity: Int)
    : Aircraft(number, maxFlightDistance, tankCapacity), Passenger {
    override fun getInfo(){
        super.getInfo()
        println("Вместимость пассажиров - $passengerCapacity человек")
    }
}

class Ty330 (number: String, maxFlightDistance: Float, tankCapacity: Int, override val payloadCapacity: Int)
    : Aircraft(number, maxFlightDistance, tankCapacity), Freight {
    override fun getInfo(){
        super.getInfo()
        println("Грузоподъемность - $payloadCapacity тонн")
    }
}

class An140 (number: String, maxFlightDistance: Float, tankCapacity: Int, override val payloadCapacity: Int, override val passengerCapacity: Int)
    : Aircraft(number, maxFlightDistance, tankCapacity), Freight, Passenger {
    override fun getInfo(){
        super.getInfo()
        println("Вместимость пассажиров - $passengerCapacity человек")
        println("Грузоподъемность - $payloadCapacity тонн")
    }
}

abstract class Aircraft(protected val number: String, protected val maxFlightDistance: Float, protected val tankCapacity: Int) {
    protected val fuelConsumptionPer100Km: Float
        get() {
            return (100 * tankCapacity) / maxFlightDistance
        }

    fun getAircraftNumber(): String{
        return number
    }

    open fun getInfo(){
        println("""
            |Номер самолета - '$number'
            |Максимальная длительность полета - $maxFlightDistance км
            |Вместимость бака - $tankCapacity л
            |Расход топлива на 100 км - $fuelConsumptionPer100Km л/100км
        """.trimMargin())
    }
}

interface Passenger{
    val passengerCapacity: Int
}

interface Freight{
    val payloadCapacity: Int
}