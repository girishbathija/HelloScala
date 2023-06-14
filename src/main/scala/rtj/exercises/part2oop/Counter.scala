package rtj.exercises.part2oop



class Counter(val count:Int) {

  def increment():Counter = new Counter(this.count+1)
  def decrement():Counter = new Counter(this.count-1)

  def increment(delta:Int = 1):Counter = new Counter(this.count+delta)
  def decrement(delta:Int = 1):Counter = new Counter(this.count-delta)

}
