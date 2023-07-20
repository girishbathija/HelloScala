package rtj.beginner.part2oop

abstract class RtjList {

  def head: Int // we have made this a method, why ?
  def tail: RtjList // we have made this a method, why ?
  def isEmpty: Boolean
  def add(element: Int): RtjList
  protected[part2oop] def printElements: String
  override def toString: String = "[" + printElements + "]"
  def map:RtjList
}

object EmptyList extends RtjList {
  override def head: Int = throw new NoSuchElementException

  override def tail: RtjList = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add(element: Int): RtjList = new ConsList(element, EmptyList.this)

  override protected[part2oop] def printElements: String = ""

  override def map: RtjList = ???
}

class ConsList(h: Int, t: RtjList) extends RtjList {
  override def head: Int = h

  override def tail: RtjList = t

  override def isEmpty: Boolean = false

  override def add(element: Int): RtjList = new ConsList(element, this) // adding to the head of the list, its like a stack

  override protected[part2oop] def printElements: String =
    if (t.isEmpty) "" + head
    else head + " " + t.printElements

  def map: RtjList =
    if (t.isEmpty) new ConsList(head * 2, EmptyList)
    else new ConsList(head * 2, t.map)

  def filter: RtjList =
    if(head % 2 ==0) new ConsList(head, if (tail.isEmpty) EmptyList else filter)
    else filter

}

object InheritanceExercise extends App {

  val list = new ConsList(1, new ConsList(2, new ConsList(3, EmptyList)))
  println(list.tail.head)
  println(list.add(4).head)
  println(list.isEmpty)

  val listX2 = list.map
  println(listX2)

  val oddList = list.filter
  println(oddList)
  
}