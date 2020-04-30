package Model;

import javafx.fxml.FXML;

import java.text.DecimalFormat;

abstract public class Part {

    private int partID, partInv, partMin, partMax;
    private String partName;
    private double partPrice;


    public Part(){
        //empty constructor
    }

    public Part(int ID, String partName, double partPrice, int stock, int min, int max){
        this.partID = ID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partInv = stock;
        this.partMin = min;
        this.partMax = max;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public int getPartInv() {
        return partInv;
    }

    public void setPartInv(int partInv) {
        this.partInv = partInv;
    }

    public int getPartMin() {
        return partMin;
    }

    public void setPartMin(int partMin) {
        this.partMin = partMin;
    }

    public int getPartMax() {
        return partMax;
    }

    public void setPartMax(int partMax) {
        this.partMax = partMax;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

}
