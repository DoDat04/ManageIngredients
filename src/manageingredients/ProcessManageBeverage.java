/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageingredients;


import service.BeverageRecipeService;

/**
 *
 * @author Do Dat
 */
public class ProcessManageBeverage {
    private final BeverageRecipeService brs = new BeverageRecipeService();
    
    public void processManageBeverage() {
        String[] options = {
            "Add The Drink",
            "Update The Drink",
            "Delete The Drink",
            "Show All",
            "Exit"
        };
        
        int choice;
        do {            
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-                             MANAGE BEVERAGE                           -");
            System.out.println("-------------------------------------------------------------------------");
            choice = Menu.getChoice(options);
            switch(choice) {
                case 1:
                    brs.addDrink();
                    break;
                case 2:
                    brs.updateDrink();
                    break;
                case 3:
                    brs.deleteDrink();
                    break;
                case 4:
                    brs.showAll();
                    break;
                case 5:
                    System.out.println("GOOD BYE!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please Choose Again!!!");
            }
        } while (choice != 5);
    }
}
