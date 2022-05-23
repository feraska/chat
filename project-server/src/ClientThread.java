import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Thread {
    private  BufferedReader otherReader;
    private  PrintWriter otherWriter;
   // private DataInputStream otherReader;
   // private DataOutputStream otherWriter;
    private static final String ERR="ERROR";
    private static final String OK="OK";
    private  Socket socket;
 //   private static Lock lock=new ReentrantLock();
    private static final String file_root="file_root";
    private static final String split=";";
    private static ArrayList<ClientThread> list = new ArrayList<>();
    //private HashMap<String,Integer> clients= new HashMap<>();

    private String clientName;



    public ClientThread(Socket socket,String clientName){
        this.socket=socket;

        try {
            this.clientName=clientName;
            otherReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            otherWriter = new PrintWriter(socket.getOutputStream(),true);
            //otherReader = new DataInputStream(socket.getInputStream());
            //otherWriter = new DataOutputStream(socket.getOutputStream());



        }
        catch (Exception e){
              close();
        }
    }
    public void removeClient(){
        list.remove(this);
        broadcast(clientName+" is left");
    }
    public void close(){
        try {

           removeClient();
            otherWriter.close();
            otherReader.close();
            socket.close();
        }
        catch (Exception e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public String readData(){
        try {

            return otherReader.readLine();
            //return otherReader.readUTF();

        }
        catch (Exception e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE,null,e);
            close();


        }

        return "";
    }

    public  void broadcast(String msg){
        try {
       //     lock.lock();
            for (ClientThread clientThread:list) {

               if (!clientThread.clientName.equals(clientName)) {
               //     System.out.println(clientThread.clientName + " is connected");
                    //System.out.println(clientThread);
                    //BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));

                    //if(soc!=socket){

                    clientThread.otherWriter.println(msg);
                 //   System.out.println(msg);
                    ///clientThread.otherWriter.flush();
                     }
                }
            //list = new ArrayList<>();
           // }
            //list.clear();
           // System.out.println("size==> "+list.size());
           // lock.unlock();
        }

            catch (Exception e){
                System.out.println(e.getMessage());
            }


       // list.clear();
    }


    public void writeData(String msg){
        try {
            otherWriter.println(msg);
            //otherWriter.writeUTF(msg);
        }
        catch (Exception e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE,null,e);
            close();
        }


    }

    @Override
    public String toString() {
        return "ClientThread{" +
                "otherReader=" + otherReader +
                ", otherWriter=" + otherWriter +
                ", socket=" + socket +
                ", clientName='" + clientName + '\'' +
                '}';
    }

    @Override
    public void run() {
        try {

            list.add(this);


            while (!socket.isClosed()) {

                String msg = readData();//=readData();// = readData();
                System.out.println(clientName+": "+msg);



                processCommand(msg);

            }
        }
        catch (Exception e){

            close();


        }



    }


    public void chat(String msg){
        try {
           // lock.lock();

         //   lock.unlock();
          //  System.out.println(msg);
            //while (!socket.isClosed()) {
                broadcast(clientName + "  :  " + msg);
                broadcast(clientName+" send is "+OK);
          //  }
           // delete();
           // close();
        }
        catch (Exception e){
        //    broadcast(clientName+" send is "+ERR);
        }

          //  list.remove(this);

       // }

        //writeData(OK);
        //  close();

    }
    public synchronized void upload(String fileName){
        try {
            File getName = new File(fileName);
            String data=readData();
          //  System.out.println(data);
            byte[] bytes = Base64.getDecoder().decode(data.getBytes());
            //byte[] bytes=data.getBytes();
            File file = new File(file_root+"/"+getName.getName());
            Files.write(Path.of(file.getPath()),bytes);
           // writeData(OK);
            broadcast(clientName+" Upload file "+getName.getName()+" is "+OK);
        }
        catch (Exception e){
            broadcast(clientName+" Upload file+"+fileName+" is "+ERR);
        }
    }

    public void download(String fileName){
        try {

            fileName=file_root + "/" + fileName;
            byte[] bytes = Files.readAllBytes(Path.of(fileName));
            byte[] encoder = Base64.getEncoder().encode(bytes);
            String data = new String(encoder);
            writeData(data);
            broadcast(clientName+" Download file "+fileName+" is "+OK);;

        }
        catch (Exception e){
            broadcast(clientName+" Download file+"+fileName+" is "+ERR);
        }
    }
    public void processCommand(String command){
        mkdirectory();
        String[]splits=command.split(split);
        String cmd=splits[0];
        switch (cmd){
            case "chat":
                chat(splits[1]);
                break;
            case "upload" :
                upload(splits[1]);
                break;
            case "download":
                download(splits[1]);
                break;
        }

    }
    public void mkdirectory(){
        File directory = new File(file_root);
        if(!directory.exists()){
            directory.mkdir();
        }
    }
}
