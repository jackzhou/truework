package com.on.work.aggregation;

/**
 * Created by jzhou on 8/16/15.
 */
class InventoryMapper extends AbstractDataMapper{

    @Override
    protected DataParser createParser(String line) {
        return new InventoryParser(line);
    }
}
