package adform.exercises

/*
Exam_data problem

Assume students are appearing for an exam that is starting in M hours

Each student gets a different exam paper and it could start at a different time from others.

The exam will contain questions from K lessons

Each lesson takes L hours to learn

You are given a CSV file containing many lines, each having the integers K, L, M as defined above

Write a scala snippet that reads this file and for each line produces an output:

"YES" if the student can finish preparation BEFORE the exam begins

"NO" if the student will not be able to finish preparation before the exam begins

Pay attention to the amount of time needed by the program to execute - try to be as optimal as possible
*/

object Ex1ExamPrepProblem extends App {

  def willPreparationComplete(k: Int, l: Int, m: Int): String =
    if (k * l > m) "NO" else "YES"

  val fileResult = FileUtil.readFileAsSeq("adform/ex1_exam_data.csv")
//  println(fileResult)
//  FileUtil.printParsedFile(fileResult)
  /*fileResult
    .foreach(
      _.map((klm: String) => {
        val fields: Seq[String] = klm.split(",")
        willPreparationComplete(fields(0).trim.toInt, fields(1).trim.toInt, fields(2).trim.toInt)
      })
        .foreach(println)
    )*/

  fileResult
    .map(
      _.map((klm: String) => {
        val fields: Seq[String] = klm.split(",")
        willPreparationComplete(fields(0).trim.toInt, fields(1).trim.toInt, fields(2).trim.toInt)
      })
    )
    .foreach(_.foreach(println))
}
