package com.on.work.model;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Vehicle record basic  info for a vehicle and flag source  indicating where the info
 * is retrieved.
 */
public class Vehicle implements WritableComparable<Vehicle> {
    public static final String BLANK = "_";
    public static final String SALES = "s";
    public static final String INVENTORY = "t";
    private Text make = new Text();
    private Text model = new Text();

    private Text modelYear = new Text();
    private Text trim = new Text();
    private Text vin = new Text();
    private Text source = new Text();

    public Vehicle() {

    }

    public Vehicle(String make, String model, String modelYear, String trim, String vin, boolean fromInv) {
        setMake(make);
        setModel(model);
        setModelYear(modelYear);
        setTrim(trim);
        setVin(vin);
        source.set(fromInv ? INVENTORY : SALES);
    }

    @Override
    public int compareTo(Vehicle that) {
        int r = this.make.compareTo(that.make);
        r = r != 0 ? r: this.model.compareTo(that.model);
        r = r != 0 ? r: this.modelYear.compareTo(that.modelYear);
        r = r != 0 ? r: this.trim.compareTo(that.trim);
        r = r != 0 ? r: this.vin.compareTo(that.vin);
        r = r != 0 ? r: this.source.compareTo(that.source);
        return r;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (!make.equals(vehicle.make)) return false;
        if (!model.equals(vehicle.model)) return false;
        if (!modelYear.equals(vehicle.modelYear)) return false;
        if (!trim.equals(vehicle.trim)) return false;
        if (!vin.equals(vehicle.vin)) return false;
        return source.equals(vehicle.source);

    }

    @Override
    public int hashCode() {
        int result = make.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + modelYear.hashCode();
        result = 31 * result + trim.hashCode();
        result = 31 * result + vin.hashCode();
        result = 31 * result + source.hashCode();
        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        getMake().write(out);
        getModel().write(out);
        getModelYear().write(out);

        getTrim().write(out);
        getVin().write(out);
        getSource().write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        getMake().readFields(in);
        getModel().readFields(in);
        getModelYear().readFields(in);
        getTrim().readFields(in);
        getVin().readFields(in);
        getSource().readFields(in);
    }


    public Text getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make.set(make);
    }

    public Text getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public Text getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear.set(modelYear);
    }

    public Text getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim.set(trim);
    }

    public Text getVin() {
        return vin;
    }

    public void setVin(String  vin) {
        this.vin.set(vin);
    }

    public Text getSource() {
        return source;
    }

    public void setSourceToSales() {
        this.source.set(SALES);
    }

    public void setSourceToInventory() {
        this.source.set(INVENTORY);
    }

    public boolean isFromInventory() {
        return INVENTORY.equals(source.toString());
    }
    public boolean isFromSales() {
        return SALES.equals(source.toString());
    }


    @Override
    public String toString() {
        return "Vehicle{" +
                "make=" + make +
                ", model=" + model +
                ", modelYear=" + modelYear +
                ", trim=" + trim +
                ", vin=" + vin +
                ", source=" + source +
                '}';
    }
}
