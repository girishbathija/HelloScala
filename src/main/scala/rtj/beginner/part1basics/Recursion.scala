package rtj.beginner.part1basics;object Recursion extends App {

        def factorial(n: Int): BigInt = {
                if (n == 1) 1
                        else {
                        println("Require "+ (n - 1) +"! for "+n+"!")
                        val result = n * factorial(n - 1)
                        println("Computed "+n+"! = "+result)
                        result
                        }
                }
        //println(factorial(5000))

        def tailFactorial(n:Int):BigInt = {
                def tFactHelper(n:Int, accumulator:BigInt):BigInt = {
                        if(n == 1) accumulator
                                else tFactHelper(n-1, n*accumulator)
                        }
                tFactHelper(n, 1)
                }
        println(tailFactorial(10))


        //EXERCISES

        //1. concatenate a string n times
                def printNTimes(str:String, n:Int):String = {
                def helper(str:String, n:Int, result:String):String = {
                        if (n == 0 ) result
                                else helper(str, n-1, result+str)
                        }
                helper(str, n, "")
                }
        println(printNTimes("cat", 5))

        //2 prime number
                def isPrime(n: Int, itr: Int): Boolean = {
                if (itr == 1) return true
                if (n % itr == 0) false
                        else isPrime(n, itr - 1)
                }

        // fibonacci using tail recursion

        /*
          def fibonacci(n: Int): BigInt = {
            if (n <= 2) 1
            else fibonacci(n - 1) + fibonacci(n - 2)
          }
          */

        def tFibonacci(n: Int): BigInt = {
                def helper(n:Int, itr:Int,  n_1:BigInt, n_2:BigInt): BigInt = {
                        if (n <= 2) 1
                                else if(itr == n) n_1 + n_2
                                else helper(n, itr+1,n_1+n_2, n_1)
                        }
                helper(n, 2, 1, 0)
                }
        println(tFibonacci(100))
        }
