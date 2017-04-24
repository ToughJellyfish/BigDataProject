package Cloud.ApacheLog;

import java.io.IOException;  
 import java.util.*;  
  
 import org.apache.hadoop.fs.Path;  
 import org.apache.hadoop.conf.*;  
 import org.apache.hadoop.io.*;  
 import org.apache.hadoop.mapred.*;  
 import org.apache.hadoop.util.*; 
public class Runner3{

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception
  {
        JobConf conf = new JobConf(Runner3.class);
        conf.setJobName("ip-count");
        
        conf.setMapperClass(Mapper3.class);
        
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(DoubleWritable.class);
        
        conf.setReducerClass(Reducer3.class);
        
        
        // take the input and output from the command line
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);

	}

}
