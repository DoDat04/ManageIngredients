/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageingredients;

import service.StoreDataService;

/**
 *
 * @author Do Dat
 */
public class ManageIngredients {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {                
        ProcessManageIngredient pmi = new ProcessManageIngredient(); 
        ProcessManageBeverage pmb = new ProcessManageBeverage(); 
        ProcessDispensingDrink pdd = new ProcessDispensingDrink(); 
        ProcessReport pr = new ProcessReport();
        StoreDataService sds = new StoreDataService();
        
        String[] options = {
            "Manage Ingredients",
            "Manage beverage receipt",
            "Dispensing beverages",
            "Report",
            "Save data to files",
            "Exit"
        };
        int choice;                
        do {            
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-                            MANAGE COFFEE STORE                        -");
            System.out.println("-------------------------------------------------------------------------");
            choice = Menu.getChoice(options);
            switch(choice) {
                case 1:
                    pmi.processManageIngredient();
                    break;
                case 2:
                    pmb.processManageBeverage();
                    break;
                case 3:
                    pdd.processDispensingDrink();
                    break;
                case 4:
                    pr.processReport();
                    break;
                case 5:
                    sds.processStoreData();
                    break;
                case 6:
                    System.out.println("GOOD BYE!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 6);
    }   
    
}
