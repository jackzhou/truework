package com.on.work.aggregation;

import com.on.work.model.DataError;
import com.on.work.model.TrueDataException;
import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jzhou on 8/16/15.
 */
public class SalesParser extends DataParser {
    public SalesParser(Vehicle vehicle, VehiclePrice price, String sep, String dateFormat) {
        super(vehicle, price, sep, dateFormat);
    }

    public SalesParser(Vehicle vehicle, VehiclePrice price) {
        this(vehicle, price, null, null);
    }

    @Override
    protected void parsePrices(String[] fields) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        vehicle.setSourceToSales();
        price.setSourceToSales();
        try {
            Date parse = sdf.parse(fields[5]);
            int elapsedDays = 0;
            long elapsed = System.currentTimeMillis() - parse.getTime();
            if (elapsed > 0) {
                int ONE_DAY = 24 * 3600 * 1000;
                elapsedDays = (int) ((elapsed - 1) / ONE_DAY + 1);
            }
            price.setElapsed(elapsedDays);
            price.setSalePrice(Double.parseDouble(fields[6]));
        } catch (ParseException e) {
            throw new TrueDataException(DataError.SalesDateError, e);
        } catch (NumberFormatException nfe) {
            throw new TrueDataException(DataError.SalesPriceError, nfe);
        }
    }


    @Override
    protected int getNumberOfFields() {
        return 8;
    }
}
