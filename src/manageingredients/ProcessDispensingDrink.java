/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageingredients;

import service.DispensingBeverageService;

/**
 *
 * @author Do Dat
 */
public class ProcessDispensingDrink {
    private final DispensingBeverageService dbs = new DispensingBeverageService();   
    
    public void processDispensingDrink() {
        String[] options = {
            "Dispensing The Drink",
            "Update The Dispensing Drink",           
            "Exit"
        };
        int choice;
        do {            
            choice = Menu.getChoice(options);
            switch(choice) {
                case 1:
                    dbs.dispensingDrink();
                    break;
                case 2:
                    dbs.updateDispensingDrink();
                    break;
                case 3:
                    System.out.println("Thank you for choosing dispensing drink!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 3);
    }
}
