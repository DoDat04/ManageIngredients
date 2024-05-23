/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageingredients;

import service.ReportService;

/**
 *
 * @author Do Dat
 */
public class ProcessReport {
    private final ReportService rs = new ReportService();
    
    public void processReport() {
        String[] options = {
            "The ingredients are available",
            "The drinks for which the store is out of ingredients",
            "Show all the dispensing drink",
            "Exit"
        };
        int choice;
        do {   
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-                                REPORT MENU                            -");
            System.out.println("-------------------------------------------------------------------------");
            choice = Menu.getChoice(options);
            switch(choice) {
                case 1:
                    rs.checkIngredientsAvailable();
                    break;
                case 2:
                    rs.checkDrinksOutOfIngredients();
                    break;
                case 3:
                    rs.showAllDispensingDrink();
                    break;
                case 4:
                    System.out.println("Thanks for choosing report!!!");
                    break;
                default:
                    System.out.println("Invalid choice!!! Please choose again!!!");
            }
        } while (choice != 4);
    }
}
