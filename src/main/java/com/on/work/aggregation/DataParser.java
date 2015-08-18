package com.on.work.aggregation;

import com.on.work.model.DataError;
import com.on.work.model.TrueDataException;
import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;

/**
 * Created by jzhou on 8/16/15.
 */
public abstract class DataParser {
    protected Vehicle vehicle;
    protected VehiclePrice price;
    protected String sep="\t";
    protected String dateFormat = "yyyy-MM-dd";


    public DataParser(Vehicle vehicle, VehiclePrice price, String sep, String dateFormat) {
        this.vehicle = vehicle;
        this.price = price;
        if (sep != null) {
            this.sep = sep;
        }
        if (dateFormat != null) {
            this.dateFormat = dateFormat;
        }
    }

    public void parse(String line) {
        String[] fields = line.split(this.sep);
        if (fields.length < getNumberOfFields()) {
            throw new TrueDataException(DataError.DataSizeError);
        }

        this.vehicle.setMake(fields[0]);
        this.vehicle.setModel(fields[1]);
        this.vehicle.setModelYear(fields[2]);
        this.vehicle.setTrim(fields[3]);
        this.vehicle.setVin(fields[4]);
        this.price.setVin(fields[4]);
        parsePrices(fields);
    }


    protected abstract void parsePrices(String[] line);
    protected abstract int getNumberOfFields();


    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehiclePrice getPrice() {
        return price;
    }

}
