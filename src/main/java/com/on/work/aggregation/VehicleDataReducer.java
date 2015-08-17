package com.on.work.aggregation;

import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by jzhou on 8/16/15.
 */
class VehicleDataReducer
        extends Reducer<Vehicle, VehiclePrice, NullWritable, Text> {

    private String getAve(int count, double total) {
        return String.valueOf(count == 0 ? 0.d : total / count);
    }

    @Override
    protected void reduce(Vehicle key, Iterable<VehiclePrice> values,
                          Context context) throws IOException, InterruptedException {
        String soldVin = null;
        int totNumUnsold = 0;
        double totSellingPrice = 0;
        int tot30 = 0;
        double totSale30 = 0;
        int tot60 = 0;
        double totSale60 = 0;
        for (VehiclePrice price : values) {
            if (price.isFromSales()) {
                soldVin = price.getVin().toString();
                tot30 += price.getElapsed().get() <= 30 ? 1 : 0;
                totSale30 += price.getElapsed().get() <= 30 ? price.getSalePrice().get() : 0;
                tot60 += price.getElapsed().get() <= 60 ? 1 : 0;
                totSale60 += price.getElapsed().get() <= 60 ? price.getSalePrice().get() : 0;
            } else {
                if (!price.getVin().toString().equals(soldVin)) {//only process unsold inventory
                    totNumUnsold += 1;
                    totSellingPrice += price.getSellingPrice().get();
                }
            }
        }
        String[] row = new String[]{key.getMake().toString(),
                key.getModel().toString(),
                key.getModelYear().toString(),
                key.getTrim().toString(),
                String.valueOf(tot30),
                getAve(tot30, totSale30),
                String.valueOf(tot60),
                getAve(tot60, totSale60),
                getAve(totNumUnsold, totSellingPrice)
        };
        String line = StringUtils.join(row, "\t");
        context.write(NullWritable.get(), new Text(line));
    }
}
