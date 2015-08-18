package com.on.work.aggregation;

import com.on.work.model.DataError;
import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jzhou on 8/16/15.
 */
public class MappersTest {

    @Test
    public void processesValidSalesRecord() throws Exception {
        Text value = new Text("TOYOTA\tRAV4\t2015\tLE\t8tttt\t2015-08-10\t88001.3\t8\n");
        new MapDriver<LongWritable, Text, Vehicle, VehiclePrice>()
                .withMapper(new SalesMapper())
                .withInput(new LongWritable(0), value)
                .withOutput(new Vehicle("TOYOTA", "RAV4", "2015", "LE", "8tttt",false),
                        new VehiclePrice(88001.3d, 7,"8tttt", false))
                .runTest();
    }

    @Test
    public void processValidInventoryRecord() throws Exception {
        Text value = new Text("Ford\tF-150\t2011\tXL\t3bcdefa\tBuild Touch\t25003\t25000\t3");
        new MapDriver<LongWritable, Text, Vehicle, VehiclePrice>()
                .withMapper(new InventoryMapper())
                .withInput(new LongWritable(0), value)
                .withOutput(new Vehicle("Ford", "F-150", "2011", "XL", "3bcdefa",true),
                        new VehiclePrice(25003.d, "3bcdefa", true))
                .runTest();
    }

    @Test
    public void processInvalidInInventoryRecord() throws Exception {
        Text value = new Text("Ford\tF-150\t2011\tXL\t3bcdefa\tBuild Touch\t25003\t25000");
        MapDriver md = new MapDriver<LongWritable, Text, Vehicle, VehiclePrice>()
                .withMapper(new InventoryMapper())
                .withInput(new LongWritable(0), value);
                md.runTest();
        Assert.assertEquals("expected a size error ounter", 1,
                md.getCounters().findCounter(DataError.DataSizeError).getValue());
    }
}