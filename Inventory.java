package matt.joslen.project.Model;

/**
 * Model class for Inventory
 * 
 * @author Matt
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    
    public static ObservableList<Product> getProducts() {
        return products;
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
