/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.BeverageData;
import java.util.HashMap;

/**
 *
 * @author Do Dat
 */
public interface BeverageRecipeServiceable {
    public void addDrink();
    public HashMap<String, BeverageData> updateDrink();
    public HashMap<String, BeverageData> deleteDrink();
    public void showAll();
}
