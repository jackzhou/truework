package com.on.work.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by jzhou on 8/16/15.
 */
public class VehiclePriceTest  {
    private VehiclePrice sellingPrice;
    private VehiclePrice salePrice;

    @Before
    public void setUp() {
        sellingPrice = new VehiclePrice(120000.39d, "vin", false);
        salePrice = new VehiclePrice(110000d, 45, "vin", true);
    }


    @Test
    public void testRoundTrip() throws Exception {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bo);
        salePrice.write(out);
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bo.toByteArray()));
        VehiclePrice v = new VehiclePrice();
        v.readFields(in);
        Assert.assertEquals(v, salePrice);
        v = new VehiclePrice();
        v.setSellingPrice(120000.39d);
        Assert.assertEquals(v, sellingPrice);


    }

    @Test
    public void testSetSellingPrice() throws Exception {
        VehiclePrice v = new VehiclePrice();
        v.setSellingPrice(120000.39d);
        Assert.assertEquals(v, sellingPrice);
    }

    @Test
    public void testSetSalePrice() throws Exception {
        sellingPrice.setElapsed(45);
        sellingPrice.setSalePrice(110000d);
        Assert.assertEquals(salePrice, sellingPrice);

    }


}