/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import check_data.Tools;
import data.IngredientData;
import data_layer.FileManager;
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
public class IngredientService implements IngredientServiceable{
    private static final Map<String, IngredientData> ingredientMap = new HashMap<>();
    private final IngredientsFile ia = new IngredientsFile();
    private final String INGREDIENTS_FILE_NAME = "Ingredients.dat";
    
    /**
     * Menu để lựa chọn quản lí nguyên liệu
     */
    String[] ingredientOptions = {
        "Add New Ingredient",
        "Exit"
    };
    /**
     * Menu để lựa chọn các update nguyên liệu
     */
    String[] updateOptions = {
        "Ingredient Name",
        "Ingredient Quantity",
        "Ingredient Price",
        "Exit"
    };  

    public IngredientService() {
        try {
            ia.loadDataFromFile((HashMap<String, IngredientData>) ingredientMap, INGREDIENTS_FILE_NAME);
            System.out.println("\u001B[32mLoaded data from Ingredients.dat successfully!!!\u001B[0m");
        } catch (Exception e) {
            System.out.println("There are no ingredients!!!");
        }
    }
   
    
    /**
     * Phương thức dùng để kiểm tra xem map có rỗng hay không
     * @return 
     */
    private boolean isIngredientMapEmpty() {
        return ingredientMap.isEmpty();
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
     * Phương thức dùng để thêm nguyên liệu vô hashmap
     */
    private void addSingleIngredient() {
        String ingredientCode;
        do {            
            ingredientCode = Tools.inputCharacter("Enter the code of ingredient: ", "TP\\d{3}");
            if (ingredientMap.containsKey(ingredientCode)) {
                System.out.println("Ingredient with code " + ingredientCode + " already exists. Please enter again!!!");
            }
        } while (ingredientMap.containsKey(ingredientCode));
        
        String ingredientName = Tools.inputCharacter("Enter the name of ingredient: ", "\\D.*");
        Double ingredientQuantity = getDoubleInputWithDefault("Enter the quantity of ingredient: ",0.0);
        Double ingredientPrice = getDoubleInputWithDefault("Enter the price of ingredient: ",0.0);
        boolean ingredientAvailable = Tools.inputYN("Is the ingredient available (Y/N): ");                  
        IngredientData newIngredient = new IngredientData(ingredientCode, ingredientName, ingredientQuantity, ingredientPrice, ingredientAvailable);
        ingredientMap.put(ingredientCode, newIngredient);
        System.out.println("Ingredient added successfully!");
    }
    
    /**
     * Phương thức để người dùng được quyền chọn thêm nguyên liệu hoặc thoát ra khỏi menu
     */
    @Override
    public void addIngredient() {
        int choice;
        do {            
            choice = Menu.getChoice(ingredientOptions);
            switch(choice) {
                case 1:
                    addSingleIngredient();
                    break;
                case 2:
                    System.out.println("Thanks for choosing add ingredients!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 2);
        
    }
    
    /**
     * Phương thức dùng để update tên nguyên liệu
     * @param code
     * @return 
     */
    private HashMap<String, IngredientData> updateName(String code) {
        IngredientData ingredientToUpdate = ingredientMap.get(code);
        String newIngredientName = Tools.inputCharacter("Enter new name of ingredient: ", "\\D.*");
        if (newIngredientName != null && !newIngredientName.isEmpty()) {
            ingredientToUpdate.setIngredientName(newIngredientName);
            System.out.println("Ingredient name updated successfully!!!");
        }
        return (HashMap<String, IngredientData>) ingredientMap;
    }
    
    /**
     * Phương thức dùng để update số lượng nguyên liệu
     * @param code
     * @return 
     */
    private HashMap<String, IngredientData> updateQuantity(String code) {
        IngredientData ingredientToUpdate = ingredientMap.get(code);
        Double newIngredientQuantity = Tools.inputNumber("Enter the quantity of ingredient: ");
        if (newIngredientQuantity != null) {
            ingredientToUpdate.setQuantity(newIngredientQuantity);
            System.out.println("Ingredient quantity updated successfully!!!");
            
            if (newIngredientQuantity == 0.0) {
                ingredientToUpdate.setAvailable(false);
            } else {
                ingredientToUpdate.setAvailable(true);
            }
        }
        return (HashMap<String, IngredientData>) ingredientMap;
    }
    
    /**
     * Phương thức dùng để update giá nguyên liệu
     * @param code
     * @return 
     */
    private HashMap<String, IngredientData> updatePrice(String code) {
        IngredientData ingredientToUpdate = ingredientMap.get(code);
        Double newIngredientPrice = Tools.inputNumber("Enter new price of ingredient: ");
        if (newIngredientPrice != null) {
            ingredientToUpdate.setPrice(newIngredientPrice);
            System.out.println("Ingredient price updated successfully!!!");
        }
        return (HashMap<String, IngredientData>) ingredientMap;
    }
    
    /**
     * Phương thức dùng để update nguyên liệu
     * @return 
     */
    @Override
    public HashMap<String, IngredientData> updateIngredient() {               
        String updateCode = Tools.inputCharacter("Enter the code of ingredient to update: ", "TP\\d{3}");
        if (!ingredientMap.containsKey(updateCode)) {
            System.out.println("The ingredient does not exist!!!");
            return (HashMap<String, IngredientData>) ingredientMap;
        }
        int choice;
        do {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-                                UPDATE MENU                            -");
            System.out.println("-------------------------------------------------------------------------");            
            choice = Menu.getChoice(updateOptions);
            switch(choice) {
                case 1:
                    updateName(updateCode);
                    break;
                case 2:
                    updateQuantity(updateCode);
                    break;
                case 3:
                    updatePrice(updateCode);
                    break;
                case 4:
                    System.out.println("Thank you for choosing update!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 4);
        return (HashMap<String, IngredientData>) ingredientMap;
    }
    
    /**
     * Phương thức này nhận code của nguyên liệu muốn xóa xong rồi xóa
     * @param code
     * @return 
     */
    private HashMap<String, IngredientData> deleteIngredientByCode(String deleteCode) {               
        boolean choice;
                      
        choice = Tools.inputYN("Do you want to delete the ingredient (Y/N): ");
        
        if (choice) {
            ingredientMap.remove(deleteCode);
            System.out.println("Ingredient with code " + deleteCode + " deleted successfully!!!");
        }
        return (HashMap<String, IngredientData>) ingredientMap;
    }    
    
    /**
     * Phương thức dùng để nhập code nguyên liệu rồi xóa nguyên liệu
     * @return 
     */
    @Override
    public HashMap<String, IngredientData> deleteIngredient() {       
        String deleteCode = Tools.inputCharacter("Enter the code of ingredient to delete: ", "TP\\d{3}");       
        deleteIngredientByCode(deleteCode);
        return (HashMap<String, IngredientData>) ingredientMap;
    }
    
    /**
     * Phương thức dùng để hiển thị các nguyên liệu ra màn hình
     */
    @Override
    public void showAll() {       
        List<IngredientData> ingredientList = new ArrayList<>(ingredientMap.values());
        Collections.sort(ingredientList);
        System.out.printf("|%-7s|%-15s|%-10s|%-10s|%-9s|\n","Code","Name","Quantity","Price","Available");
        for (IngredientData ingredient : ingredientList) {
            System.out.println(ingredient.toString());
        }
    }   
    
    public static Map<String, IngredientData> getIngredientMap() {
        return ingredientMap;
    }
}
