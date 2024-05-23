/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.BeverageData;
import data.DispensingData;
import data.IngredientData;
import data_layer.IngredientsFile;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Do Dat
 */
public class StoreDataService {
    private final Map<String, IngredientData> ingredientMap = IngredientService.getIngredientMap();
    private final IngredientsFile ia = new IngredientsFile();
    private final String INGREDIENTS_FILE_NAME = "Ingredients.dat";
    private final Map<String, BeverageData> beverageMenu = BeverageRecipeService.getBeverageMenu();
    private final String BEVERAGES_FILE_NAME = "Menu.dat";
    private final Map<String, DispensingData> dispenseDrink = DispensingBeverageService.getDispensing();
    private final String DISPENSING_FILE_NAME = "Order.dat"; 
    
    /**
     * Phương thúc dùng để lưu nguyên liệu vô file
     */
    public void saveIngredientsDataToFile() {
       ia.saveIngredientsDataToFile((HashMap<String, IngredientData>) ingredientMap, INGREDIENTS_FILE_NAME); 
    }
    
    /**
     * Phương thức dùng để lưu công thức vô file
     */
    public void saveBeverageDataToFile() {
        ia.saveBeveragesDataToFile((HashMap<String, BeverageData>) beverageMenu, BEVERAGES_FILE_NAME);
    }
    
    /**
     * Phương thức dùng để lưu đồ uống pha chế vô file
     */
    public void saveDispensingDataToFile() {
        ia.saveDispensingDataToFile((HashMap<String, DispensingData>) dispenseDrink, DISPENSING_FILE_NAME);
        System.out.println("Total dispensed drinks: " + dispenseDrink.size());
    }
    
    public void processStoreData() {
        saveIngredientsDataToFile();
        saveBeverageDataToFile();
        saveDispensingDataToFile();
    }
}
