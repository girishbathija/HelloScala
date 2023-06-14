package rtj.exercises.part2oop

import scala.runtime.Nothing$

trait MyPredicate[-T] {
  def test(predicate: T): Boolean
}

trait MyTransformer[-A, B] {
  implicit def transform(input:A): B
}

abstract class CList[+A] {

  def head: A // we have made this a method, why ?
  def tail: CList[A] // we have made this a method, why ?
  def isEmpty: Boolean
  def add[B >: A](element: B): CList[B]
  protected[part2oop] def printElements: String
  override def toString: String = "[" + printElements + "]"

  def map[B](transformer:MyTransformer[A,B]): CList[B]
  def filter(predicate: MyPredicate[A]): CList[A]

  def ++[B >: A](list: CList[B]): CList[B]
  def flatMap[B](transformer:MyTransformer[A,CList[B]]): CList[B]

  //hof
  def foreach(f: A=>Unit):Unit
  def sort(compare: (A, A)=>Int ): CList[A]
  def zipWith[B,C](list: CList[B], zip: (A,B)=>C): CList[C]
  def fold[B](start: B, operator: (B, A)=>B): B
}

object Empty extends CList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: CList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def  add[B >: Nothing](element: B): CList[B] = new Cons(element, Empty.this)

  override protected[part2oop] def printElements: String = ""

  override def map[B](transformer: MyTransformer[Nothing, B]): CList[B] = Empty

  override def filter(predicate: MyPredicate[Nothing]): CList[Nothing] = Empty

  override def ++[B >: Nothing](list: CList[B]): CList[B] = list

  override def flatMap[B](transformer: MyTransformer[Nothing, CList[B]]): CList[B] = Empty

  //hof
  override def foreach(f: Nothing=>Unit):Unit = ()

  override def sort(compare: (Nothing, Nothing) => Int): CList[Nothing] = Empty

  override def zipWith[B, C](list: CList[B], zip: (Nothing, B) => C): CList[C] =
    if(!list.isEmpty) throw new RuntimeException("Cannot zip an empty list with a non-empty list. They do not have same lengths")//they do not have have s
    else Empty

  override def fold[B](start: B, operator: (B, Nothing) => B): B = start
}

class Cons[+A](h: A, t: CList[A]) extends CList[A] {
  override def head: A = h

  override def tail: CList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): CList[B] = new Cons(element, this) // adding to the head of the list, its like a stack

  override protected[part2oop] def printElements: String =
    if (t.isEmpty) "" + head
    else head + " " + t.printElements

  override def map[B](transformer: MyTransformer[A, B]): CList[B] =
    /*if(t.isEmpty) new Cons[B](transformer.transform(head), Empty)
    else*/ new Cons[B](transformer.transform(head), tail.map(transformer))

  override def filter(predicate: MyPredicate[A]): CList[A] =
    if (predicate.test(head)) new Cons(head, tail.filter(predicate))
    else tail.filter(predicate)

  override def ++[B >: A](list: CList[B]): CList[B] = new Cons(h, t ++ list)

  override def flatMap[B](transformer: MyTransformer[A, CList[B]]): CList[B] =
    transformer.transform(h) ++ t.flatMap(transformer)

  override def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  override def sort(compare: (A, A) => Int): CList[A] = {
    def insert(elem: A, sortedList: CList[A]): CList[A] = {
      if (sortedList.isEmpty) new Cons(elem, Empty)
      else if (compare(elem, sortedList.head) <= 0) new Cons(elem, sortedList)
      else new Cons(sortedList.head,insert(elem, sortedList.tail))
    }
    val sortedList = t.sort(compare)
    insert(h,sortedList)
  }

  override def zipWith[B, C](list: CList[B], zip: (A, B) => C): CList[C] = {
    if(list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else new Cons(zip(h, list.head), t.zipWith(list.tail,  zip))
  }

  override def fold[B](start: B, operator: (B, A) => B): B =
    t.fold(operator(start, h), operator)
}

object InheritanceWithGenericsExercise extends App {

  val intList = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(intList.tail.head)
  println(intList.add(4).head)
  println(intList.isEmpty)
  println(intList)

  val stringList = new Cons("Hello", new Cons("Scala!", new Cons("This is Girish", Empty)))
  println(stringList)

  //multiply by 2
  /*println(intList.map(new MyTransformer[Int, Int] {
    override def transform(input: Int): Int = input * 2
  }))*/
  println(intList.map(
    (input: Int) => input * 2
  ))


  //even nos
  /*println(intList.filter(new MyPredicate[Int] {
    override def test(predicate: Int): Boolean = predicate%2==0
  }))*/
  println(intList.filter(
    (predicate: Int) => predicate % 2 == 0
  ))

  val anotherIntList = new Cons(7, new Cons(8, new Cons(9, Empty)));
  println("Another list - "+anotherIntList)

  println("int list + another list"+(intList ++ anotherIntList))

  /*println("flatmap i => [i, i+1]"+intList.flatMap(new MyTransformer[Int, CList[Int]] {
    override def transform(input: Int): CList[Int] =
      new Cons[Int](input, new Cons[Int](input+1, Empty))
  }))*/

  //flatmap lambda not working --??? same as tutorial
  /*intList.flatMap(
    input => new Cons(input, Empty)
  )*/

  println("printing the list using foreach")
  println(intList.foreach(println(_)))

  println("sorting the list in descending order")
  println(intList.sort((a,b)=>b-a))

  println("zipping with list of strings")
  println(intList.zipWith(stringList, (i: Int,s: String)=>s"$i-$s"))

  println("folding with list of int")
  println(intList.fold(0, (start:Int, elem:Int)=>start+elem))
  println(intList.fold(5, (start:Int, elem:Int)=>start+elem))
  println(anotherIntList.fold(10, (start:Int, elem:Int)=>start+elem))

  val combination = for {
    i <- intList
    s <- stringList
  } println(s + i)
}