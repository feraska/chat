package com.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Main   implements Initializable {
    public static ObservableList<String> ob=FXCollections.observableArrayList();
    @FXML
    public  ListView<String> logListView;
    @FXML
    private ComboBox<String> chooseComboBox;
    @FXML
    private TextArea chatTxt;
    private static String msg;
    public static Main copyMain;
    private int i = 0;


    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg) {
        Main.msg = msg;
    }
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {

       // stage.set
     //   System.out.println("FSFSDF");
      //  System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        copyMain=this;



       // s.add("👶");
      //  chooseComboBox.setItems(s);
    }




    public void chatClick(ActionEvent actionEvent) {
        Request.setCommand("chat");
        msg=chatTxt.getText();
        Thread thread = new Thread(()->Client.getRequest().getRequest());
        thread.start();
      //  AddNewNotification(msg);
        chatTxt.clear();
        //logListView.setItems(ob);


    }

    public void AddNewNotification(String notification)
    {
        Platform.runLater(
                () -> {

                    this.logListView.getItems().add(notification);
                    this.logListView.scrollTo(logListView.getItems().size());
                }
        );
    }


    public void uploadClick(ActionEvent actionEvent) {
        Request.setCommand("upload");
        Thread thread = new Thread(()->Client.getRequest().getRequest());
        thread.start();

    }





    public void exitClick(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void txtChatPress(KeyEvent keyEvent) {
        if(i>20){

        }
      i++;

    }


    public void listViewClick(MouseEvent mouseEvent) {
        String selectedItem=logListView.getSelectionModel().getSelectedItem();
        if(selectedItem.contains("upload")){
            String[]strings=selectedItem.split(" ");
            Request.setCommand("download");
            Client.getRequest().path=strings[3];
            Thread thread = new Thread(()->Client.getRequest().getRequest());
            thread.start();
        }

    }
}

