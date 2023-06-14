package adform.exercises

import scala.util.{Try, Using}

object FileUtil {

  //TODO - create an overloaded function to ignore the header of a csv file
  def readFileAsSeq(filename: String): Try[Seq[String]] =
    Using(io.Source.fromResource(filename)) {
      _.getLines().toList
    }

  def printParsedFile(parsedFile: Try[Seq[String]]) =
    parsedFile.foreach(_.foreach(println))

  def printNoOfLines(parsedFile: Try[Seq[String]]) =
    parsedFile.foreach(s => println(s.size))

}
