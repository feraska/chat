package com.example.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField ipTxt;
    @FXML
    private TextField portTxt;
    @FXML
    private TextField nameTxt;

   @FXML
   private Label lbl;




    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public boolean checkInput(String name, String ip, String port) {
        try {
            Integer.parseInt(port);
            if (name.equals("")) {
                return false;
            }
            String[] ipv4 = ip.split("\\.");
            if (ipv4.length < 4) {
                return false;
            }
            for (String ipv : ipv4) {
                Integer.parseInt(ipv);
            }
            return true;


        } catch (Exception e) {
            return false;
        }

    }

    public void openWindow(ActionEvent actionEvent){
        try {
           // Stage stage = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
            Stage old = (Stage) ipTxt.getScene().getWindow();
            old.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root= fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));

            stage.show();
            // Hide this current window (if this is what you want)

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean connect(){
        try {

            Client client = new Client(nameTxt.getText(), ipTxt.getText(), Integer.parseInt(portTxt.getText()));
            client.start();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public void start(ActionEvent actionEvent) {

        if(checkInput(nameTxt.getText(),ipTxt.getText(),portTxt.getText())&&connect()) {

            openWindow(actionEvent);
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
