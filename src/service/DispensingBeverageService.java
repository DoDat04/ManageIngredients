/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import check_data.Tools;
import data.BeverageData;
import data.DispensingData;
import data.IngredientData;
import data_layer.IngredientsFile;
import java.util.HashMap;
import java.util.Map;
import manageingredients.Menu;

/**
 *
 * @author Do Dat
 */
public class DispensingBeverageService implements DispensingBeverageServiceable{
    //mỗi đối tượng của lớp sẽ có một bản sao riêng của các biến này 
    private final Map<String, BeverageData> beverageMenu = BeverageRecipeService.getBeverageMenu(); 
    private final Map<String, IngredientData> ingredientMap = IngredientService.getIngredientMap();
    private final Map<String, Double> ingredients = BeverageRecipeService.getIngredients();
    private final Map<String, BeverageData> dispensedDrinks = new HashMap<>();
    //Tất cả các đối tượng của lớp đều chia sẻ cùng một bản sao của dispensing
    private static final Map<String, DispensingData> dispensing = new HashMap<>();
    private final IngredientsFile ia = new IngredientsFile();
    private final String DISPENSING_FILE_NAME = "Order.dat";
    
    String[] dispenseOptions = {
        "Dispensing Drink",
        "Exit"
    };
    
    String[] updateOptions = {
        "Update Quantity",
        "Exit"
    };
    
    /**
     * Phương thức dùng để load data dispensing vô hashmap
     */
    public DispensingBeverageService() {
        try {
            ia.loadDataFromFile((HashMap<String, DispensingData>) dispensing, DISPENSING_FILE_NAME);
            ia.loadDataFromFile((HashMap<String, BeverageData>) dispensedDrinks, DISPENSING_FILE_NAME);
            System.out.println("\u001B[32mLoaded data from Order.dat successfully!!!\u001B[0m");
        } catch (Exception e) {
            System.out.println("There are no dispensed drinks!!!");
        }
    }    
    
    /**
     * Phương thức dùng để hiển thị công thức đồ uống
     */
    private void showRecipeOfDrinks() {
        System.out.println("| Code      | Name           | Ingredients");
        for (BeverageData beverage : beverageMenu.values()) {
            System.out.printf("| %-10s| %-15s| ", beverage.getDrinkCode(), beverage.getDrinkName());
            Map<String, Double> ingredients = beverage.getIngredients();
            for (Map.Entry<String, Double> entry : ingredients.entrySet()) {
                String ingredientCode = entry.getKey();
                Double quantity = entry.getValue();
                String ingredientName = ingredientMap.get(ingredientCode).getIngredientName();
                System.out.printf("%s: %.1f, ", ingredientName, quantity);
            }
            System.out.println();
        }

    }
    
    /**
     * Phương thức dùng để cập nhật lại số lượng nguyên liệu sau khi phân phối đồ uống
     * @param ingredientCode
     * @param quantityDelta 
     */
    private void updateIngredientQuantity(String ingredientCode, double quantityDelta) {
        if (ingredientMap.containsKey(ingredientCode)) {
            IngredientData ingredient = ingredientMap.get(ingredientCode);          
            double currentQuantity = ingredient.getQuantity();
            double newQuantity = currentQuantity - quantityDelta;
            if (!ingredient.isAvailable() || currentQuantity < quantityDelta) {
                System.out.println("Ingredient with code " + ingredientCode + " is not available or not enough!!!");
            } else {
                ingredient.setQuantity(newQuantity);
            }
            if (newQuantity == 0) {
                ingredient.setAvailable(false);
            }
        } else {
            System.out.println("Ingredient with code " + ingredientCode + " does not exist!!!");
        }
    }  
    
