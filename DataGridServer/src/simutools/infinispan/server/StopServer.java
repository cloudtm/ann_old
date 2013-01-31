package simutools.infinispan.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class StopServer {


    public static void main(String[] args){




        Socket clientSocket = null;
        ObjectOutputStream outToServer = null;
        ObjectInputStream inFromServer = null;

        boolean result = true;

        try {
            clientSocket = new Socket(args[0], 2222);
            outToServer = new ObjectOutputStream(clientSocket.getOutputStream());


            outToServer.writeObject(TransactionalService.STOP);
            outToServer.flush();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
