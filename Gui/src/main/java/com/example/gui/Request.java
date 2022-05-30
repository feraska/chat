package com.example.gui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Request extends Thread{
    private Socket socket;
    private BufferedReader clientReader;
    private PrintWriter clientWriter;
    private static String command;
    private static final String split=";";
    public String path;


    public static String getCommand() {
        return command;
    }

    public static void setCommand(String command) {
        Request.command = command;
    }

    public Request(Socket socket) {
        try {

            clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientWriter = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (Exception e){

        }
    }
    public  void getRequest(){
    //    Scanner scanner = new Scanner(System.in);

     //   while (true) {
            try {
              //  scanner.nextLine();
             //   System.out.println("Enter Command");
          //      String in = scanner.nextLine();
                if (command.equals("exit")) {
                    System.exit(0);

                }
             //   command=in;
                processCommand(command);
                //clientWriter.close();
                //clientReader.close();
            }catch (Exception e){

            }
     //   }
    }
    public void close(){
        try {
            clientReader.close();
            clientWriter.close();
            socket.close();

        }
        catch (Exception e){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public String saveFileGui() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            return fileToSave.getAbsolutePath();
        }
        return "";
    }
    private String uploadFileGui(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            return selectedFile.getAbsolutePath();
        }
        return "";
    }
    /*
    public void close(){
        try {
            clientReader.close();
            clientWriter.close();
            other.close();

        }


        catch (Exception e){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,e);
        }

     */




    public void processCommand(String command) {
        switch (command.toLowerCase()) {
            case "chat":
                chat();
                break;
            case "upload":
                upload();
                break;
            case "download":
                download();
                break;

        }
       // close();
    }

    private void download() {
     //   System.out.println("path to dwonload");
   //     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
    //        String path = bufferedReader.readLine();
            String to = saveFileGui();
            download(path,to);
        }
        catch (Exception e){

        }

    }

    private void upload() {
        try {

            String path = uploadFileGui();
            //File file = new File(path);
            upload(path);
        }
        catch (Exception e){

        }


    }
    public void chat(String msg){
        try {
        //    command="chat";
            byte[]msgBytes=msg.getBytes();
            byte[]msgB=Base64.getEncoder().encode(msgBytes);
            String msgStr = new String(msgB);
            writeData(command+split+msgStr);
            //   listenMsg();
            //  close();
            // clientReader.readLine();



        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void chat() {
        try {
       //     System.out.println("Enter msg to send");
       //     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
       //     String msg = bufferedReader.readLine();
            String msg=Main.getMsg();
            chat(msg);

            //client.join();
            //client.close();

        } catch (Exception e) {

        }
    }
    public  void upload(String fileName){
        try {
           // command="upload";
            //  fileName=fileName.replace("\\","/");
            writeData(command+split+fileName);
            byte[] bytes= Files.readAllBytes(Paths.get(fileName));
            byte[] encoded = Base64.getEncoder().encode(bytes);
            String b=new String(encoded);
            // String b = new String(bytes);
            writeData(b);
          //  String response=readData();
          //  System.out.println(response);
        }
        catch (Exception e){
            System.out.println("error");
        }
    }
    public void download(String path,String to){
        try {
          //  command="download";
            writeData(command+split+path);
            String msg=readData();
            byte[] bytes= msg.getBytes();
            byte[] decoded = Base64.getDecoder().decode(bytes);
            File file = new File(to);
            Files.write(Path.of(file.getAbsolutePath()),decoded);
            // String b = new String(bytes);
            //writeData(b);
          //  String response=readData();
         //   System.out.println(response);
        }


        catch (Exception e){

        }
    }
    public String readData() {
        try {
            return clientReader.readLine();
            //return clientReader.readUTF();
        } catch (Exception e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
            //   close();
        }
        return "";
    }
        public void writeData(String msg){
            try {
                clientWriter.println(msg);
                //clientWriter.writeUTF(msg);
            }
            catch (Exception e){
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,e);
                //    close();
            }

        }



    @Override
    public void run() {
        getRequest();
    }
}
