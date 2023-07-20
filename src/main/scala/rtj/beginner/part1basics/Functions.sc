def aFunction(a: String, b: Int) = {
  a + " " + b
}

println(aFunction("hello",2))

def aParameterlessFunction() = {
  "I am parameterless"
}
println(aParameterlessFunction)

def aRepeatedFunc(label: String, counter: Int): String = {
  if (counter == 1) {
    label
  }
  else {
    label + aRepeatedFunc(label, counter - 1)
  }
}

println(aRepeatedFunc("hello",3))

//Use recursive functions instead of while loops in scala - Daniel Ciocîrlan

/*
* Exercises
*
* */

//1. a greeting function
def greetings(name: String, age: Int):String = "Hi, my name is "+name+" and i am "+age+" years old"
println(greetings("spruha",3))

//2. Factorial function
def factorial(n: Int):BigInt={
  if(n == 1) 1
  else n * factorial(n-1)
}
println(factorial(3))
println(factorial(10))

// 3 fibonacci series
def fibonacci(n:Int):BigInt = {
  if(n==1 | n==2) 1
  else fibonacci(n-1) + fibonacci(n-2)
}
println(fibonacci(5))
println(fibonacci(2))
println(fibonacci(10))


def isPrime(n:Int, itr:Int):Boolean = {
  if(itr==1) return true
  if(n%itr==0) false
  else isPrime(n, itr-1)
}

println(isPrime(5, 2))
println(isPrime(50, 25))
println(isPrime(7, 3))
println(isPrime(987654321, 987654321/2))