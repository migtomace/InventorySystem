package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int productID, productInv, productMin, productMax;
    private String productName;
    private double productPrice;

    public Product(int ID, String name, double price, int stock, int min, int max){
        this.productID = ID;
        this.productName = name;
        this.productPrice = price;
        this.productInv = stock;
        this.productMax = max;
        this.productMin = min;
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductInv() {
        return productInv;
    }

    public void setProductInv(int productInv) {
        this.productInv = productInv;
    }

    public int getProductMin() {
        return productMin;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    public int getProductMax() {
        return productMax;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    public boolean deleteAssociatedPart(Part part){
        associatedParts.remove(part);
        return false;
    }

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
}
