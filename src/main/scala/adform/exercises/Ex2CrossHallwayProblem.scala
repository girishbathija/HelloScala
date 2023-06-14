package adform.exercises

/*
Thief data problem
A thief wants to cross a hallway of N doors. These N doors are represented as a string. Each door is initially either open or close, represented by 1 (for open) or 0 (for closed) respectively. The thief is required to go through all the doors one by one in the order that they appear, starting from the leftmost door and moving only rightwards at each step.

To make the journey easier, the thief has a remote control, using which he can flip the state of all the doors at once. i.e. whenever the remote is used, all open doors will close and all the closed doors will open at the same time. Determine the minimum number of times the thief has to use this remote to cross the hallway of doors.

For example, the doors are 10011

The thief will start from the leftmost door and enter the first door. After passing through that door, the remote is used. This flips the string to 01100.

Now the thief passes through the next two doors in one go (because they are both open). Again, just before reaching the 4th door, the remote is used. Now that the string is in its original state, the thief passes through the last two doors as well. The minimum number of times the remote had to be used here was 2.

You are given a file with many strings that represent the doors of the hallways. For each line, your program has to output the minimum number of times the remote needs to be used so that the thief successfully crosses the hallway.

Try to solve this in an optimal fashion paying attention to performance.

Input files -
*/
object Ex2CrossHallwayProblem extends App {

//  val bits: String = "011001" // , "10011"

  //can we write the below function on string using fold ?
  def noOfFlips(bits: String, curr: Int, next: Int, count: Int): Int = {
    if (curr == 0)
      if(bits(curr).asDigit == 0) return count + 1 else return count
    noOfFlips(bits, curr-1, next-1, count + (bits(curr) ^ bits(next)) )
  }
//  println(noOfFlips(bits, bits.length - 1, bits.length - 2, 0))

  val parsedFile = FileUtil.readFileAsSeq("adform/ex2_thief_data.csv")
  FileUtil.printParsedFile(parsedFile)

  parsedFile
    .foreach(
      _.map(str => noOfFlips(str, str.length-1, str.length-2, 0))
        .foreach(println)
    )
}
