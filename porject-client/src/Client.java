import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread{
    private final String ip;
    private final int port;
    private Socket other;
    private BufferedReader clientReader;
    private PrintWriter clientWriter;
    //private DataInputStream clientReader;
    //private DataOutputStream clientWriter;
    private final String clientName;




    public Client(String clientName, String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.clientName=clientName;


    }


    public  void listenMsg(){
        try {

            while (!other.isClosed()) {

                String response = readData();

                System.out.println(response);
            }
            close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {

            other = new Socket(ip, port);
            clientReader = new BufferedReader(new InputStreamReader(other.getInputStream()));
            clientWriter = new PrintWriter(other.getOutputStream(),true);
            //clientReader = new DataInputStream(other.getInputStream());
           // clientWriter = new DataOutputStream(other.getOutputStream());
            writeData(clientName);
         //   while (!other.isClosed()) {
                Request request = new Request(other);
                request.start();
                listenMsg();


        } catch (Exception e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
             close();
        }
    }

    public String readData() {
        try {
            return clientReader.readLine();
            //return clientReader.readUTF();
        } catch (Exception e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
               close();
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
                close();
        }

    }


    public void close(){
        try {
            clientReader.close();
            clientWriter.close();
            other.close();
            System.exit(0);

        }
        catch (Exception e){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,e);
        }
    }


}
