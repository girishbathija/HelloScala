val str:String = "Hello, I am learning Scala"
//reused from java impl
println(str.charAt(2))
println(str.substring(7,11))
println(str.split(" ").toList)
println(str.startsWith("Hello"))
println(str.replace(" ","-"))
println(str.toLowerCase)
println(str.length)
//Scala specific features
val aNumberString = "2"
val aNumber = aNumberString.toInt
println('a'+ aNumberString + 'z')
println('a'+: aNumberString :+ 'z')
println(str.reverse)
println(str.take(2))

//S-interpolators
val name = "Madhav"
val age = 5
val greeting = s"Hello, my name is $name and I am $age years old"
val anotherGreeting = s"Hello, my name is $name and I will be turning ${age + 1} years old"
println(anotherGreeting)

//F-interpolaters
val speed = 0.6f
val myth = f"$name can eat $speed%2.2f burgers per minute"
println(myth)

val pi = 3.1423789900
println(f"The value of pi is $pi%1.2f")

//raw-interpolators
println(raw"this is a \n newline")
val escaped = "this is a \n newline"
println(raw"$escaped")