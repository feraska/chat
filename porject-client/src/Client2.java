import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Client2 {
    private  String name;
    private  String ip;
    private  int port;
    private String fileName(String path){
        File file = new File(path);
        if(file.exists()) {
            return file.getName();
        }
        return "";
    }




    public static void main(String[] args) {
        Client2 client1 = new Client2();
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter your name");
        client1.name=scanner.nextLine();
        System.out.println("enter your ip");
        client1.ip = scanner.nextLine();
        System.out.println("enter your port");
        client1.port=scanner.nextInt();
        Client client = new Client(client1.name, client1.ip, client1.port);
        client.start();
        //  scanner.close();




    }



}
