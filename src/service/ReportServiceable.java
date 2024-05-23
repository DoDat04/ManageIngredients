/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author Do Dat
 */
public interface ReportServiceable {
    public void checkIngredientsAvailable();
    public void checkDrinksOutOfIngredients();
    public void showAllDispensingDrink();
}
