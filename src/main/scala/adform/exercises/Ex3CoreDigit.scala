package adform.exercises

object Ex3CoreDigit extends App {

  def coreDigit(number: String, multiplier: Int): Int = {
    def coreDigitCalc(number: String): Int = {
      if (number.length == 1) return number(0).asDigit
//      println(number)
      coreDigitCalc(number.map(_.asDigit).sum.toString)
    }
    val coreDigitOfNumber = coreDigitCalc(number)
//    println(coreDigitOfNumber + " * "+ multiplier)
    if(multiplier > 1)
      coreDigitCalc((coreDigitOfNumber*multiplier).toString)
    else
      coreDigitOfNumber
  }
  val parsedFile = FileUtil.readFileAsSeq("adform/ex3_core_data.txt")
//  FileUtil.printParsedFile(parsedFile)
//  FileUtil.printNoOfLines(parsedFile)

  parsedFile
    .map(
      _.map(
        numberStr => {
          val inputs = numberStr.split(" ")
          coreDigit(inputs(0), inputs(1).toInt)
        }
      )
    )
    .foreach(
      _.foreach(println(_))
    )

}
