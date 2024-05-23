/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.IngredientData;
import java.util.HashMap;

/**
 *
 * @author Do Dat
 */
public interface IngredientServiceable {
    public void addIngredient();
    public HashMap<String, IngredientData> updateIngredient();
    public HashMap<String, IngredientData> deleteIngredient();
    public void showAll();
}
