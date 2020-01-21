package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import AAA.FrameActionMsg;

public class ClientApp {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // need host and port, we want to connect to the ServerSocket at port 8080
        Socket socket = new Socket("localhost", 8080);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        
        //input streams
        InputStream input = socket.getInputStream();

        // make a bunch of messages to send.
        FrameActionMsg message = new FrameActionMsg();
        message.setUserName("Joaquim");
        message.setAction("GetActivities");

        System.out.println("Sending messages to the ServerSocket");
        objectOutputStream.writeObject(message);
        
        //read object
        ObjectInputStream objectInputStream = new ObjectInputStream(input);
        FrameActionMsg messageRec = (FrameActionMsg) objectInputStream.readObject();
        System.out.println(messageRec.getActivities().toString());
        System.out.println("Closing socket and terminating program.");
        objectOutputStream.close();
        socket.close();
    }
}
