package adform.exercises

/*
Harvest
You are a fruit farm manager and you have several employees. Your employees gather fruits every day. Your task is to reward the best employees and drive your farm towards the best profitability. Of course, you can't do that without proper data.

There are two files: harvest.csv and prices.csv. First contains data on amounts of fruits collected by a given gatherer on a given day. The other contains fruit prices on a given day.

Before you make any decision, you need to know:

1. Who is your best gatherer in terms of the amounts of fruits gathered every month? Are there employees that are better at gathering some specific fruit?

2. What is your best-earning fruit (overall and by month)? Which is your least profitable fruit (again, overall and by month)?

3. Which gatherer contributed most to your income (during the whole year and by month)?

Headers
gatherer,date,fruit,amount
fruit,date,price
*/
object Ex4HarvestProblem extends App {

  // Intentional about throwing an exception. want the execution to halt in case of an exception
  val harvestCsv: Seq[String] = FileUtil.readFileAsSeq("adform/ex4_harvest.csv", true).get
  //  harvestData.foreach(println)

  //NOTE - edited the file to include missing prices for 2020-01-01
  val pricesCsv: Seq[String] = FileUtil.readFileAsSeq("adform/ex4_prices.csv", true).get
  //  prices.foreach(println)

  //Q 1a
  println("\n1a. Who is your best gatherer in terms of the amounts of fruits gathered every month?")
  HarvestSolution.topGathererPerMonth.toSeq.sortBy(_._1).foreach(println)

  //Q 1b
  println("\n1b. Are there employees that are better at gathering some specific fruit ?")
  HarvestSolution.topFruitByGatherer.toSeq.sortBy(_._1).foreach(println)

  //Q2a
  println("\n2a. What is your least and best-earning fruit by month?")
  HarvestSolution.worstNBestFruitByRevenuePerMonth.toSeq.sortBy(_._1).foreach(println)

  //Q2b
  println("\n2b. What is your least and best-earning fruit by year?")
  HarvestSolution.worstBestFruitByRevenuePerYear.toSeq.sortBy(_._1).foreach(println)

  //Q3a
  println("\n3a. Which gatherer contributed least and most to your income every month ?")
  HarvestSolution.worstNBestGathererByRevenuePerMonth.toSeq.sortBy(_._1).foreach(println)

  //Q3b
  println("\n3b. Which gatherer contributed least and most to your income every yr ?")
  HarvestSolution.worstNBestGathererByRevenuePerYear.toSeq.sortBy(_._1).foreach(println)
}

//TODO - exercise: try LocalDate instead of String date
case class FruitLineItem(gatherer: String, date: String, fruit: String, amount: Float) {
  def dailyPriceKey: String = fruit + "_" + date
}

object FruitLineItem {
  def apply(csvLine: String): FruitLineItem = {
    val fields = csvLine.split(",")
    new FruitLineItem(fields(0), fields(1), fields(2), fields(3).toFloat)
  }
}

object HarvestSolution {

  //this is okay coz Objects are initialised when they are used
  val harvestData: Seq[FruitLineItem] = Ex4HarvestProblem.harvestCsv.map(FruitLineItem(_))

  val dailyPrices: Map[String, Float] =
    Ex4HarvestProblem
      .pricesCsv
      .view
      .map(s => {
        val fields = s.split(",")
        (fields(0) + "_" + fields(1), fields(2).toFloat)
      })
      .toMap


  def topGathererPerMonth: Map[String, String] =
    harvestData
      .groupBy(fruit => fruit.date.substring(0, 7)) // group by month
      .view
      .mapValues(
        oneMonthFruits =>
          oneMonthFruits
            .groupMapReduce(_.gatherer)(_.amount)(_ + _)
            .maxBy(_._2) //max by amount
            ._1 // fetch gatherer
      )
      .toMap

  def topFruitByGatherer =
    harvestData
      .groupBy(_.gatherer)
      .view
      .mapValues(
        fruitsByGatherer =>
          fruitsByGatherer
            .groupMapReduce(_.fruit)(_.amount)(_ + _)
            .maxBy(_._2)
      )
      .toMap

  //TODO - generalise and merge with groupByGathererMinMaxRevenue
  def groupByFruitMinMaxRevenue(fruits: Seq[FruitLineItem]) = {
    val groupedByFruitSumRevenue =
      fruits
        .map(fruit => (fruit.fruit, fruit.amount * dailyPrices(fruit.dailyPriceKey)))
        .groupMapReduce(_._1)(_._2)(_ + _)
    (groupedByFruitSumRevenue.minBy(_._2), groupedByFruitSumRevenue.maxBy(_._2)) //return a tuple of (min,max) by revenue (amount * price)
  }

  //TODO - parameterise group by duration
  def worstNBestFruitByRevenuePerMonth: Map[String, ((String, Float), (String, Float))] =
    harvestData
      .groupBy(fruit => fruit.date.substring(0, 7)) // group by month
      .view
      .mapValues(oneMonthFruits => groupByFruitMinMaxRevenue(oneMonthFruits))
      .toMap

  def worstBestFruitByRevenuePerYear: Map[String, ((String, Float), (String, Float))] =
    harvestData
      .groupBy(fruit => fruit.date.substring(0, 4)) // group by year
      .view
      .mapValues(oneYrFruits => groupByFruitMinMaxRevenue(oneYrFruits))
      .toMap

  def groupByGathererMinMaxRevenue(fruits: Seq[FruitLineItem]) = {
    val groupedByFruitSumRevenue =
      fruits
        .map(fruit => (fruit.gatherer, fruit.amount * dailyPrices(fruit.dailyPriceKey)))
        .groupMapReduce(_._1)(_._2)(_ + _)
    (groupedByFruitSumRevenue.minBy(_._2), groupedByFruitSumRevenue.maxBy(_._2)) //return a tuple of (min,max) by revenue
  }

  //TODO - parameterise group by duration
  def worstNBestGathererByRevenuePerMonth: Map[String, ((String, Float), (String, Float))] =
    harvestData
      .groupBy(fruit => fruit.date.substring(0, 7)) // group by month
      .view
      .mapValues(oneMonthFruits => groupByGathererMinMaxRevenue(oneMonthFruits))
      .toMap

  def worstNBestGathererByRevenuePerYear: Map[String, ((String, Float), (String, Float))] =
    harvestData
      .groupBy(fruit => fruit.date.substring(0, 4)) // group by year
      .view
      .mapValues(oneYrFruits => groupByGathererMinMaxRevenue(oneYrFruits))
      .toMap
}