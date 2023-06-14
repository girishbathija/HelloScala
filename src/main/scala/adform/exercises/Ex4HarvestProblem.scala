package adform.exercises

import scala.util.Try


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

  //TODO - use LocalDate instead of String date
  case class FruitLineItem(gatherer: String, date: String, fruit: String, amount: Float) {
    def dailyPriceKey: String = fruit+"_"+date
  }

  object FruitLineItem {
    def apply(csvLine: String): FruitLineItem = {
      val fields = csvLine.split(",")
      new FruitLineItem(fields(0), fields(1), fields(2), fields(3).toFloat)
    }
  }

  private val harvestData: Try[Seq[String]] = FileUtil.readFileAsSeq("adform/ex4_harvest.csv")
//  FileUtil.printParsedFile(harvestData)

  private val priceData: Try[Seq[String]] = FileUtil.readFileAsSeq("adform/ex4_prices.csv")
//  FileUtil.printParsedFile(priceData)

  private val harvestDataSeq: Seq[String] = harvestData.get

  private val monthlyHarvest: Map[String, Seq[FruitLineItem]] = harvestDataSeq
    .map(FruitLineItem(_))
    .groupBy(fruit => fruit.date.substring(0, 7)) // group by month
  // TODO - use sorted map to maintain ordering of keys or just sort by keys while printing
//  println(monthlyHarvest)

  //NOTE - edited the file to include missing prices for 2020-01-01
  private val prices: Seq[String] = priceData.get

  private val dailyFruitPrice: Map[String, Float] = prices
    .view
    .map(s => {
      val fields = s.split(",")
      (fields(0) + "_" + fields(1), fields(2).toFloat) //Map Tuple (fruit_date, price)
    })
    .toMap
  println(dailyFruitPrice)
  println(dailyFruitPrice.get("apples_2020-01-01"))

  //Q 1.a
  private val topGathererByMonth: Map[String, String] = monthlyHarvest
    .view
    .mapValues(
      oneMonthFruits => {
        val groupedByGathererSumAmount = oneMonthFruits.groupMapReduce(_.gatherer)(_.amount)(_ + _)
        groupedByGathererSumAmount.maxBy(_._2)._1 //max by value, fetch key
      }
    )
    .toMap

  println("1. Top Gatherer By Month - \n"+topGathererByMonth) // verify : 5 - jason, 2 - jake

  //Q2 a. by month
  def mapFruitLineItemSeqToFruitByRevenueTuple(fruits: Seq[FruitLineItem]) = {
    //TODO - bug - unable to fetch price for apples_2020-01-01 although it exists in the map
    val groupedByFruitSumAmount = fruits.map(fruit => (fruit.fruit, fruit.amount * dailyFruitPrice.getOrElse(fruit.dailyPriceKey, 0f))).groupMapReduce(_._1)(_._2)(_ + _)
    (groupedByFruitSumAmount.minBy(_._2), groupedByFruitSumAmount.maxBy(_._2)) //return a tuple of (min,max) by value
  }


  private val minMaxFruitByRevenuePerMonth: Map[String, ((String, Float), (String, Float))] = monthlyHarvest
    .view
    .mapValues(oneMonthFruits => mapFruitLineItemSeqToFruitByRevenueTuple(oneMonthFruits))
    .toMap
  println()
  println("2. Worst and Best Fruit by Revenue every month - \n"+minMaxFruitByRevenuePerMonth)

  //Q2 b. by year
  private val minMaxFruitByRevenueForYear: ((String, Float), (String, Float)) = mapFruitLineItemSeqToFruitByRevenueTuple(harvestDataSeq.map(FruitLineItem(_)))

  println("2. Worst and Best Fruit by Revenue every month - \n"+minMaxFruitByRevenueForYear)

  //Q3 a. by month
  def mapFruitLineItemSeqToGathererByRevenueTuple(fruits: Seq[FruitLineItem]) = {
    //TODO - bug - unable to fetch price for apples_2020-01-01 although it exists in the map
    val groupedByFruitSumAmount = fruits.map(fruit => (fruit.gatherer, fruit.amount * dailyFruitPrice.getOrElse(fruit.dailyPriceKey, 0f))).groupMapReduce(_._1)(_._2)(_ + _)
    (groupedByFruitSumAmount.minBy(_._2), groupedByFruitSumAmount.maxBy(_._2)) //return a tuple of (min,max) by value
  }


  private val minMaxGathererByRevenuePerMonth: Map[String, ((String, Float), (String, Float))] = monthlyHarvest
    .view
    .mapValues(oneMonthFruits => mapFruitLineItemSeqToGathererByRevenueTuple(oneMonthFruits))
    .toMap
  println()
  println("2. Worst and Best Gatherer by Revenue every month - \n" + minMaxGathererByRevenuePerMonth)
  //TODO - returning 0 revenue for minimum entry - John 0 for 2020-01; this needs to be checked; need to debug

  //Q2 b. by year
  private val minMaxGathererByRevenueForYear: ((String, Float), (String, Float)) = mapFruitLineItemSeqToGathererByRevenueTuple(harvestDataSeq.map(FruitLineItem(_)))

  println("2. Worst and Best Gatherer by Revenue every month - \n" + minMaxGathererByRevenueForYear)
  //TODO - returning 0 revenue for minimum entry - John 0; this does is definitely wrong; need to debug
}
