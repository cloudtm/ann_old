package eu.cloudtm.am.test;


import eu.cloudtm.am.client.ActuatorClient;

public class TestRemoveInfinispanNode {



    public static void main(String[] args){


        if(args== null || args.length != 2){
            System.out.println("Usage: java TestRemoveInfinispanNode <Actuator Server IP> <Atuator Server Port>");
        }

        ActuatorClient client = new ActuatorClient(args[0], Integer.parseInt(args[1]));


        client.removeInfinispanNode();


    }

}
