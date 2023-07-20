package rtj.beginner.part1basics;object Expressions extends App {

  //Everything is a expression in scala; there are no statements; statement return type -> Unit
  // Unit is synonymous to void in java

  val x = 1 + 2 //Expression
  println(x)

  println(1+2*3)

  // all operators are overloaded methods in classes
  // thus -
  // 1. all operators can be accessed via . notations for eg - 2.+(3)
  // 2. same op behave differently for different functions since they are overloaded. for eg - & |  - function bitwise on ints, logically on logical expressions
  println(2 | 4)

  println(2 < 3 & 3 < 4)

  // for single parameter method calls, () can replaced with {}
  println {
    2.&{3}
  }

  println(3 << 2)

  println(12 >> 2)
  // >>> - what does this operator do ?

  val aConditionalExp = 2 == 3
  //val aConditionalExp2 = 2 = 3 // not valid
  println("2 == 3 equates to "+aConditionalExp)

  /* illegal, val cannot be assigned again
  val plusEqTo = 3
  plusEqTo += 2
  */
  var plusEqTo = 3
  plusEqTo += 2
  println(plusEqTo)

  println(if (plusEqTo >= 5) "if" else "else")

  var i = 0
  while(i<10){
    print(i)
    i += 1
  }
  println()

  // NEVER USE while loops in SCALA AGAIN ->
  // coz imperative style of programming.. we want to write functional / declarative code

  val aCodeBlock = {
    val a = 1
    var b = a + 2
    "The last line/expression returns always"

  }
  //println(b) // not accessible outside code block
  println(aCodeBlock)

  //Exercise

  //Q1 difference between "hello world" and println("hello world")
  val exp1 = "Hello World"
  val exp2 = println(exp1)
  println(exp2)
  //ans - one exp is a string the other exp is a Unit

  //Q2
  val someValue = {
    2<3
  }
  println(someValue)
  //ans - bool true

  //Q3
  val someOtherValue = {
    if (someValue) 239 else 986
    42
  }
  println(someOtherValue)
  //ans - 42



}
