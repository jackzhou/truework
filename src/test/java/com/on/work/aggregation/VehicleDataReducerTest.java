package com.on.work.aggregation;

import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jzhou on 8/18/15.
 */
public class VehicleDataReducerTest {

    @Test
    public void testReduce() throws Exception {
        Vehicle v = new Vehicle("m", "MN", "y", "t", "v", true);
        List<VehiclePrice> list = new ArrayList<VehiclePrice>();
        list.add(new VehiclePrice(15,"v1", true));
        list.add(new VehiclePrice(5, "v2", true));
        list.add(new VehiclePrice(3, 29,"v3", false));
        list.add(new VehiclePrice(5, "v3", true));
        list.add(new VehiclePrice(1, 11,"v4", false));
        list.add(new VehiclePrice(5, "v4", true));
        list.add(new VehiclePrice(2, 45,"v5", false));
        list.add(new VehiclePrice(5, "v5", true));
        list.add(new VehiclePrice(10, 50,"v6", false));
        list.add(new VehiclePrice(5, "v6", true));

        new ReduceDriver<Vehicle, VehiclePrice, NullWritable, Text>()
                .withReducer(new VehicleDataReducer())
                .withInput(v, list)
                .withOutput(NullWritable.get(),new Text("m\tMN\ty\tt\t2\t2.0\t4\t4.0\t10.0"))
                .runTest();

    }
}