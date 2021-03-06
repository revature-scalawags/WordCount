import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.fs.Path
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.io.Text
import org.apache.hadoop.io.IntWritable

object Main extends App {

  if (args.length != 2) {
    println("Usage: Main [input dir] [output dir]")
    System.exit(-1)
  }

  val job = Job.getInstance()

  job.setJarByClass(Main.getClass())
  job.setJobName("Word Count")
  job.setInputFormatClass(classOf[TextInputFormat])

  FileInputFormat.setInputPaths(job, new Path(args(0)))
  FileOutputFormat.setOutputPath(job, new Path(args(1)))

  job.setMapperClass(classOf[WordMapper])
  job.setReducerClass(classOf[WordReducer])

  job.setOutputKeyClass(classOf[Text])
  job.setOutputValueClass(classOf[IntWritable])

  val success = job.waitForCompletion(true)
  System.exit(if (success) 0 else 1)
}
