/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageingredients;

import service.IngredientService;

/**
 *
 * @author Do Dat
 */
public class ProcessManageIngredient {
    private final IngredientService is = new IngredientService();
    
    /**
     * Đây là chỗ dùng để xử lí phần Manage Ingredient
     */
    public void processManageIngredient() {
        String[] options = {
            "Add An Ingredient",
            "Update Ingredient",
            "Delete Ingredient",
            "Show All Ingredient",
            "Exit"
        };
        int choice;
        do {            
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-                            MANAGE INGREDIENT                          -");
            System.out.println("-------------------------------------------------------------------------");
            choice = Menu.getChoice(options);
            switch(choice) {
                case 1:
                    is.addIngredient();
                    break;
                case 2:                   
                    is.updateIngredient();
                    break;
                case 3:
                    is.deleteIngredient();
                    break;
                case 4:
                    is.showAll();
                    break;
                case 5:
                    System.out.println("GOOD BYE!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 5);
    }
    
    public IngredientService getIngredientService() {
        return is;
    }
    
}
