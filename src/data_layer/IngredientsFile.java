/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_layer;
import java.util.HashMap;

/**
 *
 * @author Do Dat
 */
public class IngredientsFile<T> {
    private final FileManager fm;

    public IngredientsFile() {
        fm = new FileManager();
    }
    
    public boolean loadDataFromFile(HashMap<String, T> data, String fileName) {
        return fm.loadDataFromFile((HashMap<String, Object>) data, fileName);
    }
    
    public boolean saveIngredientsDataToFile(HashMap<String, T> data, String fileName) {
        return fm.saveDataToFile((HashMap<String, Object>) data, fileName, "Save Ingredients to Ingredients.dat Successfully!!!");
    }
    
    public boolean saveBeveragesDataToFile(HashMap<String, T> data, String fileName) {
        return fm.saveDataToFile((HashMap<String, Object>) data, fileName, "Save Beverages to Menu.dat Successfully!!!");
    }
    
    public boolean saveDispensingDataToFile(HashMap<String, T> data, String fileName) {
        return fm.saveDataToFile((HashMap<String, Object>) data, fileName, "Save Dispense Drink to Order.dat Successfully!!!");
    }
            
}
