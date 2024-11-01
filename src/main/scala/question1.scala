import org.apache.spark.sql.{Row, SparkSession, functions}
import org.apache.spark.sql.functions.{col, desc, lower, trim}

object IndiaExportAnalysis {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("India Export Analysis")
      .master("local[*]")
      .getOrCreate()

    val filePath = "/Users/manishkumartyagi/Downloads/untitled/src/main/data/2018-2010_export.csv"

    if (args.length < 2) {
      println("Please provide year and country as arguments.")
      return
    }

    val year = args(0).trim
    val country = args(1).trim

    // Read data from the CSV file
    val df = spark.read.option("header", "true").csv(filePath)

    // Print the schema and some rows to verify data
    println("Schema of the DataFrame:")
    df.printSchema()
    println("Sample data from the DataFrame:")
    df.show(5)

    // Check unique values for year and country
    println("Unique years in the data:")
    df.select("year").distinct().show()
    println("Unique countries in the data:")
    df.select("country").distinct().show()

    // Process the data
    val filteredDF = df.filter(
      trim(lower(col("year"))) === year.toLowerCase &&
        trim(lower(col("country"))) === country.toLowerCase
    )

    // Debugging: Show filtered results to confirm the filter worked
    println("Filtered results:")
    filteredDF.show()

    // Aggregate and get the top commodity
    val result = filteredDF
      .groupBy("Commodity")
      .agg(functions.sum(col("value").cast("double")).alias("total_value"))
      .orderBy(desc("total_value"))
      .limit(1)

    // Collect results and print to console
    val results = result.collect()
    if (results.isEmpty) {
      println(s"No data found for the specified year ($year) and country ($country).")
    } else {
      results.foreach {
        case Row(commodity: String, totalValue: Double) =>
          println(s"Top commodity exported to $country in $year:\nCommodity: $commodity, Total Value: $totalValue")
      }
    }

    spark.stop()
  }
}
