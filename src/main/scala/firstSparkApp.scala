import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object FirstApp {

	def time[R](block: => R): R = {
	    val t0 = System.nanoTime()
	    val result = block    // call-by-name
	    val t1 = System.nanoTime()
	    println("Elapsed time: " + (t1 - t0) + "ns")
	    result
	}

	def runSpark(){
		val sparkHome = "/code_tmp/spark-0.9.1"
		val logFile = sparkHome.concat("/README.md")
		val sc = new SparkContext("local[4]","First App",sparkHome, //multi thread doesnt provide any speedup on my machine - not surprising since the readme is so small
			List("target/scala-2.10/simple-first-project_2.10-1.0.jar")) //jar comes from options in simple.sbt
		val logData = sc.textFile(logFile,2).cache()
		val numAs = logData.filter(line => line.contains("a")).count()
		println("Lines with a: %s".format(numAs))
	}

	def main(args: Array[String]){
		time(runSpark())
	}
}
