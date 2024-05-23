/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.BeverageData;
import data.DispensingData;
import data.IngredientData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Do Dat
 */
public class ReportService implements ReportServiceable{
    private final Map<String, BeverageData> beverageMenu = BeverageRecipeService.getBeverageMenu();
    private final Map<String, IngredientData> ingredientMap = IngredientService.getIngredientMap();
    private final Map<String, DispensingData> dispensing = DispensingBeverageService.getDispensing();
    
    /**
     * Phương thức dùng để kiểm tra các nguyên liệu còn available thì in ra
     */
    @Override
    public void checkIngredientsAvailable() {
        System.out.println("Available Ingredients:");
        System.out.printf("|%-7s|%-15s|%-10s|%-10s|%-9s|\n","Code","Name","Quantity","Price","Available");
        for(IngredientData ingredient : ingredientMap.values()) {
            if (ingredient.isAvailable()) {
                System.out.println(ingredient.toString());
            }
        }
    }
    
    /**
     * Phương thức dùng để kiểm tra đồ uống mà có nguyên liệu unavailable
     */
    @Override
    public void checkDrinksOutOfIngredients() { 
        System.out.println("Drinks with Unavailable Ingredients:");
        System.out.println("| Code      | Name                | Ingredients");
        for (BeverageData beverage : beverageMenu.values()) {
            boolean unavailableIngredientFound = false;
            for (Map.Entry<String, Double> entry : beverage.getIngredients().entrySet()) {
                String ingredientCode = entry.getKey();
                if (!ingredientMap.get(ingredientCode).isAvailable()) {
                    unavailableIngredientFound = true;
                    break;
                }                
            }
            if (unavailableIngredientFound) {                              
                System.out.printf("| %-10s| %-20s| ", beverage.getDrinkCode(), beverage.getDrinkName());
                for (Map.Entry<String, Double> entry : beverage.getIngredients().entrySet()) {
                    String ingredientCode = entry.getKey();
                    Double ingredientQuantity = entry.getValue();
                    String ingredientName = ingredientMap.get(ingredientCode).getIngredientName();
                    System.out.printf("%s: %.1f, ", ingredientName, ingredientQuantity);
                }
                System.out.println();
            }
        }
    }
    
    /**
     * Phương thúc dùng để hiển thị các đồ uống đã pha chế
     */
    @Override
    public void showAllDispensingDrink() {
        List<DispensingData> dispensingList = new ArrayList<>(dispensing.values());
        Collections.sort(dispensingList);
        System.out.println("|Code      |Name                |Quantity|");
        for (DispensingData drink : dispensingList) {
            System.out.println(drink.toString());
        }
    }
    
}


