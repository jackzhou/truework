package com.on.work.aggregation;

import com.on.work.model.TrueDataException;
import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by jzhou on 8/16/15.
 */
public abstract class AbstractDataMapper extends Mapper<LongWritable, Text, Vehicle, VehiclePrice> {
    private Logger logger = Logger.getLogger(AbstractDataMapper.class);
    private Vehicle vehicle = new Vehicle();
    private VehiclePrice price = new VehiclePrice();

    protected void map(LongWritable key, Text value,
                       Context context) throws IOException, InterruptedException {
        try {
            createParser(vehicle, price).parse(value.toString());
            context.write(this.vehicle, this.price);
        } catch (TrueDataException te) {
            context.getCounter(te.error).increment(1);
            logger.warn("parsing error", te.getCause());
        }
    }

    abstract protected DataParser createParser(Vehicle vehicle, VehiclePrice price);
}
