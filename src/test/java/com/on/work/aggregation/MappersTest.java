package com.on.work.aggregation;

import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jzhou on 8/16/15.
 */
public class MappersTest {

    @Test
    public void processesValidRecord() throws IOException, InterruptedException {
        Text value = new Text("TOYOTA\tRAV4\t2015\tLE\t8tttt\t2015-08-10\t88001.3\t8\n");
        new MapDriver<LongWritable, Text, Vehicle, VehiclePrice>()
                .withMapper(new SalesMapper())
                .withInput(new LongWritable(0), value)
                .withOutput(new Vehicle("TOYOTA", "RAV4", "2015", "LE", "8tttt",false),
                        new VehiclePrice(88001.3d, 7,"8tttt", false))
                .runTest();
    }
}