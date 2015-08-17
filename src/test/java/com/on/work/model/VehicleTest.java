package com.on.work.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Created by jzhou on 8/16/15.
 */
public class VehicleTest {
    Vehicle f1;
    Vehicle f2;
    Vehicle f3;
    Vehicle t1;
    Vehicle n;

    @Before
    public void setUp() throws Exception{
        f1 = new Vehicle("Ford", "F150", "2015", "XL", "vinnumber1", false);
        f2 = new Vehicle("Ford", "F150", "2015", "XL", "vinnumber1", true);
        f3 = new Vehicle("Ford", "F150", "2015", "XL", "vinnumber1", true);
        t1 = new Vehicle("Toyota","Lexus"," 2014", "GS", "whatever", false);
        n = new Vehicle("Toyota","Lexus"," 2014", "" , "whatever", false);

    }

    @Test
    public void testCompareTo() throws Exception {
        Assert.assertTrue(f1.compareTo(f2) < 0);
        Assert.assertTrue(f2.compareTo(f3) == 0);
        Assert.assertTrue(f3.compareTo(t1) < 0);
    }

    @Test
    public void testEquals() throws Exception {
        Assert.assertEquals(f2, f3);
        Assert.assertNotEquals(f1, f2);
        Assert.assertTrue(f1.isFromSales());
        Assert.assertTrue(f2.isFromInventory());
        Assert.assertTrue(!f1.isFromInventory());
        Assert.assertTrue(!f2.isFromSales());

    }

    @Test
    public void testRoundTrip() throws Exception {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutput out = new DataOutputStream(bo);
        t1.write(out);
        Vehicle v = new  Vehicle();
        v.readFields(new DataInputStream(new ByteArrayInputStream(bo.toByteArray())));
        Assert.assertEquals(t1, v);

    }

}