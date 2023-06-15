package adform.exercises

import scala.util.{Try, Using}

object FileUtil {

  //TODO - create an overloaded function to ignore the header of a csv file
  def readFileAsSeq(filename: String, containsHeader: Boolean = false): Try[Seq[String]] =
    Using(io.Source.fromResource(filename)) {
      if(containsHeader) _.getLines().drop(1).toList else _.getLines().toList
    }

  def printParsedFile(parsedFile: Try[Seq[String]]) =
    parsedFile.foreach(_.foreach(println))

  def printNoOfLines(parsedFile: Try[Seq[String]]) =
    parsedFile.foreach(s => println(s.size))

}
