package matt.joslen.project.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Matt
 */
public abstract class Part {
    
    public final IntegerProperty partID;
    public final StringProperty name;
    public final DoubleProperty price;
    public final IntegerProperty inStock;
    public final IntegerProperty max;
    public final IntegerProperty min;
    
    public Part(int partID, String name, double price, int inStock, int max, int min) {
        this.partID = new SimpleIntegerProperty(partID);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.inStock = new SimpleIntegerProperty(inStock);
        this.max = new SimpleIntegerProperty(max);
        this.min = new SimpleIntegerProperty(min);
    }
    
    
    public void setName(String name) {
        this.name.set(name);
    }
    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setPrice(double price) {
        this.price.set(price);
    }
    public double getPrice() {
        return price.get();
    }
    public DoubleProperty priceProperty() {
        return price;
    }
    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }
    public int getInStock() {
        return inStock.get();
    }
    public IntegerProperty inStockProperty() {
        return inStock;
    }
    public void setMin(int min) {
        this.min.set(min);
    }
    public int getMin() {
        return min.get();
    }
    public void setMax(int max) {
        this.max.set(max);
    }
    public int getMax() {
        return max.get();
    }
    public void setPartID(int partID) {
       this.partID.set(partID);
    }
    public int getPartID() {
        return partID.get();
    }
    public IntegerProperty partIDProperty() {
        return partID;
    }
}
