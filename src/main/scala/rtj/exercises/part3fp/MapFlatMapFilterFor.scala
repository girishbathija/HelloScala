package rtj.exercises.part3fp

object MapFlatMapFilterFor extends App {

  val list = List(1,2,3)
  println(list)
  println(list.head)
  println(list.tail)

  //map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  //filter
  println(list.filter(_ % 2 ==0))

  //flatMap
  println(list.flatMap((x:Int)=>List(x, x + 1)))

  //print all combinations between two lists
  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  // List("a1", "a2"..."d4")

  val combinations = numbers.flatMap(n=> chars.map(c => ""+c+n))
  println(combinations)

  val colors = List("white", "black")
  val combinations1 = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(co=> ""+c+n+"-"+co)))
  println(combinations1)

  //foreach
  list.foreach(println)

  //for-comprehensions
  val forCombinations = for {
    n <- numbers if n % 2 != 0
    c <- chars
    color <- colors
  } yield ""+c+n+"-"+color
  println(forCombinations)

  //syntax overload
  val combo = numbers.filter {
    _ % 2 == 0
  }.flatMap { n =>
    chars.map(c => "" + c + n)
  }

  println(combo)

  //exercise

  trait Maybe[+T] {
    //def value: T

    def map[S](f: T => S): Maybe[S]
    def flatMap[S](f: T => Maybe[S]): Maybe[S]
    def filter(f: T => Boolean): Maybe[T]
  }

  object MaybeNot extends Maybe[Nothing] {
    //override def value: Nothing = throw new NoSuchElementException()

    override def map[S](f: Nothing => S): Maybe[S] = MaybeNot

    override def flatMap[S](f: Nothing => Maybe[S]): Maybe[S] = MaybeNot

    override def filter(f: Nothing => Boolean): Maybe[Nothing] = MaybeNot
  }

  class Just[+T](val value: T) extends Maybe[T] {
    override def map[S](f: T => S): Maybe[S] = new Just[S](f(value))

    override def flatMap[S](f: T => Maybe[S]): Maybe[S] = f(value)

    override def filter(f: T => Boolean): Maybe[T] = if (f(value)) this else MaybeNot

  }

}
