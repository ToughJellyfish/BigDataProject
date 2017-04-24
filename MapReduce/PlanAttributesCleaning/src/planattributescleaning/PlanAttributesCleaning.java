/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planattributescleaning;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.FileInputFormat;
//import org.apache.hadoop.mapred.FileOutputFormat;
//import org.apache.hadoop.mapred.JobClient;
//import org.apache.hadoop.mapred.JobConf;
//import org.apache.hadoop.mapred.MapReduceBase;
//import org.apache.hadoop.mapred.Mapper;
//import org.apache.hadoop.mapred.OutputCollector;
//import org.apache.hadoop.mapred.Reducer;
//import org.apache.hadoop.mapred.Reporter;
//import org.apache.hadoop.mapred.TextOutputFormat;
//import org.apache.hadoop.mapred.lib.MultipleOutputs;

    import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author Fei
 */
public class PlanAttributesCleaning {

    /**
     * @param args the command line arguments
     */
    /*
  public static class Events_Mapper1  extends MapReduceBase
        implements Mapper<Object, Text, Text, NullWritable> {
      private MultipleOutputs mos;
       // private final static SimpleDateFormat frmt = new SimpleDateFormat("'['dd/MMM/yyyy:HH:mm:ss");
        @Override
        public void configure(JobConf conf) {
            mos = new MultipleOutputs(conf);
  }
        
        @Override
        public void map(Object key, Text values, OutputCollector<Text, NullWritable> oc, Reporter reporter) throws IOException {
            
                String [] wordList = values.toString().split(",");
                try{
                String id = wordList[114].substring(0, 13);
                if(!wordList[43].equalsIgnoreCase("Yes")){
                    
                   
                    //for separated plan, get the planid, businessyear, deductiables, moop, combined, inntier1, and outnet, and copaytier1.
                    //tier2 doesn't matter and rare in us medical plan system
                    if(!wordList[12].isEmpty()||!wordList[16].isEmpty()||!wordList[21].isEmpty()){
                        mos.getCollector("Attributes","separated", reporter).collect(new Text(id+wordList[12]+wordList[16]
                                +wordList[17]+wordList[21]
                        +wordList[22]+wordList[26]+wordList[30]+wordList[34]+wordList[38]+wordList[42]+wordList[69]+wordList[73]+
                                wordList[74]+wordList[78]+wordList[79]+wordList[83]+wordList[87]+wordList[91]+wordList[95]+wordList[99]+wordList[103]), NullWritable.get());
                    }
                    
                    if(!wordList[139].isEmpty()||!wordList[143].isEmpty()||!wordList[148].isEmpty()){
                        mos.getCollector("Attributes","combined", reporter).collect(new Text(id+wordList[139]
                                +wordList[143]+wordList[144]+wordList[148]+wordList[149]+wordList[153]+wordList[157]+wordList[161]
                        +wordList[165]+wordList[169]+wordList[103]), NullWritable.get());
                    }
                }
                    if(wordList[43].equalsIgnoreCase("yes")){
                        mos.getCollector("Attributes","dental",reporter).collect(new Text(id+wordList[69]+wordList[73]+
                                wordList[74]+wordList[78]+wordList[79]+wordList[83]+wordList[87]+wordList[91]+wordList[95]+wordList[99]+wordList[103]+wordList[63]), NullWritable.get());
                    }
                        
                }
                
                
            catch(Exception e){
        }           
        }

        @Override
        public void close() throws IOException {
            mos.close(); //To change body of generated methods, choose Tools | Templates.
        }
        


    }

  
    public static class Events_Reducer1 extends MapReduceBase implements Reducer<Text, NullWritable, Text, NullWritable> {
       
     
      @Override
        public void reduce(Text key, Iterator<NullWritable> values, OutputCollector<Text, NullWritable> oc, Reporter rprtr) throws IOException{
            
            oc.collect(key, NullWritable.get());
        }



       

    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
       JobConf conf = new JobConf(PlanAttributesCleaning.class);
        conf.setJobName("AttributesCleaning");
        

        conf.setMapperClass(Events_Mapper1.class);

        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(NullWritable.class);
        conf.setNumReduceTasks(0);
        MultipleOutputs.addNamedOutput(conf, "Attributes", TextOutputFormat.class, Text.class, NullWritable.class);
        
//job.setNumReduceTasks(0);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
//        boolean success = job.waitForCompletion(true);
//        Configuration conf1 = new Configuration();
//        Job job1 = new Job(conf1,"count total events happend one day in each country");
//        job1.setJarByClass(PlanAttributesCleaning.class);
//        if(success){
//            
//        }
//        job.setReducerClass(Events_Reducer1.class);
        JobClient.runJob(conf);

    }
    
}

*/
  public static class Events_Mapper1 extends Mapper<Object, Text, Text, NullWritable> {
        
