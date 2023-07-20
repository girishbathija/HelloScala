package rtj.beginner.part1basics;object Functions extends App {

        def fibonacci(n: Int): Int = {
                if (n <= 2) 1
                        else {
                        println("Req "+(n-1)+" + "+(n - 2))
                        val result = fibonacci(n - 1) + fibonacci(n - 2)
                        println("Computed for "+n+" result ="+result)
                        result
                        }
                }
        println("hello")
        println(fibonacci(50))

        }
