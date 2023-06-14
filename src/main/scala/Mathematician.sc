val bits = "10011"
bits(2) ^ bits(1)

"12345".map(_.asDigit).fold(0)( _ + _)
"12345".map(_.asDigit).sum

900000L * 99999L
Long.MaxValue
println(Int.MaxValue)

def square(x: Int): Int = x * x

square(2)
square(2) * square(3)

def aFunction(a: String, b: Int) = {
  a + " " + b
}

println(aFunction("hello",2))

def aParameterlessFunction() = {
  "I am parameterless"
}
println(aParameterlessFunction)

//can we insert comments?
print("test")

/*

val y = List(1,2,3,4,5) match {
  case List(x, List(2, List(4, _))) => x
  case Nil => 42
  case List(x, List(y, List(3, List(4, _)))) => x + y
  case List(h, t) => h + 3
  case _ => 101
}
*/

497.41f * 0.01f