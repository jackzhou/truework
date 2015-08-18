package com.on.work.model;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Wrap a Vehicle's selling price from inventory data and sales info in sales data.
 * However, only one set of info is wrapped.
 */
public class VehiclePrice implements Writable {

    private Text vin = new Text();
    private Text source = new Text();
    private DoubleWritable sellingPrice = new DoubleWritable(-1.0d);
    private DoubleWritable salePrice = new DoubleWritable(-1.0d);
    private IntWritable elapsed = new IntWritable((-1));


    public VehiclePrice() {
    }

    public VehiclePrice(double sellingPrice, String vin, boolean fromInv) {
        this.getVin().set(vin);
        this.setSource(fromInv ? Vehicle.INVENTORY : Vehicle.SALES);
        this.getSellingPrice().set(sellingPrice);

    }

    public VehiclePrice(double salePrice, int elapsed, String vin, boolean fromInv) {
        this.getVin().set(vin);
        this.setSource(fromInv ? Vehicle.INVENTORY: Vehicle.SALES);
        this.getSalePrice().set(salePrice);
        this.getElapsed().set(elapsed);
    }


    @Override
    public void write(DataOutput out) throws IOException {
        getVin().write(out);
        getSource().write(out);
        getSellingPrice().write(out);
        getSalePrice().write(out);
        getElapsed().write(out);

    }

    @Override
    public void readFields(DataInput in) throws IOException {
        getVin().readFields(in);
        getSource().readFields(in);
        getSellingPrice().readFields(in);
        getSalePrice().readFields(in);
        getElapsed().readFields(in);
    }


    /**
     * Negavie values  indicate not info does not apply
     */
    public DoubleWritable getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice.set(sellingPrice);
        this.salePrice.set(-1.0d);
        this.elapsed.set(-1);
    }

    public DoubleWritable getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice.set(salePrice);
        this.sellingPrice.set(-1.0d);
    }

    public IntWritable getElapsed() {
        return elapsed;
    }

    public void setElapsed(int elapsed) {
        this.elapsed.set(elapsed);
        this.sellingPrice.set(-1.0d);
    }

    public void setSourceToInventory() {
        this.source.set(Vehicle.INVENTORY);
    }
    public void setSourceToSales() {
        this.source.set(Vehicle.SALES);
    }

    public boolean isFromInventory() {
        return Vehicle.INVENTORY.equals(source.toString());
    }

    public boolean isFromSales() {
        return Vehicle.SALES.equals(source.toString());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehiclePrice that = (VehiclePrice) o;

        if (vin != null ? !vin.equals(that.vin) : that.vin != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (sellingPrice != null ? !sellingPrice.equals(that.sellingPrice) : that.sellingPrice != null) return false;
        if (salePrice != null ? !salePrice.equals(that.salePrice) : that.salePrice != null) return false;
        return !(elapsed != null ? !elapsed.equals(that.elapsed) : that.elapsed != null);

    }

    @Override
    public int hashCode() {
        int result = vin != null ? vin.hashCode() : 0;
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (sellingPrice != null ? sellingPrice.hashCode() : 0);
        result = 31 * result + (salePrice != null ? salePrice.hashCode() : 0);
        result = 31 * result + (elapsed != null ? elapsed.hashCode() : 0);
        return result;
    }

    public Text getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin.set(vin);
    }

    public Text getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    @Override
    public String toString() {
        return "VehiclePrice{" +
                "vin=" + vin +
                ", source=" + source +
                ", sellingPrice=" + sellingPrice +
                ", salePrice=" + salePrice +
                ", elapsed=" + elapsed +
                '}';
    }
}
