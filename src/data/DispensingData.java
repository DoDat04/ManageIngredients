/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;

/**
 *
 * @author Do Dat
 */
public class DispensingData implements Serializable, Comparable<DispensingData>{
    private BeverageData beverageData;
    private double dispenseQuantity;

    public DispensingData(BeverageData beverageData, double dispenseQuantity) {
        this.beverageData = beverageData;
        this.dispenseQuantity = dispenseQuantity;
    }

    public BeverageData getBeverageData() {
        return beverageData;
    }

    public void setBeverageData(BeverageData beverageData) {
        this.beverageData = beverageData;
    }

    public double getDispenseQuantity() {
        return dispenseQuantity;
    }

    public void setDispenseQuantity(double dispenseQuantity) {
        this.dispenseQuantity = dispenseQuantity;
    }

    @Override
    public String toString() {
        return String.format("|%-10s|%-20s|%-8s|", beverageData.getDrinkCode(), beverageData.getDrinkName(), dispenseQuantity);
    }

    @Override
    public int compareTo(DispensingData o) {
        return beverageData.getDrinkName().compareTo(o.getBeverageData().getDrinkName());
    }
    
}
