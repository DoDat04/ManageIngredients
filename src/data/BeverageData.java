/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Do Dat
 */
public class BeverageData implements Serializable, Comparable<BeverageData>{
    private String drinkCode;
    private String drinkName;
    private Map<String, Double> ingredients; // Danh sách nguyên liệu và số lượng

    public BeverageData(String drinkCode, String drinkName, Map<String, Double> ingredients) {
        this.drinkCode = drinkCode;
        this.drinkName = drinkName;
        this.ingredients = ingredients;
    }

    public String getDrinkCode() {
        return drinkCode;
    }

    public void setDrinkCode(String drinkCode) {
        this.drinkCode = drinkCode;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public Map<String, Double> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Double> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return String.format("|%-10s|%-20s|%-15s|", drinkCode,drinkName,ingredients);
    }     

    @Override
    public int compareTo(BeverageData o) {
        return this.getDrinkName().compareTo(o.getDrinkName());
    }

}
