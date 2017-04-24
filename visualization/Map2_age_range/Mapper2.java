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
public class Mapper2 extends MapReduceBase 
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
   
	for(int i=0;i<13;i++){
		tokenizer.nextToken();
		
	}
	String s=tokenizer.nextToken();
	double l=Double.parseDouble(tokenizer.nextToken());
	try{
		int x=Integer.parseInt(s);
		if(x>20&&x<=30){
			s="21-30";
		}else if(x>30&&x<=40){
			s="31-40";
		}else if(x>40&&x<=50){
			s="41-50";
		}else{
			s="51-65";
		}
	}catch(Exception e){
		
	}
	output.collect(new Text(s),new DoubleWritable(l));
  }

}
