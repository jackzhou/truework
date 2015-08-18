package com.on.work.aggregation;

import com.on.work.ComparatorUtils;
import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class TrueReportDriver extends Configured implements Tool {

    public static class TrimPartitioner
            extends Partitioner<Vehicle, VehiclePrice> {

        @Override
        public int getPartition(Vehicle key, VehiclePrice VehiclePrice, int numPartitions) {
            int result = key.getMake().hashCode();
            result = 31 * result + key.getModel().hashCode();
            result = 31 * result + key.getModelYear().hashCode();
            result = 31 * result + key.getTrim().hashCode();
            return Math.abs(result) % numPartitions;
        }
    }


    public static class TrimComparator extends WritableComparator {
        protected TrimComparator() {
            super(Vehicle.class, true);
        }

        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            Vehicle v1 = (Vehicle) w1;
            Vehicle v2 = (Vehicle) w2;
            int r = v1.getMake().compareTo(v2.getMake());
            r = r != 0 ? r : v1.getModel().compareTo(v2.getModel());
            r = r != 0 ? r : v1.getModelYear().compareTo(v2.getModelYear());
            r = r != 0 ? r : v1.getTrim().compareTo(v2.getTrim());
            return r;

        }

        @Override
        public int compare(byte[] b1, int s1, int l1,
                           byte[] b2, int s2, int l2) {
            try {
                return ComparatorUtils.compareTextArray(b1, s1, b2, s2, 4);
            }catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

        }
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.printf("Usage: %s [generic options] <inventory_data_input> <sales_data_input> <output>\n",
                    getClass().getSimpleName());
            return -1;
        }

        Job job = new Job(getConf(), "True Price Report Job");
        job.setJarByClass(getClass());
        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, InventoryMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, SalesMapper.class);
        job.setPartitionerClass(TrimPartitioner.class);
        job.setGroupingComparatorClass(TrimComparator.class);
        job.setReducerClass(VehicleDataReducer.class);
        job.setOutputKeyClass(Vehicle.class);
        job.setOutputValueClass(VehiclePrice.class);
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new TrueReportDriver(), args);
        System.exit(exitCode);
    }
}
