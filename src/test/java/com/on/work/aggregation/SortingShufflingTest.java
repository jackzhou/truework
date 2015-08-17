package com.on.work.aggregation;

import com.on.work.model.Vehicle;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jzhou on 8/16/15.
 */
public class SortingShufflingTest {
    Vehicle v1 = new Vehicle("Ford", "F-150", "2015", "XL","adfasdfa", true);
    Vehicle v2 = new Vehicle("Ford", "F-150", "2015", "XL","adfasdfa", false);
    Vehicle v3 = new Vehicle("Ford", "F-150", "2015", "LE","adfasdfa", false);
    @Test
    public void testGetPartition() throws Exception {

        TrueReportDriver.TrimPartitioner part = new TrueReportDriver.TrimPartitioner();
        Assert.assertEquals(part.getPartition(v1, null, 100), part.getPartition(v2, null, 100));
        Assert.assertNotEquals(part.getPartition(v1, null, 100), part.getPartition(v3, null, 100));

    }

    @Test
    public void testGroupComparator() throws Exception {
        TrueReportDriver.TrimComparator comparator = new TrueReportDriver.TrimComparator();
        Assert.assertTrue(comparator.compare(v1, v2) == 0);
        Assert.assertTrue(comparator.compare(v2, v3) > 0);
    }


}