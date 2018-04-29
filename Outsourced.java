package matt.joslen.project.Model;

/**
 *
 * @author Matt
 */
public class Outsourced extends Part {
    
    public String companyName;
    
    public void setCompanyName(String companyName) {
        companyName = this.companyName;
    }
    public String getCompanyName() {
        return companyName;
    }
           
    public Outsourced(int partID, String name, double price, int inStock, int max, int min, String companyName) {
        super(partID, name, price, inStock, max, min);
        this.companyName = companyName;
    }
}
