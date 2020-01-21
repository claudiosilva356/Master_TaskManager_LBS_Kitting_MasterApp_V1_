package Master_TaskManager;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import AAA.FrameActionMsg;
import javafx.application.Platform;

public class ServerThread extends Thread {
    Socket socket;
    ServerCore serverCore;
 
    public ServerThread(Socket socket,ServerCore serverCore) {
        this.socket = socket;
        this.serverCore = serverCore;
    }
 
    public void run() {
        try {
            //Input Streams
            InputStream input = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(input);
            FrameActionMsg messageRec = (FrameActionMsg) objectInputStream.readObject();
            
            //output Streams
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
 
            switch(messageRec.getAction()) {
            
            case "GetActivities":
            	System.out.print("GetActivities");
            	//Chama o CoreTasks
            	//Envia Resposta para o Client
            	messageRec.setActivities(serverCore.getActivities(messageRec.getUserName()));
                objectOutputStream.writeObject(messageRec);
                System.out.println("Reply Sent - SetActivities");
                //objectOutputStream.close();
            
            	break;
            case "SetActivityUpdate":
            	System.out.print("SetActivityUpdate");
            	serverCore.updateActivity(messageRec.getUserName(),messageRec.getTaskName(), messageRec.getDetails());
            	messageRec.setActivities(serverCore.getActivities(messageRec.getUserName()));
            	objectOutputStream.writeObject(messageRec);
                System.out.println("Reply Sent - SetActivityUpdate");
            	break;
            case "OnHoldActivity":
            	System.out.println("OnHoldActivity");
            	serverCore.updateActivityState(messageRec.getUserName(),messageRec.getTaskName(),2);
            	messageRec.setActivities(serverCore.getActivities(messageRec.getUserName()));
            	objectOutputStream.writeObject(messageRec);
            	break;
            case "CloseActivity":
            	System.out.println("CloseActivity");
            	serverCore.updateActivityState(messageRec.getUserName(),messageRec.getTaskName(),0);
            	messageRec.setActivities(serverCore.getActivities(messageRec.getUserName()));
            	objectOutputStream.writeObject(messageRec);
            	break;
            }
            //socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}