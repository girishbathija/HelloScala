package rtj.beginner.part2oop

import java.time.LocalDate

class Writer(val firstName:String, val surname:String, val year:Int) {

  def fullname():String = this.firstName+" "+this.surname

}

class Novel(val name:String, val yearOfRelease:Int, val author:Writer) {

  def authorAge():Int = this.yearOfRelease - this.author.year;

  def isWrittenBy(author: Writer) = this.author == author

  def copy(newYrOfRelease:Int) = new Novel(this.name, newYrOfRelease, this.author)
}

