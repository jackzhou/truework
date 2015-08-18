package com.on.work;

import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jzhou on 8/17/15.
 */
public class ComparatorUtilsTest {

    @Test
    public void testCompareTextArray() throws Exception {
        byte[] b1 = serialize(new String[] {"true", "car"});
        byte[] b2= serialize(new String[] {"true", "food"});

        Assert.assertTrue(ComparatorUtils.compareTextArray(b2,0, b1, 0, 2) > 0);
        Assert.assertTrue(ComparatorUtils.compareTextArray(b1,0, b2, 0, 2) < 0);
        Assert.assertTrue(ComparatorUtils.compareTextArray(b1,0, b2, 0, 1) == 0);

    }

    private byte[] serialize(String[] a) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bo);
        for (String s: a) {
            new Text(s).write(output);
        }
        return bo.toByteArray();
    }
}