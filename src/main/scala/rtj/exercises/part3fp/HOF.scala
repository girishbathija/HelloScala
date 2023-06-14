package rtj.exercises.part3fp

object HOF extends App {

  def curriedFormatter(c: String)(x: Double):String = c.format(x)

  val standardFormatter: Double => String = curriedFormatter("%4.2f")

  /*def toCurry(f: (Int, Int)=>Int): Int => Int => Int =
    x => y=> f(x,y)*/

  def toCurry[A,B,C](f: (A,B)=>C): A=>B=>C =
    a=>b=>f(a,b)

  /*def fromCurry(f: Int => Int => Int): (Int, Int)=> Int =
    (x,y) => f(x)(y)*/

  def fromCurry[A,B,C](f: A => B => C): (A, B) => C =
    (x, y) => f(x)(y)
  //FunctionX lib
  /*def compose(f: Int => Int, g: Int => Int): Int => Int =
    x => f(g(x))*/

  def compose[A,B,C](f: A => B, g: C => A): C => B =
    x => f(g(x))

  /*def andThen(f: Int => Int, g: Int => Int): Int => Int =
    x => g(f(x))*/

  def andThen[A,B,C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  //Tests
  val supperAdder = toCurry((a:Int, b:Int)=>a+b)
  val add4 = supperAdder(4)
  println(add4(17))

  val simpleAdder = fromCurry(supperAdder)
  println(simpleAdder(4,17))
  //println(simpleAdder(4)(17)) //curry does not work out of the box for a 2 paramter function - be it normal or literal/lambda

  val add2 = (x: Int)=> x + 2
  val times3 = (x: Int)=> x * 3

  val composed1 = compose(add2, times3)
  val composed2 = compose(times3, add2)

  println(composed1(4))
  println(composed2(4))

  val ordered = andThen(add2, times3)
  println(ordered(4))

}
