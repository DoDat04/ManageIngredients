/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import check_data.Tools;
import data.BeverageData;
import data.IngredientData;
import data_layer.IngredientsFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import manageingredients.Menu;

/**
 *
 * @author Do Dat
 */
public class BeverageRecipeService implements BeverageRecipeServiceable{
    //Tất cả các đối tượng của lớp đều chia sẻ cùng một bản sao của beverageMenu
    private static final Map<String, BeverageData> beverageMenu = new HashMap<>();
    //mỗi đối tượng của lớp sẽ có một bản sao riêng của các biến này
    private final Map<String, IngredientData> ingredientMap = IngredientService.getIngredientMap();
    private static final Map<String, Double> ingredients = new HashMap<>();
    private final IngredientsFile ia = new IngredientsFile();
    private final String BEVERAGES_FILE_NAME = "Menu.dat";
    
    /**
     * Menu để lựa chọn thêm đồ uống hoặc thoát ra
     */
    String[] drinkOptions = {
        "Add New Drink",
        "Exit"
    }; 
    
    /**
     * Menu để lựa chọn cập nhật
     */
    String[] updateOptions = {
        "Name",
        "Ingredient",
        "Exit"
    };  

    public BeverageRecipeService() {
        try {
            ia.loadDataFromFile((HashMap<String, BeverageData>) beverageMenu, BEVERAGES_FILE_NAME);
            System.out.println("\u001B[32mLoaded data from Menu.dat successfully!!!\u001B[0m");
        } catch (Exception e) {
            System.out.println("There are no beverage recipes!!!");
        }
    }
    
    /**
     * Phương thức dùng để hiển thị nguyên liệu trong ingredientMap
     */
    private void showListOfIngredients() {
        System.out.printf("|%-7s|%-15s|%-10s|%-10s|%-9s|\n","Code","Name","Quantity","Price","Available");
        for (IngredientData ingredient : ingredientMap.values()) {
                System.out.println(ingredient.toString());
        }
    }
    
    /**
     * Phương thức dùng để lấy giá trị nhập của người dùng, nếu là rỗng thì trả về giá trị mặc định
     * @param msg
     * @param defaultValue
     * @return 
     */
    private Double getDoubleInputWithDefault(String msg, double defaultValue) {
        Double input = Tools.inputNumber(msg);
        return input != null ? input : defaultValue;
    }
    
    /**
     * Phương thức dùng để thêm nguyên liệu cho công thức đồ uống
     * @return 
     */
    private Map<String, Double> addIngredientsToDrink() {  
        Map<String, Double> ingredientsOfDrink = new HashMap<>();
        String ingredientCode;
        do {
            do {                
                ingredientCode = Tools.inputCharacter("Enter the code of ingredient for drink: ", "TP\\d{3}");
                if (!ingredientMap.containsKey(ingredientCode)) {
                    System.out.println("Ingredient with " + ingredientCode + " has not exist. Please choose another one!!!");
                } else if (ingredientsOfDrink.containsKey(ingredientCode)) {
                    System.out.println("Ingredient with " + ingredientCode + " has been used for this drink. Please choose another one!!!");
                } 
            } while (!ingredientMap.containsKey(ingredientCode) || ingredientsOfDrink.containsKey(ingredientCode));                        
            String ingredientName = Tools.inputCharacter("Enter the name of ingredient for drink: ", "\\D.*+");
            Double ingredientQuantity = getDoubleInputWithDefault("Enter the quantity of ingredient: ",0.0);
            ingredientsOfDrink.put(ingredientCode, ingredientQuantity);
        } while (Tools.inputYN("Do you want to add more ingredients? (Y/N): "));

        return ingredientsOfDrink;
    }

    /**
     * Phương thức dùng để thêm tên mã và nguyên liệu đồ uống vào menu
     */
    private void addNewDrink() {
        String drinkCode;
        do {
            drinkCode = Tools.inputCharacter("Enter the code of drink: ", "DR\\d{3}");
            if (beverageMenu.containsKey(drinkCode)) {
                System.out.println("Drink with code " + drinkCode + " already exists. Please enter again!!!");
            }
        } while (beverageMenu.containsKey(drinkCode));

        String drinkName = Tools.inputCharacter("Enter the name of drink: ", "\\D.*");
        
        // Hiển thị các nguyên liệu đang có sẵn
        System.out.println("HERE IS THE LIST OF INGREDIENTS IN COFFEE STORE!!!");
        showListOfIngredients();       

        Map<String, Double> ingredientsOfDrink = addIngredientsToDrink();
        
        BeverageData newDrink = new BeverageData(drinkCode, drinkName, ingredientsOfDrink);
        beverageMenu.put(drinkCode, newDrink);
        System.out.println("Added new drink successfully!!!");
    }
    
