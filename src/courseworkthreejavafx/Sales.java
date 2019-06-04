/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseworkthreejavafx;

/**
 *
 * @author cmpgsbyr // GETTING THE DATA FROM THE WEB SERVICE
 */
public class Sales {
    private int QTR;
    private int Quantity;
    private String Region, Vehicle, Year;

    public Sales(int QTR, int Quantity, String Region, String Vehicle, String Year) {
        this.QTR = QTR;
        this.Quantity = Quantity;
        this.Region = Region;
        this.Vehicle = Vehicle;
        this.Year = Year;
    }
    
    public int getQTR() {
        return QTR;
    }

    public void setQTR(int QTR) {
        this.QTR = QTR;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String Vehicle) {
        this.Vehicle = Vehicle;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    Object getIntake() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}