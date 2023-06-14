package rtj.exercises.part2oop

object AnonymousClasses extends App {

  abstract class Animal {
    def speak: Unit
  }

  //anonymous class //AnonymousClasses$$anon$1
  // for abstract class, trait
  // need to implement all abstract methods
  val dog:Animal = new Animal {
    override def speak: Unit = println("bark")
  }
  dog.speak

  println(dog.getClass)

  class Person(name: String){
    def sayHi: Unit = println(s"Hi, my name is $name, how can i help?")
    def sayBye: Unit = println("I am logging off, bye bye")
  }

  //anonymous class for concrete type
  // for concrete type any no of methods can be overridden
  // also, new methods can be defined
  val jim = new Person("Jim") {
    override def sayHi: Unit = println("I am Jim, how can i be of service")
    def saySorry: Unit = println(s"I am sorry")
  }
  jim.sayHi
  jim.sayBye
  jim.saySorry

  println(jim.getClass)

}
