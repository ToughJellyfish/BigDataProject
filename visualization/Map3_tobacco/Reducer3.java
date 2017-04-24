package Cloud.ApacheLog;

import java.io.IOException;  
 import java.util.*;  
  
 import org.apache.hadoop.fs.Path;  
 import org.apache.hadoop.conf.*;  
 import org.apache.hadoop.io.*;  
 import org.apache.hadoop.mapred.*;  
 import org.apache.hadoop.util.*; 

/**
 * Counts all of the hits for an ip. Outputs all ip's
 */
public class Reducer3 extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, DoubleWritable> 
{

  public void reduce(Text key, Iterator<DoubleWritable> counts,
      OutputCollector<Text, DoubleWritable> output, Reporter reporter)
      throws IOException {
    
     ArrayList<Double> list=new ArrayList<>();
	 

    
    // loop over the count and tally it up
    while (counts.hasNext())
    {
      DoubleWritable count = counts.next();
      list.add(count.get());
    }
	Collections.sort(list);
	double x=list.get(list.size()/2);

    output.collect(key,new DoubleWritable(x));

 }

}
