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
public class IngredientData implements Serializable, Comparable<IngredientData>{
    private String ingredientCode;
    private String ingredientName;
    private double quantity;
    private double price;
    private boolean available;

    public IngredientData() {
    }

    public IngredientData(String ingredientCode, String ingredientName, double quantity, double price, boolean available) {
        this.ingredientCode = ingredientCode;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.price = price;
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }   

    public String getIngredientCode() {
        return ingredientCode;
    }

    public void setIngredientCode(String ingredientCode) {
        this.ingredientCode = ingredientCode;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("|%-7s|%-15s|%-10s|%-10s|%-9s|", ingredientCode,ingredientName,quantity,price,available?"yes":"no");
    }  

    @Override
    public int compareTo(IngredientData o) {
        return this.getIngredientName().compareTo(o.getIngredientName());
    }
}

