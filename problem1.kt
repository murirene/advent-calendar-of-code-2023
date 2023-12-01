import java.io.File

fun main(args: Array<String>) {
    if (args.size == 0){
        println("Provide an input file")
        return
    } 
    
    val file = File(args[0])
    val bufferReader = file.bufferedReader()
    val text:List<String> = bufferReader.readLines()
    var sum:Int = 0
    val Numbers:Set<String> = setOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val NumberMap:Map<String, Char> = mapOf("one" to '1', "two" to '2', "three" to '3', "four" to '4', "five" to '5', "six" to '6', "seven" to '7', "eight" to '8', "nine" to '9') 
    for(line in text){
        var first:Char = '\u0000' 
        var last:Char = '\u0000' 

        for((index:Int, ch:Char) in line.withIndex()) {
            var isNumber:Boolean = false
            var NumberKey = ""
            if(ch.isLetter()) {
                for(n in Numbers) {
                    if(index+n.length > line.length){
                        continue
                    }
                    val subStr = line.substring(index, (index+n.length))
                    if (subStr == n) {
                        isNumber = true
                        NumberKey = n
                    } 
                }
            }

            // first digit set
            if(ch.isDigit() && first == '\u0000'){
                first = ch
            } else if(ch.isDigit()) {
                last = ch
            } else if(isNumber && first == '\u0000'){
                first = NumberMap.getValue(NumberKey)
            } else if (isNumber){
                last = NumberMap.getValue(NumberKey)
           }
        }
        
        var str:String = "$first$last"
        // single digit use case
        if (last == '\u0000') {
            str = "$first$first"
        }
        sum += str.toInt()
    }
    println(sum)
}
