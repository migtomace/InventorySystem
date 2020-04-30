package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {


    //still need to complete two methods!!!!

    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public void addPart(Part newPart){
        allParts.add(newPart);
    }

    public void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public int getPartIndex(Part selected){
        int index = 0;
        for (Part part: allParts){
            if (part.getPartID() == selected.getPartID()) return index;
            else index++;
        }
        return index;
    }

    public int getProductIndex(Product selected){
        int index = 0;
        for (Product product: allProducts){
            if (product.getProductID() == selected.getProductID()) return index;
            else index++;
        }
        return index;
    }

    public Part lookupPart(int partid){
        return allParts.get(partid);
    }

    public Product lookupProduct(int productid){
        return allProducts.get(productid);
    }

    public ObservableList<Part> lookupPart(String name){

        return null;
    }

    public ObservableList<Product> lookupProduct(String name){

        return null;
    }

    public void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart){
        if (allParts.remove(selectedPart)) return true;
        return false;
    }

    public boolean deleteProduct(Part selectedProduct){
        if (allProducts.remove(selectedProduct)) return true;
        return false;
    }

    public ObservableList<Part> getAllParts(){
        return allParts;
    }

    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
