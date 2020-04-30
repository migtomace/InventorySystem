package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {

    private Inventory inv;

    ToggleGroup toggle;

    @FXML
    private RadioButton inRadio, outRadio;

    @FXML
    private Label compNameL, machL;

    @FXML
    private TextField idTF, partNameTF, invTF, priceTF, maxTF, minTF, compNameTF, machTF;

    @FXML
    private Button cancelBtn, saveBtn;

    private Part selected;

    private String[] errors = new String[]{"All Fields Required.", "MAX must be more than INV, and INV must be more than MIN.", "INV, MIN, MAX & PRICE must contain numeric values. Price may be formatted as such '0.00'", "INV, MIN, MAX & PRICE must contain positive values."};
    private ArrayList<Integer> getErrors = new ArrayList<>();


    public ModifyPart(Inventory inv, Part selected){
        this.inv = inv;
        this.selected = selected;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggle = new ToggleGroup();
        inRadio.setToggleGroup(toggle);
        outRadio.setToggleGroup(toggle);
        idTF.setText(selected.getPartID() + "");
        partNameTF.setText(selected.getPartName() + "");
        invTF.setText(selected.getPartInv() + "");
        priceTF.setText(selected.getPartPrice() + "");
        minTF.setText(selected.getPartMin() + "");
        maxTF.setText(selected.getPartMax() + "");
        if (selected instanceof InHouse){
            machTF.setText(((InHouse) selected).getMachineid() + "");
            toggle.selectToggle(inRadio);
            swapTF();
        } else if (selected instanceof Outsourced){
            compNameTF.setText(((Outsourced) selected).getCompanyName() + "");
            toggle.selectToggle(outRadio);
            swapTF();
        }
    }
    @FXML
    void save(ActionEvent event){
        //check data entered correctly
        Part part;
        //add data
        String name = partNameTF.getText();
        if (checkFormatAndNotNull()){
            double price = Double.parseDouble(priceTF.getText());
            int stock = Integer.parseInt(invTF.getText());
            int min = Integer.parseInt(minTF.getText());
            int max = Integer.parseInt(maxTF.getText());
            int id = Integer.parseInt(idTF.getText());
            if (inRadio.isSelected()){
                int machineid = Integer.parseInt(machTF.getText());
                //new id only generates if part is fully validated
                part = new InHouse(id, name, price, stock, min, max, machineid);
                inv.updatePart(inv.getPartIndex(selected), part);
                //route back to mainscreen
                displayMainScreen(event);
            } else if (outRadio.isSelected() && max > min){
                String companyName = compNameTF.getText();
                //new id only generates if part is fully validated
                part = new Outsourced(id, name, price, stock, min, max, companyName);
                inv.updatePart(inv.getPartIndex(selected), part);
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
        save(event);
    }


    void alertError(String string){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(string);
        alert.show();
    }

    private boolean checkFormatAndNotNull(){
        Boolean bool = true;
        bool = getaBooleanFromString(bool, partNameTF);
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

        if (inRadio.isSelected()){
            bool = getaBoolean(bool, machTF);
        } else if (outRadio.isSelected()){
            bool = getaBooleanFromString(bool, compNameTF);
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

    private Boolean getaBooleanFromString(Boolean bool, TextField tf){
        if (tf.getText().isEmpty()){
            if (!getErrors.contains(0)) getErrors.add(0);
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
    void swapTF(ActionEvent event){
        if (inRadio.isSelected()){
            machTF.setVisible(true);
            machL.setVisible(true);
            compNameTF.setVisible(false);
            compNameL.setVisible(false);
        }
        else if (outRadio.isSelected()){
            compNameTF.setVisible(true);
            compNameL.setVisible(true);
            machTF.setVisible(false);
            machL.setVisible(false);
        }
    }

    void swapTF(){
        if (inRadio.isSelected()){
            machTF.setVisible(true);
            machL.setVisible(true);
            compNameTF.setVisible(false);
            compNameL.setVisible(false);
        }
        else if (outRadio.isSelected()){
            compNameTF.setVisible(true);
            compNameL.setVisible(true);
            machTF.setVisible(false);
            machL.setVisible(false);
        }
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

    private void displayMainScreen() {
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
