package rtj.beginner.part3fp

object WhatAFunction extends App {

  //1. a function which takes 2 strings and concatenates them
  val concat = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1+v2
  }

  println(concat("hello","world"))

  //2. functions for lists
  // higher order functions

  //3. define a function which takes an int and returns another function which takes an int and returns an int
  // - whats the type of this function
  // - how to do it

  val intToFuncIntSq = new Function1[Int, Function1[Int, Int]] {
    override def apply(v1: Int): Int => Int = {
      new Function[Int, Int] {
        override def apply(v1: Int): Int = v1*v1
      }
    }
    }
  val input = 5
  //val funcIntSq = intToFuncIntSq(input)(input)
  val inputSquare = intToFuncIntSq(input)(input)
  println(s"Square of $input is $inputSquare")

  //these are curried functions

  //Anonymous function

  val specialFunc: Int => Int =>Int = (v1:Int)=>(v2:Int)=>v1+v2
  println(specialFunc(2)(3))

  //self exercise
  def multiplyBy2(no:Int):Int = no*2

  def delegate[A,B](f: A=>B, input: A): B = f(input)

  println(delegate(multiplyBy2, 2))
  println(delegate((x: Int)=>x*3,2))

  def multiply(n1: Int, n2: Int): Int = n1*n2
  println(multiply(2,3))
  //val mulBy2: Int=>Int = multiply(2)
  //println(multiply(2)(3))
  //unable to curry normal method

  def adder(n1:Int)(n2:Int)(n3:Int):Int = n1 + n2 + n3
  val addThird: Int => Int = adder(1)(2)
  println(addThird(4))
}
