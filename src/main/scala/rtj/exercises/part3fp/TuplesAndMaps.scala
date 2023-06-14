package rtj.exercises.part3fp

object TuplesAndMaps extends App {

  val aMap = Map("Jim"->555, "JIM"->900)
  println(aMap)

  println(aMap.map(pair=>pair._1.toLowerCase->pair._2))

  object SocialNetwork {
    var peopleGraph: Map[Person, Seq[Person]] = Map.empty

    def addPerson(p: Person) =  peopleGraph += (p -> Seq.empty)

    def removePerson(p: Person) = {
      val peopleToUpdate = peopleGraph.get(p)
      //peopleGraph.filterNot(_._1 == p).map(pair=>if(friends.contains(pair._1)) (pair._1 -> pair._2.filterNot(_ == p)) else pair)
      peopleGraph = peopleGraph.view.filterKeys(_ ne p).mapValues(friends => friends.filterNot(_ ne p) ).toMap
    }

    def friendship(p1: Person, p2: Person) = {
      peopleGraph += (p1->peopleGraph.getOrElse(p1, Seq(p2)).appended(p2) )
      peopleGraph += (p2->peopleGraph.getOrElse(p2, Seq(p1)).appended(p1) )
    }

    def unfriend(p1: Person, p2: Person) = {
      peopleGraph += (p1 -> peopleGraph.getOrElse(p1, Seq(p2)).filterNot(_ == p2))
      peopleGraph += (p2 -> peopleGraph.getOrElse(p2, Seq(p1)).filterNot(_ == p1))
    }

    def numberOfFriends(p: Person) =
      peopleGraph.filterKeys(_==p).mapValues(_.count(_ ne null))

    def personWithMaxFriends() =
      peopleGraph.mapValues(_.count(_ ne null)).fold(Nil)((pair1,pair2)=>pair1)




  }

  case class Person(val name: String) {

  }

}
