/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageingredients;

import check_data.Tools;

/**
 *
 * @author Do Dat
 */
public class Menu {
    public static int getChoice(Object[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + " - " + options[i]);           
        }
        return (int) (double) Tools.inputNumber("Choose 1 -> " + options.length + ": ");
    }
}
