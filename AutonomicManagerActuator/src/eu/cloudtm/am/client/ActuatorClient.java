package eu.cloudtm.am.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
* @Author Sebastiano Peluso
*/
public class ActuatorClient {




    private String ip;

    private int port = 8888;



    public ActuatorClient(String serverAddress){



        this.ip = serverAddress;

    }

    public ActuatorClient(String serverAddress, int port){

        this.ip = serverAddress;

        this.port = port;

    }

    public boolean setConfiguration(int numNodes, int replicationDegree){

        if(numNodes < 0 || replicationDegree < 0) {

            return false;
        }

        Socket clientSocket = null;
        ObjectOutputStream outToServer = null;
        ObjectInputStream inFromServer = null;

        boolean result = true;

        try {
            clientSocket = new Socket(this.ip, this.port);
            outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            outToServer.writeObject(eu.cloudtm.am.server.ActuatorCommand.SET_CONFIGURATION);
            outToServer.flush();

            outToServer.writeInt(numNodes);
            outToServer.writeInt(replicationDegree);
            outToServer.flush();

            result = (Boolean) inFromServer.readObject();


        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            result = false;
            e.printStackTrace();
        }


        return result;

    }



    public boolean addNewInfinispanNode(){

        Socket clientSocket = null;
        ObjectOutputStream outToServer = null;
        ObjectInputStream inFromServer = null;

        boolean result = true;

        try {
            clientSocket = new Socket(this.ip, this.port);
            outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            outToServer.writeObject(eu.cloudtm.am.server.ActuatorCommand.ADD_NEW_INFINISPAN_NODE);
            outToServer.flush();

            result = (Boolean) inFromServer.readObject();





        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            result = false;
            e.printStackTrace();
        }


        return result;
    }

    public boolean removeInfinispanNode(){

        Socket clientSocket = null;
        ObjectOutputStream outToServer = null;
        ObjectInputStream inFromServer = null;

        boolean result = true;

        try {
            clientSocket = new Socket(this.ip, this.port);
            outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            outToServer.writeObject(eu.cloudtm.am.server.ActuatorCommand.REMOVE_INFINISPAN_NODE);
            outToServer.flush();

            result = (Boolean) inFromServer.readObject();





        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            result = false;
            e.printStackTrace();
        }


        return result;
    }

    public boolean changeReplicationDegree(int replicationDegree){

        Socket clientSocket = null;
        ObjectOutputStream outToServer = null;
        ObjectInputStream inFromServer = null;

        boolean result = true;

        try {
            clientSocket = new Socket(this.ip, this.port);
            outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            outToServer.writeObject(eu.cloudtm.am.server.ActuatorCommand.SET_REPLICATION_DEGREE);
            outToServer.flush();

            outToServer.writeInt(replicationDegree);
            outToServer.flush();

            result = (Boolean) inFromServer.readObject();


        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            result = false;
            e.printStackTrace();
        }


        return result;
    }
}



