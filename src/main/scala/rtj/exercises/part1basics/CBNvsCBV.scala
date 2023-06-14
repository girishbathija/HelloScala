package rtj.exercises.part1basics;

object CBNvsCBV extends App {

  def calledByValue(x: Long): Unit = {
    println(x)
    println(x)
  }

  def calledByName(x: => Long): Unit = {
    println(x)
    println(x)
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime()) //prints 2 diff values

  //CBN - stores the expression instead of just the value
  //and thus the evaluation of the expression happens lazy-ly

  def infinte(): Int = 1 + infinte()

  def printX(x: Int, y: => Int): Unit = println(x)

  //printX(infinte(), 34) //stackoverflow
  printX(34, infinte()) // infinite does not get called

  //parameter passing for instances
  class MyInt(var x: Int)

  val x = new MyInt(5)
  x.x = 10
  println(x.x)

  def mutate(myInt: MyInt) = myInt.x = 15

  def mutateByName(myInt: => MyInt) = myInt.x = 20

  mutate(x)
  println(x.x)

  mutateByName(x)
  println(x.x)


}
