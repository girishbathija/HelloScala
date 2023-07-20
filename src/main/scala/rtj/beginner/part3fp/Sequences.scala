package rtj.beginner.part3fp

import scala.util.Random

object Sequences extends App {

  //Seq
  val aSequence = Seq(1,2,3,4)
  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2)) //get method
  println(aSequence ++ Seq(5,6,7))
  println(Seq(3,2,1).sorted)
  //aSequence.count()

  //Ranges
  val aRange: Seq[Int] = 1 to 10
  aRange.foreach(println)
  println(aRange.map(_+1))

  (1 to 10).foreach(x=>println("hello"))

  //Lists
  val aList = List(1,2,3,4)
  val prepend1 = 42 :: aList
  println(prepend1)

  println(42 +: aList :+ 24)

  val apples5 = List.fill(5)("apple")
  println(apples5)
  println(aList.mkString("-|-"))

  //Arrays
  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim(3)
  println(threeElements)
  threeElements.foreach(println)

  //mutation
  numbers(2) = 33 //syntax sugar for numbers.update(2,33)
  numbers.update(3,44)
  println(numbers.mkString(" "))

  //arrays <> seq
  val numberSeq: Seq[Int] = numbers //implicit conversion
  println(numberSeq)

  //vectors
  val aVectorList = Vector(1,2,3,4,5,6)
  println(aVectorList)

  //vector vs lists
  val maxRuns = 10000
  val maxCapacity = 1000000
  def getWriteTime(collection: Seq[Int]) = {
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRuns
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  //keeps tail reference
  //updating an element in the middle takes long
  println(getWriteTime(numbersList))

  //depth of the tree is small
  // needs to replace an entire 32-element chunk
  // near constant time, very slow growing logarithmic time
  println(getWriteTime(numbersVector))



}
