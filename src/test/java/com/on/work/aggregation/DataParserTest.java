package com.on.work.aggregation;

import com.on.work.model.DataError;
import com.on.work.model.TrueDataException;
import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jzhou on 8/16/15.
 */
public class DataParserTest {

    String invLine = "Ford\tF-150\t2011\tXL\t3bcdefa\tBuild Touch\t25003\t25000\t3";
    String salesLine = "TOYOTA\tRAV4\t2015\tLE\t8tttt\t2015-08-10\t88001.3\t8\n";

    @Test
    public void testParseSellingPrices() throws Exception {
        DataParser parser = new InventoryParser(invLine);
        Vehicle v = new Vehicle("Ford", "F-150", "2011", "XL", "3bcdefa",true);
        Assert.assertEquals("3bcdefa", v.getVin().toString());
        Assert.assertEquals(v.getSource().toString(), Vehicle.INVENTORY);
        VehiclePrice price = new VehiclePrice(25003,"3bcdefa",true );

        Assert.assertEquals(v, parser.getVehicle());
        Assert.assertEquals(price, parser.getPrice());
        parser = new InventoryParser(invLine.replaceAll("\t", ","), ",", null);
        Assert.assertEquals(v, parser.getVehicle());
        Assert.assertEquals(price, parser.getPrice());

    }
    @Test
    public void testParseSalesData() throws Exception {
        DataParser parser = new SalesParser(salesLine);
        Vehicle v = new Vehicle("TOYOTA", "RAV4", "2015", "LE", "8tttt",false);
        Assert.assertEquals("8tttt", v.getVin().toString());
        Assert.assertEquals(v.getSource().toString(), Vehicle.SALES);

        VehiclePrice price = new VehiclePrice(88001.3d, 7,"8tttt", false);

        Assert.assertEquals(v, parser.getVehicle());
        Assert.assertEquals(price, parser.getPrice());
        parser = new SalesParser(salesLine.replaceAll("\t", ","), ",", null);
        Assert.assertEquals(v, parser.getVehicle());
        Assert.assertEquals(price, parser.getPrice());
    }

    @Test(expected = TrueDataException.class)
    public void testShorterLine() throws Exception {
        new SalesParser("a\t");
    }

    public void testNumberFormatError() throws Exception {
        String line = "TOYOTA\tRAV4\t2015\tLE\\t2015-08-10\t8tttt\t88001.3\t8\n";
        try {
            new SalesParser(line);
        } catch(TrueDataException te) {
            Assert.assertTrue(te.error == DataError.SalesPriceError);
        }

    }


}