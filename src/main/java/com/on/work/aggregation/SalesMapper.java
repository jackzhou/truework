package com.on.work.aggregation;


/**
 * Created by jzhou on 8/16/15.
 */
public class SalesMapper  extends AbstractDataMapper {
    @Override
    protected DataParser createParser(String line) {
        return new SalesParser(line);
    }
}
