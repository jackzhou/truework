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


    public DataParser(String line, String sep, String dateFormat) {
        if (sep != null) {
            this.sep = sep;
        }
        if (dateFormat != null) {
            this.dateFormat = dateFormat;
        }
        String[] fields = line.split(this.sep);
        if (fields.length < getNumberOfFields()) {
            throw new TrueDataException(DataError.DataSizeError);
        }
        vehicle = new Vehicle();
        price = new VehiclePrice();
        initVehicle();
        parseVehicleInfo(fields);
        parsePrices(fields);
    }

    private void parseVehicleInfo(String[] fields) {
        this.vehicle.setMake(fields[0]);
        this.vehicle.setModel(fields[1]);
        this.vehicle.setModelYear(fields[2]);
        this.vehicle.setTrim(fields[3]);
        this.vehicle.setVin(fields[4]);
        this.price.setVin(fields[4]);

    }

    protected abstract void parsePrices(String[] line);
    protected abstract void initVehicle();
    protected abstract int getNumberOfFields();


    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehiclePrice getPrice() {
        return price;
    }

}
