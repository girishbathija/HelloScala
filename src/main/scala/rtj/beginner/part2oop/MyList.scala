package rtj.beginner.part2oop

import scala.language.postfixOps

//List of integers - singly linked list
abstract class MyList {

  def isEmpty: Boolean
  def add(element:Int): MyList

}

class SinglyLinkedList extends MyList {

  class Node(val value:Int, var next:Node)

  var head: Node = null
  //var tail: Node

  override def isEmpty(): Boolean = head == null

  private def replace(curr: Node, newNode: Node): Node = new Node(curr.value, newNode)

  private def updateLast(curr:Node, newNode: Node): Unit = {
    if (curr == null) head = newNode
    else if (curr.next == null) {
      head = replace(curr, newNode)
    } else if (curr.next != null && curr.next.next == null)
      curr.next = replace(curr.next, newNode)
    else
      updateLast(curr.next, newNode)
  }

  override def add(element: Int): MyList = {
    val newNode = new Node(element, null)
    updateLast(head, newNode)
    return this
  }

  def getStringRep(curr: Node, str: String): String = {
    if (curr == null) str
    else getStringRep(curr.next, str+curr.value)
  }

  override def toString: String = getStringRep(head, "")

}

object MyListApp extends App {

  val list:MyList = new SinglyLinkedList
  list.add(1).add(2).add(3)
  println(list)
  println(list isEmpty )
}
