package Cloud.ApacheLog;

import java.io.IOException;  
 import java.util.*;  
  
 import org.apache.hadoop.fs.Path;  
 import org.apache.hadoop.conf.*;  
 import org.apache.hadoop.io.*;  
 import org.apache.hadoop.mapred.*;  
 import org.apache.hadoop.util.*; 

/**
 * Mapper that takes a line from an Apache access log and emits the IP with a
 * count of 1. This can be used to count the number of times that a host has
 * hit a website.
 */
public class Mapper1 extends MapReduceBase 
                        implements Mapper<LongWritable, Text, Text, DoubleWritable>
{

  // Regular expression to match the IP at the beginning of the line in an
  // Apache access log
 // private static final Pattern ipPattern = Pattern.compile("^([\\d\\.]+)\\s");

  // Reusable IntWritable for the count
 // private static final IntWritable one = new IntWritable(1);
  
  public void map(LongWritable fileOffset, Text lineContents,
      OutputCollector<Text, DoubleWritable> output, Reporter reporter)
      throws IOException {
    String line = lineContents.toString();  
    StringTokenizer tokenizer = new StringTokenizer(line,","); 
    // apply the regex to the line of the access log
    String s=tokenizer.nextToken();	
	s=s+","+tokenizer.nextToken();
	for(int i=0;i<12;i++){
		tokenizer.nextToken();
		
	}
	double l=Double.parseDouble(tokenizer.nextToken());
	output.collect(new Text(s),new DoubleWritable(l));
  }

}
