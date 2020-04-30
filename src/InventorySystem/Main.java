package InventorySystem;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {

    @FXML
    private Button saveBtn;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Inventory inv = new Inventory();
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Controller/MainScreen.fxml"));
        Controller.MainScreen controller = new Controller.MainScreen(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void addTestData(Inventory inv) {

        Part part = new InHouse(1, "MyPart", 5.33, 23, 5,56,456);
        Part part2 = new InHouse(2, "MyCoolPart", 4.99, 13, 3,50,111);
        Part part3 = new InHouse(3, "coolPart", 5.00, 34, 10,60,234);
        Part part4 = new InHouse(4, "The Part", 9.99, 33, 9,40,543);
        Part part5 = new Outsourced(5, "A Part", 5.33, 23, 7,67,"MyCo");
        Part part6 = new Outsourced(6, "Thing", 5.33, 34, 6,90,"TheCo");
        Part part7 = new Outsourced(7, "Something", 9.99, 53, 20,70,"CoolCo");
        Part part8 = new Outsourced(8, "Another part", 5.33, 38, 5,85,"CoolCo");
        Part part9 = new Outsourced(8, "ExpensivePart", 139.95, 38, 5,85,"MoneyCo");
        inv.addPart(part);
        inv.addPart(part2);
        inv.addPart(part3);
        inv.addPart(part4);
        inv.addPart(part5);
        inv.addPart(part6);
        inv.addPart(part7);
        inv.addPart(part8);
        inv.addPart(part9);

        Product product1 = new Product(123, "MyCrazyProduct", 50.99, 23, 10, 100);
        product1.addAssociatedPart(part7);
        product1.addAssociatedPart(part2);
        Product product2 = new Product(321, "MySimpleProduct", 30.55, 23, 10,100);
        product2.addAssociatedPart(part);
        product2.addAssociatedPart(part3);
        Product product3 = new Product(432, "simpleProduct", 40.00, 23, 1,50);
        product3.addAssociatedPart(part5);
        product3.addAssociatedPart(part6);
        inv.addProduct(product1);
        inv.addProduct(product2);
        inv.addProduct(product3);


    }


}