        @Override
        public void map(Object key, Text values, Context context) throws IOException, InterruptedException{
            try{
                String [] wordList = values.toString().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String id = wordList[114].substring(0, 14);
                int i = 0;
               if(wordList[103].equalsIgnoreCase("low")||wordList[103].equalsIgnoreCase("high"))//dental
               {
                   
                   context.write(new Text(String.valueOf(i)+id+","+wordList[69]+","+wordList[73]+","+
                                wordList[74]+","+wordList[78]+","+wordList[87]+","+wordList[91]+
                        ","+wordList[99]+","+wordList[103]+","+wordList[63]), NullWritable.get());
               }
               else if(!wordList[0].equalsIgnoreCase("AVCalculatorOutputNumber")){
                   if(!wordList[139].isEmpty()||!wordList[143].isEmpty()||!wordList[144].isEmpty()||!wordList[148].isEmpty()||!wordList[157].isEmpty()||
                           !wordList[161].isEmpty()||!wordList[169].isEmpty()){
                       i = 1;
                       context.write(new Text(String.valueOf(i)+id+","+wordList[139]+","+wordList[143]+","+
                                wordList[144]+","+wordList[148]+","+wordList[157]+","+wordList[161]+
                        ","+wordList[169]+","+wordList[103]), NullWritable.get());
                   }
                   if(!wordList[12].isEmpty()||!wordList[16].isEmpty()||!wordList[17].isEmpty()||!wordList[21].isEmpty()||!wordList[30].isEmpty()||
                           !wordList[34].isEmpty()||!wordList[42].isEmpty()){
                       i = 2;
                       context.write(new Text(String.valueOf(i)+id+","+wordList[12]+","+wordList[16]+","+
                                wordList[17]+","+wordList[21]+","+wordList[30]+","+wordList[34]+
                        ","+wordList[42]+","+wordList[103]), NullWritable.get());
                       
                   }
                   if(!wordList[69].isEmpty()||!wordList[73].isEmpty()||!wordList[74].isEmpty()||!wordList[78].isEmpty()||!wordList[87].isEmpty()||
                           !wordList[91].isEmpty()||!wordList[99].isEmpty())//dental
               {
                       i =3;
                   
                   context.write(new Text(String.valueOf(i)+id+","+wordList[69]+","+wordList[73]+","+
                                wordList[74]+","+wordList[78]+","+wordList[87]+","+wordList[91]+
                        ","+wordList[99]+","+wordList[103]), NullWritable.get());
               }
                   
                       
               }
            
            }catch(Exception e){
        }           
        }

    }
  public static class GroupPartitioner extends Partitioner<Text, NullWritable>{

        @Override
        public int getPartition(Text key, NullWritable value, int i) {
            return Integer.parseInt(key.toString().substring(0, 1) )% i;
        }
  }

    public static class Events_Reducer1 extends org.apache.hadoop.mapreduce.Reducer<Text, NullWritable, Text, NullWritable> {
       
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            
            context.write(new Text(key.toString().substring(1)), NullWritable.get());
        }

       

    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = new Job(conf,"attributes cleaning");
        job.setJarByClass(PlanAttributesCleaning.class);

        job.setMapperClass(Events_Mapper1.class);

        job.setReducerClass(Events_Reducer1.class);
        job.setPartitionerClass(GroupPartitioner.class);
        
        job.setNumReduceTasks(4);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new Path(args[0]));
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}
    
