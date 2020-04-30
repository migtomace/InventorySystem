package Controller;


import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    Inventory inv;

    @FXML
    private TextField partSearchBox, productSearchBox;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private Button addPartBtn, modPartBtn,deletePartBtn, addProductBtn, modProductBtn, deleteProductBtn, searchPartBtn, searchProductBtn;

    @FXML
    private TableColumn partPrice, productPrice;

    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productsInventorySearch = FXCollections.observableArrayList();

    public MainScreen(Inventory inv){
        this.inv = inv;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generatePartsTable();
        generateProductsTable();

    }

    private void generateProductsTable() {
        productsInventory.setAll(inv.getAllProducts());
        productsTableView.setItems(productsInventory);
        productPrice.setCellFactory(getPriceCell(decimalFormat));
        productsTableView.getSelectionModel().selectFirst();
        productsTableView.refresh();
    }

    private void generatePartsTable() {
        partsInventory.setAll(inv.getAllParts());
        partsTableView.setItems(partsInventory);
        partPrice.setCellFactory(getPriceCell(decimalFormat));
        partsTableView.getSelectionModel().selectFirst();
        partsTableView.refresh();
    }

    @FXML
    void searchPart(ActionEvent event){
        if (!partSearchBox.getText().isEmpty()){
            partsInventorySearch.clear();
            for (Part part : inv.getAllParts()){
                if (part.getPartName().toLowerCase().contains(partSearchBox.getText().toLowerCase().trim())){
                    partsInventorySearch.add(part);
                }
            }
            partsTableView.setItems(partsInventorySearch);
            partsTableView.refresh();
            partsTableView.getSelectionModel().selectFirst();
        }

    }

    @FXML
    void searchProduct(ActionEvent event){
        if (!productSearchBox.getText().isEmpty()){
            productsInventorySearch.clear();
            for (Product product: productsInventory){
                if (product.getProductName().toLowerCase().contains(productSearchBox.getText().toLowerCase().trim())){
                    productsInventorySearch.add(product);
                }
            }
            productsTableView.setItems(productsInventorySearch);
            productsTableView.refresh();
            productsTableView.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void onEnter(ActionEvent event){
        String string = event.getSource().toString();
        if (string.equals("TextField[id=partSearchBox, styleClass=text-input text-field]")){
            searchPart(event);
        } else {
            searchProduct(event);
        }

    }

    public <ROW,Double> Callback<TableColumn<ROW, Double>, TableCell<ROW, Double>> getPriceCell (DecimalFormat format) {
        return column -> {
            return new TableCell<ROW, Double> () {
                @Override
                protected void updateItem (Double item, boolean empty) {
                    super.updateItem (item, empty);
                    if (item == null || empty) {
                        setText (null);
                    }
                    else {
                        setText ("$" + format.format (item));
                    }
                }
            };
        };
    }


    @FXML
    void deletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            partsInventory.remove(partsTableView.getSelectionModel().getSelectedItem());
            partsTableView.refresh();
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            productsInventory.remove(productsTableView.getSelectionModel().getSelectedItem());
            productsTableView.refresh();
        }
    }

    @FXML
    void displayAddPart(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
            Controller.AddPart controller = new Controller.AddPart(inv);
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) addPartBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayAddProduct(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
            Controller.AddProduct controller = new Controller.AddProduct(inv);
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) addProductBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayModPart(ActionEvent event) {
        Part selected = partsTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            Controller.ModifyPart controller = new Controller.ModifyPart(inv, selected);
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) modPartBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayModProduct(ActionEvent event) {
        Product selected = productsTableView.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            Controller.ModifyProduct controller = new Controller.ModifyProduct(inv, selected);
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) modProductBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
