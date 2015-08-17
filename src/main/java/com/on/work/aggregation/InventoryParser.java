package com.on.work.aggregation;

import com.on.work.model.DataError;
import com.on.work.model.TrueDataException;

/**
 * Created by jzhou on 8/16/15.
 */
public class InventoryParser extends DataParser {
    public InventoryParser(String line, String sep, String dateFormat) {
        super(line, sep, dateFormat);
    }

    public InventoryParser(String line) {
        this(line, null, null);
    }

    @Override
    protected void parsePrices(String[] fields) {
        try {
            price.setSellingPrice(Double.parseDouble(fields[6]));
        } catch (NumberFormatException e) {
            throw new TrueDataException(DataError.SellingPriceError, e);
        }
    }

    @Override
    protected void initVehicle() {
        vehicle.setSourceToInventory();
        price.setSourceToInventory();

    }

    @Override
    protected int getNumberOfFields() {
        return 9;
    }
}
