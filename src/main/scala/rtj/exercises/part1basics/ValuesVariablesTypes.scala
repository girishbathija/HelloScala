package rtj.exercises.part1basics

object ValuesVariablesTypes extends App {

  val x: Int = 34
  println(x)

  //VALS ARE IMMUTABLE
  // COMPILER can infer types
  val y = 3.4f
  println(y)

  val aString: String = "hello"
  val anotherString = "goodbye"

  val aBoolean: Boolean = false
  val aChar: Char = 'a'
  val aShort: Short = 9837
  val aLong: Long = 1239123982031L
  val aFloat: Float = 3.4f
  val aDouble: Double = 3.432

  var anIntVar = 42

  printf("anIntVar = %d is a var and hence can definitely be changed to %d, val cannot be changed", anIntVar, anIntVar+9)

}
