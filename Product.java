package matt.joslen.project.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matt
 */
public class Product {
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public final IntegerProperty productID;
    public final StringProperty productName;
    public final DoubleProperty productPrice;
    public final IntegerProperty productInStock;
    public final IntegerProperty productMin;
    public final IntegerProperty productMax;
    
    public Product(int productID, String productName, double productPrice, int productInStock, int productMax, int productMin, ObservableList<Part> associatedParts) {
        this.productID = new SimpleIntegerProperty(productID);
        this.productName = new SimpleStringProperty(productName);
        this.productPrice = new SimpleDoubleProperty(productPrice);
        this.productInStock = new SimpleIntegerProperty(productInStock);
        this.productMax = new SimpleIntegerProperty(productMax);
        this.productMin = new SimpleIntegerProperty(productMin);
        this.associatedParts = associatedParts;
    }
    
    
    public void setProductName(String productName) {
       this.productName.set(productName);
    }
    public String getProductName() {
        return productName.get();
    }
    public StringProperty productNameProperty() {
        return productName;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice.set(productPrice);
    }
    public double getProductPrice() {
        return productPrice.get();   
    }
    public DoubleProperty productPriceProperty() {
        return productPrice;
    }
    public void setProductInStock(int productInStock) {
        this.productInStock.set(productInStock);
    }
    public int getProductInStock() {
        return productInStock.get();
    }
    public IntegerProperty productInStockProperty() {
        return productInStock;
    }
    public void setProductMin(int productMin) {
        this.productMin.set(productMin);
    }
    public int getProductMin() {
        return productMin.get();
    }
    public void setProductMax(int productMax) {
        this.productMax.set(productMax);
    }
    public int getProductMax() {
        return productMax.get();
    }
    public Part getAssociatedParts(Part associatedParts) {
        return associatedParts;
    }
    public void setProductID(int productID) {
        this.productID.set(productID);
    }
    public int getProductID() {
        return productID.get();
    }
    public IntegerProperty productIDProperty() {
        return productID;
    }
}
