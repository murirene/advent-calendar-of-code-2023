import java.io.File

class Hand(var blue:Int,var green:Int, var red:Int) {
    override fun toString():String {
        return "blue($blue), green($green), red($red)"
    }
}

data class Game (val id: Int, val hands:ArrayList<Hand>) 

fun parseGame(line:String): Game {
    val tokens:List<String> = line.split(":")
    var id:Int = tokens.get(0).split("Game ").get(1).toInt()
    var hands = arrayListOf<Hand>()
    var handsStr = tokens.get(1).split(";")
    
    for (h in handsStr) {
        val handMap = LinkedHashMap<String, Int>()
        handMap["green"] = 0
        handMap["red"] = 0
        handMap["blue"] = 0
       
        val cubesStr = h.split(',')
         
        for(c in cubesStr) {
           val (countStr, color) = c.trim().split(' ')
           val count:Int = countStr.trim().toInt()
           
           if(!handMap.containsKey(color)) {
               throw Exception("$color not a key")
           }
           handMap[color] = count
        }

        hands.add(Hand(handMap["blue"] ?: 0, handMap["green"] ?: 0, handMap["red"] ?: 0)) 
    }

    return Game(id, hands)
}  

fun getMaxHands(hands:ArrayList<Hand>):Hand {
    var maxHand = Hand(0, 0, 0)
    for(h in hands){
        if(h.red > maxHand.red) {
            maxHand.red = h.red
        }
        if(h.blue > maxHand.blue) {
            maxHand.blue = h.blue
        }
        if(h.green > maxHand.green) {
            maxHand.green = h.green
        }
    }

    return maxHand
}

fun main(args: Array<String>) {
    if (args.size == 0){
        println("Provide an input file")
        return
    } 

    val load = Hand(14, 13, 12)
    val file = File(args[0])
    val bufferReader = file.bufferedReader()
    val text:List<String> = bufferReader.readLines()

    var maxHands = arrayListOf<Hand>()
    var answer1:Int = 0
    var answer2:Int = 0

    for(line in text){ 
        val game:Game = parseGame(line)

        // part 1
        var possible:Boolean = true
        for(hand in game.hands) {
           if(load.red < hand.red || load.blue < hand.blue || load.green < hand.green) {
               possible = false
               break
           }
        }
      
        if (possible) {
           answer1 += game.id
        }
        
        // part 2
        maxHands.add(getMaxHands(game.hands))              
    }
    
    // part 2
    for(m in maxHands) {
        answer2 += m.red * m.blue * m.green
    }

    println("first part($answer1)  second part($answer2)")
}
