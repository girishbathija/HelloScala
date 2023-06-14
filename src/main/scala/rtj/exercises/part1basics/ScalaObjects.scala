package rtj.lectures.part1basics

case class CustomInt(x:Int) {
  def test = println("I belong to customInt class")
}
object CustomInt

case object aSingleton {
  val a:Int = 5
}
object ScalaObjects {

  def main(args:Array[String]) = {
    val x = CustomInt
    val y = new CustomInt(5)
    //x.test() //value test is not a member of object rtj.lectures.part1basics.CustomInt
    println(x)
    println(y.toString)
    println(x.getClass == y.getClass)

    val z = aSingleton
    println(z)
  }

}