    /**
     * Phương thức này để người dùng chọn thêm đồ uống hoặc thoát ra ngoài
     */
    @Override
    public void addDrink() {
        int choice;
        do {            
            choice = Menu.getChoice(drinkOptions);
            switch(choice) {
                case 1:
                    addNewDrink();
                    break;
                case 2:
                    System.out.println("Thanks for choosing add new drinks!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 2);           
    }
    
    /**
     * Phương thức cập nhật tên đồ uống trong công thức
     * @param code
     * @return 
     */
    private HashMap<String, BeverageData> updateName(String code) {
        BeverageData drinkToUpdate = beverageMenu.get(code);
        String updateName = Tools.inputCharacter("Enter the new name of the drink: ", "\\D.*");
        if (!updateName.isEmpty()) {
            drinkToUpdate.setDrinkName(updateName);
            System.out.println("Updated name successfully!!!");
        }
        return (HashMap<String, BeverageData>) beverageMenu;
    }
    
    /**
     * Phương thức cập nhật thành phần đồ uống trong công thức
     * @param code
     * @return 
     */
    private HashMap<String, BeverageData> updateIngredient(String code) {
        BeverageData drinkToUpdate = beverageMenu.get(code);
        Map<String, Double> updatedIngredients = addIngredientsToDrink();

        if (!updatedIngredients.isEmpty()) {
            for (Map.Entry<String, Double> entry : updatedIngredients.entrySet()) {
                String ingredientCode = entry.getKey();
                Double updatedQuantity = entry.getValue();

                updateSingleIngredient(drinkToUpdate, ingredientCode, updatedQuantity);
            }
            System.out.println("Updated Ingredient Successfully!!!");
        }
        return (HashMap<String, BeverageData>) beverageMenu;
    }
    
    /**
     * Phương thức dùng để hỏi người dùng muốn cập nhật đồ uống không khi nhập code
     * @param drinkToUpdate
     * @param ingredientCode
     * @param updatedQuantity 
     */
    private void updateSingleIngredient(BeverageData drinkToUpdate, String ingredientCode, Double updatedQuantity) {
        boolean choice;
        if (drinkToUpdate.getIngredients().containsKey(ingredientCode)) {
            choice = Tools.inputYN("This ingredient already exists in drinks. Do you want to choose another one? ");
            if (!choice) {
                Double currentQuantity = drinkToUpdate.getIngredients().get(ingredientCode);
                if (updatedQuantity < 0) {
                    drinkToUpdate.getIngredients().put(ingredientCode, currentQuantity - updatedQuantity);
                }
                drinkToUpdate.getIngredients().put(ingredientCode, currentQuantity + updatedQuantity);
            } 
        } else {
            drinkToUpdate.getIngredients().put(ingredientCode, updatedQuantity);
        }
    }
    
    /**
     * Phương thức cho người dùng lựa chọn để cập nhật công thức đồ uống
     * @return 
     */
    @Override
    public HashMap<String, BeverageData> updateDrink() {
        String updatedCode = Tools.inputCharacter("Enter the code of drink to update: ", "DR\\d{3}");
        if (!beverageMenu.containsKey(updatedCode)) {
            System.out.println("The drink with code " + updatedCode + " does not exist!!!");
            return (HashMap<String, BeverageData>) beverageMenu;
        }
        int choice;
        do { 
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-                                UPDATE MENU                            -");
            System.out.println("-------------------------------------------------------------------------");
            choice = Menu.getChoice(updateOptions);
            switch(choice) {
                case 1:
                    updateName(updatedCode);
                    break;
                case 2:
                    updateIngredient(updatedCode);
                    break;
                case 3:
                    System.out.println("Thank you for choosing update drinks!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 3);
        return (HashMap<String, BeverageData>) beverageMenu;
    }
    
    /**
     * Phương thức dùng để xóa đồ uống
     * @param deleteCode
     * @return 
     */
    private HashMap<String, BeverageData> deleteDrinkByCode(String deleteCode) {
        boolean choice;
        if (!beverageMenu.containsKey(deleteCode)) {
            System.out.println("Drink with code " + deleteCode + " does not exist!!!");
            return (HashMap<String, BeverageData>) beverageMenu;
        }
        
        choice = Tools.inputYN("Do you want to delete the drink (Y/N): ");
        if (choice) {
            beverageMenu.remove(deleteCode);
            System.out.println("Drink with code " + deleteCode + " deleted successfully!!!");
        }
        return (HashMap<String, BeverageData>) beverageMenu;
    }

    /**
     * Phương thức dùng để nhận code của đồ uống rồi xóa
     * @return 
     */
    @Override
    public HashMap<String, BeverageData> deleteDrink() {
        String deleteCode = Tools.inputCharacter("Enter the code of drink to delete: ", "DR\\d{3}");
        deleteDrinkByCode(deleteCode);
        return (HashMap<String, BeverageData>) beverageMenu;
    }
    
    /**
     * Phương thức dùng để hiển thị đồ uống lên màn hình sắp xếp tăng dần theo tên
     */
    @Override
    public void showAll() {
        List<BeverageData> beverageList = new ArrayList<>(beverageMenu.values());
        Collections.sort(beverageList);
        System.out.println("| Code      | Name                | Ingredients");
        for (BeverageData beverage : beverageList) {
            System.out.printf("| %-10s| %-20s| ", beverage.getDrinkCode(), beverage.getDrinkName());
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
     * Phương thức dùng để trả về danh sách đồ uống
     * @return 
     */
    public static Map<String, BeverageData> getBeverageMenu() {
        return beverageMenu;
    }
    
    /**
     * Phương thức dùng để trả về danh sách nguyên liệu đồ uống
     * @return 
     */
    public static Map<String, Double> getIngredients() {
        return ingredients;
    }
}
