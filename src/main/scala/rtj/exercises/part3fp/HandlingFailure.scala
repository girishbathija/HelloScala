package rtj.exercises.part3fp

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure extends App {

  //create success and failure
  val aSuccess = Success("A successful result")
  val aFailure = Failure(new RuntimeException("SUPER FAILURE"))

  println(aSuccess)
  println(aFailure)

  def unsafeMethod: String = throw new RuntimeException("NO STRING FOR YOU BUSTER")

  val potentialFailure = Try(unsafeMethod)
  println(potentialFailure)

  //syntax sugar
  val anotherPotentialFailure = Try {
    throw new RuntimeException("ANOTHER EXCEPTION THROWN")
  }
  println(anotherPotentialFailure)

  //utilities
  println(potentialFailure.isSuccess)

  //orElse
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod) orElse Try(backupMethod)
  println(fallbackTry)

  //right way to design the API - if u r designing it
  def betterUnsafeMethod: Try[String] = Failure(new RuntimeException("Better way to deal with runtime exception"))
  def betterBackupMethod: Try[String] = Success("A better valid result")
  val betterFallback = betterUnsafeMethod orElse betterBackupMethod
  println(betterFallback)

  //map, flatMap, filter
  println(aSuccess.map(_ * 2))
  aSuccess.map(_ * 2).foreach(println)
//  aFailure.map(_ * 2).foreach(println)

  println(aSuccess.flatMap(x=>Success(x * 3)))

  println(aSuccess.filter(_.length > 100)) // a filter can convert a success to failure.
  aSuccess.filter(_.length > 100).foreach(println) // does not print anything

  /*
  * Exercise
  * */
  val hostname = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)


  class Connection {
    val random = new Random(System.nanoTime())
    def get(url: String): String = {
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted") //
    }
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }
  }

  // if you get the html page from the connection , print it to the console ie call renderHTML
  Try(HttpService.getConnection(hostname, port)).flatMap(c=>Try(c.get("/google.com"))).foreach(renderHTML)

  for {
    //i<- 1 to 10
    connection <- Try(HttpService.getConnection(hostname, port))
    page <- Try(connection.get("/google.com"))
  } yield renderHTML(page)

}
