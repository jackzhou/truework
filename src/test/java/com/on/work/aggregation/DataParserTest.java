package com.on.work.aggregation;

import com.on.work.model.DataError;
import com.on.work.model.TrueDataException;
import com.on.work.model.Vehicle;
import com.on.work.model.VehiclePrice;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.http.impl.cookie.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jzhou on 8/16/15.
 */
public class DataParserTest {

    String invLine = "Ford\tF-150\t2011\tXL\t3bcdefa\tBuild Touch\t25003\t25000\t3";
    String salesLine = "TOYOTA\tRAV4\t2015\tLE\t8tttt\t2015-08-10\t88001.3\t8\n";
    Vehicle vehicle = new Vehicle();
    VehiclePrice price = new VehiclePrice();

    @Test
    public void testParseSellingPrices() throws Exception {
        DataParser parser = new InventoryParser(vehicle, price);
        parser.parse(invLine);
        Vehicle v = new Vehicle("Ford", "F-150", "2011", "XL", "3bcdefa",true);
        Assert.assertEquals("3bcdefa", v.getVin().toString());
        Assert.assertEquals(Vehicle.INVENTORY, v.getSource().toString());
        VehiclePrice price = new VehiclePrice(25003,"3bcdefa",true );
        Assert.assertEquals(v, vehicle);
        Assert.assertEquals(price, this.price);
        parser = new InventoryParser(vehicle, this.price, ",", null);
        parser.parse(invLine.replaceAll("\t", ","));
        Assert.assertEquals(v, vehicle);
        Assert.assertEquals(price, parser.getPrice());

    }
    @Test
    public void testParseSalesData() throws Exception {
        new SalesParser(vehicle, price).parse(salesLine);
        Vehicle v = new Vehicle("TOYOTA", "RAV4", "2015", "LE", "8tttt",false);
        Assert.assertEquals("8tttt", v.getVin().toString());
        Assert.assertEquals(v.getSource().toString(), Vehicle.SALES);
        Date d = DateUtils.parseDate("2015-08-10", new String[]{"yyyy-MM-dd"});
        long  days =  1 + (System.currentTimeMillis() - d.getTime() -1)/(3600*24*1000);
        VehiclePrice price = new VehiclePrice(88001.3d, (int)days,"8tttt", false);

        Assert.assertEquals(v, vehicle);
        Assert.assertEquals(price, this.price);
        new SalesParser(vehicle, price, ",", null).parse(salesLine.replaceAll("\t", ","));
        Assert.assertEquals(v, vehicle);
        Assert.assertEquals(price, this.price);
    }

    @Test(expected = TrueDataException.class)
    public void testShorterLine() throws Exception {
        new SalesParser(vehicle, price).parse("a\t");
    }

    public void testNumberFormatError() throws Exception {
        String line = "TOYOTA\tRAV4\t2015\tLE\\t2015-08-10\t8tttt\t88001.3\t8\n";
        try {
            new SalesParser(vehicle, price).parse(line);
        } catch(TrueDataException te) {
            Assert.assertTrue(te.error == DataError.SalesPriceError);
        }

    }


}