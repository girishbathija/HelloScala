package rtj.beginner.part3fp

import scala.util.Random

object Options extends App {
  
  val myFirstOption : Option[Int] = Some(4)
  val noOption : Option[Int] = None
  
  println(myFirstOption)
  println(noOption)

  //HOW TO WORK with unsafe APIs
  def unsafeMethod(): String = null
  val result_IncorrectWay = Some(unsafeMethod()) // WRONG - some should never contain a null value, it defeats the whole purpose
  println(result_IncorrectWay)
  //my guess - wrapping null in Some() will then lead to explicit checking of whether the value is present or not,
  // which is the same as checking for null, and hence defeats the purpose
  val result = Option(unsafeMethod()) //Some or None
  println(result)

  def backupMethod(): String = "A valid result"
  // this is how to deal with unsafe apis on the client side ie when calling unsafe methods
  val chainedResult = Option(unsafeMethod) orElse Option(backupMethod)
  println(chainedResult)

  // HOW TO DESIGN unsafe APIs the right way
  def betterUnsafeMethod(): Option[String] = None // return None when you have to return null
  def betterBackupMethod(): Option[String] = Some("A valid result")

  // the client doesn't have to wrap into option explicitly if API returns the option itself
  val betterChainedResult = betterUnsafeMethod orElse betterBackupMethod
  println(betterChainedResult)

  // functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) // UNSAFE - DO NOT USE THIS, atleast not without checking isEmpty
  println(result_IncorrectWay.get) // this could result into null pointer exceptions, if methods are called on the get() result

  //map, flatMap, filter
  //map
  println(myFirstOption.map(_ * 2)) // mapping Some(4)
//  println(result_IncorrectWay.map(_ * 2)) // mapping some(null) - throws NPE
  println(result.map(_ * 2)) // mapping none

  println(myFirstOption.map("I am "+_ )) //x => "I am "+x

  //flatmap
  println(myFirstOption.flatMap(x => Option(x * 10)))
  //WHY do we need flatmap ? is it while using unsafe methods in lambdas. ANOTHER ANS = flatten nested options
//  println(myFirstOption.map(unsafeMethod())) // throws NPE
  println(myFirstOption.flatMap(x=>Option(unsafeMethod())))

  //filter
  println("checking filters")
  println(myFirstOption.filter(_ == 3))
  println(myFirstOption.filter(_ == 4))
  println(result_IncorrectWay.filter(_ == null))
  println(result_IncorrectWay.filter(_ == ""))
//  println(result_IncorrectWay.filter(_.isEmpty)) //NPE
  println(result.filter(_ == "hello"))
  println(result.filter(_ == ""))
  println(result.filter(_ == None))

  /*
  * Exercise
  * */
  val config: Map[String, String] = Map(
    //fetched from elsewhere
    "host"->null,//"176.45.36.1",
    "port"->"80"
  )

  class Connection {
    def connect = "Connected" // simulate connect to some server
  }

  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  //try to establish a connection, if so - print the connect method
  /*
  //attempt 1 for single connection
  val connection = Connection(config("host"), config("port"))
  println(connection.map(_.connect))
  */
  /*
  //attempt 1 with multiple connections
  for{
    i <- 1 to 100
  } yield println(Connection(config("host"), config("port")).map(_.connect))
  */
  //answer
  for {
    i <- 1 to 100
  } yield config.get("host")
    .flatMap(h=> config.get("port")
      .flatMap(p=>Connection(h,p)))
      .map(_.connect)
    .foreach(println)
/*

  val forConnectionStatus = for{
    i <- 1 to 10
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)
*/

}
