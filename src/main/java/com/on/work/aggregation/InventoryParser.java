package com.on.work.aggregation;

import com.on.work.model.DataError;
import com.on.work.model.TrueDataException;
import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;

/**
 * Created by jzhou on 8/16/15.
 */
public class InventoryParser extends DataParser {
    public InventoryParser(Vehicle vehicle, VehiclePrice price, String sep, String dateFormat) {
        super(vehicle, price, sep, dateFormat);
    }

    public InventoryParser(Vehicle vehicle, VehiclePrice price) {
        this(vehicle, price, null, null);
    }

    @Override
    protected void parsePrices(String[] fields) {
        try {
            vehicle.setSourceToInventory();
            price.setSourceToInventory();
            price.setSellingPrice(Double.parseDouble(fields[6]));
        } catch (NumberFormatException e) {
            throw new TrueDataException(DataError.SellingPriceError, e);
        }
    }

    @Override
    protected int getNumberOfFields() {
        return 9;
    }
}
