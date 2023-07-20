package rtj.beginner.part2oop

object OOBasics extends App{

  val author = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, author)

  println(novel.authorAge())
  println(novel.isWrittenBy(author))

  val imposter = new Writer("Charles", "Dickens", 1812)
  println(novel.isWrittenBy(imposter))


}