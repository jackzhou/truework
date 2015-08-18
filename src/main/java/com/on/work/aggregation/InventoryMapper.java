package com.on.work.aggregation;

import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;

/**
 * Created by jzhou on 8/16/15.
 */
class InventoryMapper extends AbstractDataMapper{

    @Override
    protected DataParser createParser(Vehicle vehicle, VehiclePrice price) {
        return new InventoryParser(vehicle, price);
    }
}
