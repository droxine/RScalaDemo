import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.ddahl.rscala.RClient

object RScalaDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val conf = new SparkConf().setAppName("RScalaDemo")
    val sc = new SparkContext(conf)

    val R = RClient("R",serializeOutput=true)
    R eval """
        # install.packages("readr")
        library("readr")
        DatasetEx <- read_csv("/home/sangeles/Byte/BIGDATA/Roberto/Data/DatasetEx.csv")
        #DatasetEx <- read_csv("hdfs:///user/sangeles/Data/DatasetEx.csv")
        View(DatasetEx)
        #DatasetEx <- cbind(col_1,col_2,col_3)
        DatasetEx <- as.data.frame(DatasetEx)
        DatasetEx$col_2 <- ifelse(DatasetEx$col_2 > 0 & DatasetEx$col_2 <= 10, "(0-10]",
                                  ifelse(DatasetEx$col_2 > 10 & DatasetEx$col_2 <= 20, "(10-20]",
                                         ifelse(DatasetEx$col_2 > 20 & DatasetEx$col_2 <= 6000, "(20-6000]", DatasetEx$col_2)))
        DatasetEx$col_3 <- ifelse(DatasetEx$col_3 == 99999999999, "Ilimitado", "Limitado")

        DatasetEx_trans <- DatasetEx
        View(DatasetEx_trans)
        write.csv(DatasetEx_trans, '/home/sangeles/Byte/BIGDATA/Roberto/Data/DatasetEx_trans.csv', row.names = FALSE)
        #write.csv(DatasetEx_trans, 'hdfs:///user/sangeles/Data/DatasetEx_trans.csv', row.names = FALSE)
    """

    println("It finished")
  }
}
