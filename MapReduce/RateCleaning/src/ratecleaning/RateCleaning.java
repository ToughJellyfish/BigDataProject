/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratecleaning;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author Fei
 */
public class RateCleaning {

    /**
     * @param args the command line arguments
     */
  public static class Events_Mapper1 extends Mapper<Object, Text, Text, NullWritable> {
        private String rate = "";
       private String marriage = ""; //0 for 
        private String dependent ="";
        @Override
        public void map(Object key, Text values, Context context) throws IOException, InterruptedException{
            try{
                String [] wordList = values.toString().split(",");
                if(!wordList[0].equalsIgnoreCase("BusinessYear")){
                if(wordList[12].equalsIgnoreCase("No Preference")&& !wordList[13].equalsIgnoreCase("Family Option")){
                    if(!wordList[14].isEmpty())
                        rate = wordList[14];
                }
                
                if(wordList[12].contains("Tobacco User/")&& !wordList[13].equalsIgnoreCase("Family Option")){
                if(!wordList[15].isEmpty())   
                    rate = wordList[15];
                }
                if(!wordList[13].equalsIgnoreCase("Family Option")){
                context.write(new Text(wordList[0]+","+wordList[1]+wordList[11].substring(12)+","+
                        wordList[10]+","+wordList[13]+","+"No"+","+"none"+","+rate), NullWritable.get());
                }else{
                    if(!wordList[16].isEmpty()){
                        
                        context.write(new Text(wordList[0]+","+wordList[1]+wordList[11].substring(12)+","+
                        wordList[10]+","+wordList[13]+","+"Yes"+","+"none"+","+wordList[16]), NullWritable.get());
                        if(!wordList[20].isEmpty())
                            context.write(new Text(wordList[0]+","+wordList[1]+wordList[11].substring(12)+","+
                        wordList[10]+","+wordList[13]+","+"Yes"+","+"one"+","+wordList[20]), NullWritable.get());
                        if(!wordList[21].isEmpty())
                            context.write(new Text(wordList[0]+","+wordList[1]+wordList[11].substring(12)+","+
                        wordList[10]+","+wordList[13]+","+"Yes"+","+"two"+","+wordList[21]), NullWritable.get());
                        if(!wordList[22].isEmpty())
                            context.write(new Text(wordList[0]+","+wordList[1]+wordList[11].substring(12)+","+
                        wordList[10]+","+wordList[13]+","+"Yes"+","+"three"+","+wordList[22]), NullWritable.get());
                    }
                    if(!wordList[17].isEmpty())
                            context.write(new Text(wordList[0]+","+wordList[1]+wordList[11].substring(12)+","+
                        wordList[10]+","+wordList[13]+","+"No"+","+"one"+","+wordList[17]), NullWritable.get());
                    if(!wordList[18].isEmpty())
                            context.write(new Text(wordList[0]+","+wordList[1]+wordList[11].substring(12)+","+
                        wordList[10]+","+wordList[13]+","+"No"+","+"two"+","+wordList[18]), NullWritable.get());
                        if(!wordList[19].isEmpty())
                            context.write(new Text(wordList[0]+","+wordList[1]+wordList[11].substring(12)+","+
                        wordList[10]+","+wordList[13]+","+"No"+","+"three"+","+wordList[19]), NullWritable.get());
                }
                }
            }catch(Exception e){
        } 
            
        }

    }

    public static class Events_Reducer1 extends Reducer<Text, NullWritable, Text, NullWritable> {
       
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            
            context.write(key, NullWritable.get());
        }

       

    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = new Job(conf,"rate cleaning");
        job.setJarByClass(RateCleaning.class);

        job.setMapperClass(Events_Mapper1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setReducerClass(Events_Reducer1.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}
    

