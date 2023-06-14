package rtj.exercises.part2oop

import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name:String, val favMovie:String, val age:Int){
    //1
    def +(alias:String) = new Person(s"$name ($alias)",favMovie, age)

    //2
    def unary_+() = new Person(name, favMovie, age+1)

    //3
    def learns(subject:String) = s"$name learns $subject"
    def learnsScala() = learns("Scala")

    //4
    def apply(count:Int) = s"$name watched $favMovie $count times"
  }

  val person = new Person("Mary", "Inception", 29)
  //1  infix
  val p1 = person + "had a little lamb"
  println(p1.name)
  //2 prefix
  val p2 = +person
  println(p2.age)
  //3 postfix
  println(person learnsScala )
  //4
  println(person(3))

  //INFIX notation - for methods with only 1 param

  //PREFIX notation - for unary_ methods with no params, only allowed for - +,-,!,~

  //POST FIX notation - for methods with no params, rarely used - causes confusion in reading

  //apply method is spl - allow you to call your objects like they were functions



}
