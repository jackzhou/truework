package com.on.work;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;

import java.io.IOException;

/**
 * Created by jzhou on 8/17/15.
 */
public abstract class ComparatorUtils {
    /**
   .
     * @param b
     * @param s
     * @param numTexts
     * @return
     */
    /**
     *  given two byte array <doe>b1</doe> and <code>b2</code>.   Staring at <code><i1 (i2)/code>,
     *  the sub array of b1 (b2) is the concatenation of at least <code>size</code>
     *  many byte arrays that are the deserialized from some Text objects. This method compare,
     *  these two array of Text objects in the Dictionary order
     * @param b1 first byte array
     * @param i1 starting index of subarray for b1
     * @param b2 second byte array
     * @param i2 starting index of sub array of b2
     * @param size number of Text object involved
     * @return
     */
    public static int compareTextArray(byte[] b1, int i1,
                                       byte[] b2, int i2, int size) throws IOException {
        Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
        int start1 = i1;
        int start2 = i2;
        for (int i = 0; i < size; i++) {
            int l1 = WritableUtils.decodeVIntSize(b1[start1]) + WritableComparator.readVInt(b1, start1);
            int l2 = WritableUtils.decodeVIntSize(b2[start2]) + WritableComparator.readVInt(b2, start2);
            int c = TEXT_COMPARATOR.compare(b1, start1, l1,
                    b2, start2, l2);
            if (c !=0) {
                return c;
            }
            start1 += l1;
            start2 += l2;
        }
        return 0;
    }
}
