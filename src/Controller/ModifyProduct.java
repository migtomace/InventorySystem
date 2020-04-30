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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProduct implements Initializable {

    @FXML
    private TextField partSearchBox;
    @FXML
    private TableView<Part> partsTableView, associatedTableView;
    @FXML
    private TableColumn partPrice, aPartPrice;

    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");


    @FXML
    private Button cancelBtn;

    @FXML
    private TextField idTF, nameTF, invTF, priceTF, minTF, maxTF;

    private Inventory inv;
    private Product selected;

    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private String[] errors = new String[]{"All Fields Required.", "MAX must be more than INV, and INV must be more than MIN.", "INV, MIN, MAX & PRICE must contain numeric values. Price may be formatted as such '0.00'", "INV, MIN, MAX & PRICE must contain positive values."};
    private ArrayList<Integer> getErrors = new ArrayList<>();

    public ModifyProduct(Inventory inv, Product selected){
        this.inv = inv;
        this.selected = selected;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generatePartsTable();
        generateAssociatedPartsTable();
        idTF.setText(selected.getProductID() + "");
        nameTF.setText(selected.getProductName() + "");
        invTF.setText(selected.getProductInv() + "");
        priceTF.setText(decimalFormat.format(selected.getProductPrice()) + "");
        minTF.setText(selected.getProductMin() + "");
        maxTF.setText(selected.getProductMax() + "");
    }

    private void generatePartsTable() {
        partsInventory.setAll(inv.getAllParts());
        partsTableView.setItems(partsInventory);
        partPrice.setCellFactory(getPriceCell(decimalFormat));
        partsTableView.getSelectionModel().selectFirst();
        partsTableView.refresh();
    }

    private void generateAssociatedPartsTable() {
        associatedParts.setAll(selected.getAllAssociatedParts());
        sortListByID();
        associatedTableView.setItems(associatedParts);
        aPartPrice.setCellFactory(getPriceCell(decimalFormat));
        associatedTableView.getSelectionModel().selectFirst();
        associatedTableView.refresh();
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
    void save(ActionEvent event){
        Product product;
        //check data entered correctly
        //and update data
            String name = nameTF.getText();
            if (checkFormatAndNotNull()){
            double price = Double.parseDouble(priceTF.getText());
            int stock = Integer.parseInt(invTF.getText());
            int min = Integer.parseInt(minTF.getText());
            int max = Integer.parseInt(maxTF.getText());
                //new id only generates if part is fully validated
                int id = selected.getProductID();
                product = new Product(id, name, price, stock, min, max);
                product.setAssociatedParts(associatedParts);
                double costParts = 0;
                for (Part part: associatedParts){
                    costParts += part.getPartPrice();
                }
                if (associatedParts.isEmpty()){
                    alertError("Each Product must have at least one associated part.");
                } else if (price < costParts){
                    alertError("Product can't cost less than associated parts.");
                    redTF(priceTF);
                } else {
                    product.setAssociatedParts(associatedParts);
                    inv.updateProduct(inv.getProductIndex(selected), product);
                    //route back to mainscreen
                    displayMainScreen(event);
                }
            } else {
                String string = "Please make the following corrections:\n\n";
                for(int code : getErrors){
                    string += errors[code] + "\n";
                }
                getErrors.clear();
                alertError(string);
            }

    }

    @FXML
    void onEnter(ActionEvent event){
        String string = event.getSource().toString();
        if (string.equals("TextField[id=partSearchBox, styleClass=text-input text-field]")){
            searchPart(event);
        } else {
            save(event);
        }

    }

    void alertError(String string){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(string);
        alert.show();
    }

    private boolean checkFormatAndNotNull(){
        Boolean bool = true;
        if (nameTF.getText().isEmpty()){
            getErrors.add(0);
            bool = false;
            redTF(nameTF);
        } else {
            defaultTF(nameTF);
        }
        bool = getaBoolean(bool, priceTF);
        bool = getaBoolean(bool, invTF);
        bool = getaBoolean(bool, minTF);
        bool = getaBoolean(bool, maxTF);
        if (isDouble(minTF.getText()) && isDouble(maxTF.getText()) && isDouble(invTF.getText())){
            double max = Double.parseDouble(maxTF.getText());
            double min = Double.parseDouble(minTF.getText());
            double inv = Double.parseDouble(invTF.getText());
            if (max < min || inv < min || inv > max){
                getErrors.add(1);
                redTF(minTF);
                redTF(maxTF);
                redTF(invTF);
                bool = false;
            }
        }
        return bool;
    }

    private void defaultTF(TextField tf) {
        tf.setStyle("-fx-border-color: none");
    }


    private Boolean getaBoolean(Boolean bool, TextField tf) {
        if (!isDouble(tf.getText())){
            if (!tf.getText().isEmpty()) if (!getErrors.contains(2)) getErrors.add(2);
            bool = false;
            redTF(tf);
        } else if (Double.parseDouble(tf.getText()) < 0){
            if (!getErrors.contains(3)) getErrors.add(3);
            bool = false;
            redTF(tf);
        } else {
            defaultTF(tf);
        }
        return bool;
    }

    private void redTF(TextField tf){
        tf.setStyle("-fx-border-color: red");
    }

    private boolean isDouble(String string) {
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            if (!getErrors.contains(0)) getErrors.add(0); //if TextField is null add error code
            return false;
        }
        return true;
    }

    @FXML
    void addAssociatedPart(ActionEvent event){
        if (!containsPart(partsTableView.getSelectionModel().getSelectedItem())){
            associatedParts.add(partsTableView.getSelectionModel().getSelectedItem());
            sortListByID();
        }
        else alertError("Part is already associated with this product.");
    }

    @FXML
    void deleteAssociatedPart(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Remove this associated part from this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            associatedParts.remove(associatedTableView.getSelectionModel().getSelectedItem());
            associatedTableView.refresh();
        }
    }

    private void sortListByID(){
        associatedParts.sort(Comparator.comparing(Part::getPartID));
    }

    private boolean containsPart(Part associated){
        for (Part part : associatedParts){
            if (part.getPartID() == associated.getPartID())  return true;
        }
        return false;
    }

    @FXML
    void displayMainScreen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            Controller.MainScreen controller = new Controller.MainScreen(inv);
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
