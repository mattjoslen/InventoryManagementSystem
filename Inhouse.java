package matt.joslen.project.Model;

/**
 *
 * @author Matt
 */
public class Inhouse extends Part {
    
    public int machineID;
    
    public void setMachineID(int machineID) {
        machineID = this.machineID;
    }
    public int getMachineID() {
        return machineID;
    }
    
    public Inhouse(int partID, String name, double price, int inStock, int max, int min, int machineID) {
        super(partID, name, price, inStock, max, min);
        this.machineID = machineID;
    }
}