    /**
     * Phương thức dùng để phân phối đồ uống
     */
    private void dispensingSingleDrink() {
        String dispensingDrinkCode;

        showRecipeOfDrinks();

        do {           
            dispensingDrinkCode = Tools.inputCharacter("Enter the code of the drink you want to dispense: ", "DR\\d{3}");
        } while (!dispensedDrinks.containsKey(dispensingDrinkCode));

        Double quantityDispense = Tools.inputNumber("Enter the quantity of the dispensing drink: ");

        BeverageData dispensedDrink = beverageMenu.get(dispensingDrinkCode);
        DispensingData dispensingData = new DispensingData(dispensedDrink, quantityDispense);
        
        //Cộng dồn số lượng nếu trùng code
        if (dispensedDrinks.containsKey(dispensingDrinkCode)) {
            DispensingData previousDispensingData = dispensing.get(dispensingDrinkCode);
            double totalQuantity = previousDispensingData.getDispenseQuantity() + quantityDispense;
            dispensingData.setDispenseQuantity(totalQuantity);
        }

        dispensedDrinks.put(dispensingDrinkCode, dispensedDrink);
        dispensing.put(dispensingDrinkCode, dispensingData);

        BeverageData chosenDrink = beverageMenu.get(dispensingDrinkCode);
        for (Map.Entry<String, Double> entry : chosenDrink.getIngredients().entrySet()) {
            String ingredientCode = entry.getKey();
            Double quantityToAdd = entry.getValue() * quantityDispense;
            updateIngredientQuantity(ingredientCode, quantityToAdd);
        }

        System.out.println("Dispensing the drink successfully!!!");
    }
    
    /**
     * Phương thức để người dùng chọn phân phối đồ uống hoặc không
     */
    @Override
    public void dispensingDrink() {       
        int choice;
        do {            
            choice = Menu.getChoice(dispenseOptions);
            switch(choice) {
                case 1:
                    dispensingSingleDrink();
                    break;
                case 2:
                    System.out.println("Thank you for choosing dispensing drink!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 2);

    }
    
    /**
     * Phương thức dùng để cập nhật số lượng đồ uống
     * @param code
     * @return 
     */
    private HashMap<String, DispensingData> updateQuantity(String code) {
        DispensingData chosenDrink = dispensing.get(code); 
        Double newQuantityDispense = Tools.inputNumber("Enter quantity of dispensing drink: ");
        
        BeverageData drink = beverageMenu.get(code);
        for (Map.Entry<String, Double> entry : drink.getIngredients().entrySet()) {
            String ingredientCode = entry.getKey();
            if (chosenDrink.getDispenseQuantity() < newQuantityDispense) {
                Double quantityToAdd = entry.getValue() * (newQuantityDispense - chosenDrink.getDispenseQuantity());
                updateIngredientQuantity(ingredientCode, quantityToAdd);
            } else {
                Double quantityToAdd = entry.getValue() * (newQuantityDispense - chosenDrink.getDispenseQuantity());
                updateIngredientQuantity(ingredientCode, quantityToAdd);
            }
                              
        }
        
        if (newQuantityDispense != null) {
            if (newQuantityDispense < 0 || newQuantityDispense >= 0) {
                chosenDrink.setDispenseQuantity(chosenDrink.getDispenseQuantity() + newQuantityDispense);
            }            
            System.out.println("Quantity of drink updated successfully!!!");
        }
        return (HashMap<String, DispensingData>) dispensing;
    }
    
    /**
     * Phương thức dùng để lựa chọn cập nhật số lượng hoặc không
     */
    @Override
    public void updateDispensingDrink() {
        if (beverageMenu.isEmpty()) {
            System.out.println("The drink does not exist");
            return;
        }              
        
        showDispensedDrinks();
        String drinkCodeToUpdate = Tools.inputCharacter("Enter the code of drink you want to update: ", "DR\\d{3}");
        int choice;
        do {  
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-                                UPDATE MENU                            -");
            System.out.println("-------------------------------------------------------------------------");            
            choice = Menu.getChoice(updateOptions);
            switch(choice) {
                case 1:
                    updateQuantity(drinkCodeToUpdate);
                    break;
                case 2:
                    System.out.println("Thank you for choosing update!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            } 
        } while (choice != 2);  
    }
    
    /**
     * Phương thức dùng để hiển thị các đồ uống đã được pha chế
     */
    private void showDispensedDrinks() {        
        System.out.println("|Code      |Name           |Quantity|");
        for (DispensingData drink : dispensing.values()) {
            System.out.println(drink.toString());
        }
    }  
        
    /**
     * Phương thức dùng để trả về các đồ uống đã pha chế
     * @return 
     */
    public static Map<String, DispensingData> getDispensing() {
        return dispensing;
    }
       
}
